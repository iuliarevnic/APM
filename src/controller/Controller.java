package controller;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceHeap;
import model.collections.InterfaceStack;
import model.expressions.InterfaceExpression;
import model.statements.InterfaceStatement;
import model.values.InterfaceValue;
import model.values.ReferenceValue;
import repository.InterfaceRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Controller {
    private InterfaceRepository repository;
    private ExecutorService executor;

    @Override
    public String toString() {
        return repository.toString();
    }
    public void clearFile()
    {
        try{
            repository.clearLog();
        }
        catch (MyException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public Controller(InterfaceRepository repository)
    {
        this.repository=repository;
    }

    public InterfaceRepository getRepository(){return repository;}
    public void addNewProgram(ProgramState program){repository.addProgram(program);}
    /*public ProgramState oneStep(ProgramState state) throws MyException
    {
        InterfaceStack<InterfaceStatement> stack=state.getExecutionStack();
        if(stack.isEmpty()) throw new MyException("Program state stack is empty\n");
        InterfaceStatement currentStatement=stack.pop();
        return currentStatement.execute(state);


    }*/
    /*public void allSteps() throws  MyException,InterruptedException
    {
        /* For A2
        System.out.println("~~~~~~~~~~~~~~~~Initial state~~~~~~~~~~~~~~");
        ProgramState state= repository.getCurrentProgram();
        System.out.println(state.toString());
        while(!state.getExecutionStack().isEmpty())
        {
            oneStep(state);
            System.out.println("~~~~~~~~~~~~~~~~Next step~~~~~~~~~~~~~~~~");
            System.out.println(state.toString());
        }*/
        /*System.out.println("~~~~~~~~~~~~~~~~Initial state~~~~~~~~~~~~~~");
        ProgramState program = repository.getCurrentProgram();
        System.out.println(program.toString());
        repository.logPrgStateExec();
        while (!program.getExecutionStack().isEmpty())
        {
            oneStep(program);
            repository.logPrgStateExec();


            repository.getCurrentProgram().getHeap().setContent(conservativeGarbageCollector(addIndirectReferences(getAddrFromSymTable(repository.getAll()),repository.getCurrentProgram().getHeap()
                    ), repository.getCurrentProgram().getHeap()));
            repository.logPrgStateExec();

            System.out.println("~~~~~~~~~~~~~~~~Next step~~~~~~~~~~~~~~~~");
            System.out.println(program.toString());

        }
    }*/
    public void displayCurrentProgramState()
    {
        System.out.println(repository.getProgramIndex(repository.length()-1).toString());
    }

    public InterfaceStatement getOriginalProgram() {
        return repository.getProgramIndex(repository.length()-1).getOriginalProgram();
    }

    Map<Integer, InterfaceValue> conservativeGarbageCollector(List<Integer> symTableAddr, InterfaceHeap<InterfaceValue> heap){
        return heap.getEntrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    List<Integer> getAddrFromSymTable(List<ProgramState> programList){
        return programList.stream()
                .flatMap(prgState -> prgState.getSymbolTable().getValues().stream())
                .collect(Collectors.toList())
                .stream()
                .filter(element -> element instanceof ReferenceValue)
                .map(element -> ((ReferenceValue) element).getAddress())
                .collect(Collectors.toList());
    }
    List<Integer> addIndirectReferences(List<Integer> addressesFromSymbolTable, InterfaceHeap<InterfaceValue> heap){
        boolean change = true;
        //heap entrySet that needs modifications
        Set<Map.Entry<Integer, InterfaceValue>> heapSet = heap.getEntrySet();
        //copy the addressList for adding indirectReferences
        List<Integer> addressList = new ArrayList<>(addressesFromSymbolTable);

        //we go through heapSet again and again and each time we add to the address list new indirection level and new addresses which must NOT be deleted
        while (change){
            List<Integer> indirectRefList = null;
            change = false;
            indirectRefList = heapSet.stream()
                    .filter(element -> element.getValue() instanceof ReferenceValue)//check if the value is a referenceValue so we can have indirectReferences
                    .filter(element -> addressList.contains(element.getKey()))//check if the addressList has a reference to it
                    .map(element -> (((ReferenceValue) element.getValue()).getAddress()))//map reference to its address(to add it in addressList)
                    .filter(element -> !addressList.contains(element))//check if the addressList already has the reference from elements checked prior
                    .collect(Collectors.toList());//collect to list

            if(!indirectRefList.isEmpty()){
                //we still have indirect references(we should keep checking)
                change = true;
                addressList.addAll(indirectRefList);
            }
        }
        return addressList;
    }

    public ArrayList<ProgramState> removeCompletedProgram(ArrayList<ProgramState> inProgramList)
    {
        //in functional manner
        return (ArrayList<ProgramState>) inProgramList.stream().filter(ProgramState::isNotCompleted)//method reference
                .collect(Collectors.toList());
    }
    public void oneStepForAllPrg(ArrayList<ProgramState> programStates)
    {
        programStates.forEach(program-> {
            try {
                repository.logPrgStateExec(program);
            } catch (MyException exception) {
                System.out.println(exception.getMessage());
            }
        });
        //prepare the list of callables
        List<Callable<ProgramState>> callList=programStates.stream()
                .map((ProgramState p)->(Callable<ProgramState>)()->{return p.oneStep();})
                .collect(Collectors.toList());
        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        List<ProgramState> newProgramList=null;
        try {
            newProgramList=executor.invokeAll(callList).stream()
                    .map(future->{
                        try{return future.get();}
                        catch (InterruptedException| ExecutionException exception)
                        {
                            System.out.println(exception.getMessage());
                        }
                        return null;
                    })
                    .filter(program->program!=null)
                    .collect(Collectors.toList());
        } catch (InterruptedException exception) {
            System.out.println(exception.getMessage());
        }
        //add the new created threads to the list of existing threads
        programStates.addAll(newProgramList);
        //after the execution, print the PrgStateList into the log file
        programStates.forEach(program -> {
            try {
                repository.logPrgStateExec(program);
                System.out.println(program);
//                System.out.println(repository.getProgramIndex(repository.length()-1).toString() + "\n");
            } catch (MyException exception) {
                System.out.println(exception.getMessage());
            }
        });
        //save the current programs into repository
        repository.setProgramList(programStates);//ArrayList
    }
    public void allStep()
    {
        executor= Executors.newFixedThreadPool(2);
        //remove the completed programs
        ArrayList<ProgramState> programList=removeCompletedProgram(repository.getProgramList());
        try{
            repository.clearLog();}
        catch(MyException e)
        {
            System.out.println(e.getMessage());
        }
        while(programList.size()>0)
        {
            repository.getProgramIndex(repository.length()-1).getHeap().setContent(conservativeGarbageCollector(addIndirectReferences(getAddrFromSymTable(repository.getAll()),repository.getProgramIndex(repository.length()-1).getHeap()
            ), repository.getProgramIndex(repository.length()-1).getHeap()));

            oneStepForAllPrg(programList);
            //remove the completed programs
            programList=removeCompletedProgram(repository.getProgramList());
        }
        executor.shutdownNow();
        //Here the repository still contains at least one CompletedProgram
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository
        // update the repository state
        repository.setProgramList(programList);


    }

    public void OneStep()
    {
        executor = Executors.newFixedThreadPool(2);
        ArrayList<ProgramState> programs= removeCompletedProgram(repository.getProgramList());

        programs.forEach(program-> {
            try
            {
                repository.logPrgStateExec(program);
            }
            catch (MyException e)
            {
                System.out.println(e.toString());
            }
        });

        repository.getProgramIndex(repository.length()-1).getHeap().setContent(conservativeGarbageCollector(addIndirectReferences(getAddrFromSymTable(repository.getAll()),repository.getProgramIndex(repository.length()-1).getHeap()
        ), repository.getProgramIndex(repository.length()-1).getHeap()));

        List<Callable<ProgramState>> callList=programs.stream().map((ProgramState p) -> (Callable<ProgramState>) p::oneStep).collect(Collectors.toList());

        List<ProgramState> newProgramList=null;
        try
        {
            newProgramList=executor.invokeAll(callList).stream().
                    map(future-> {
                        try
                        {
                            return future.get();
                        }
                        catch(InterruptedException | ExecutionException e)
                        {
                            System.out.println(e.toString());
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());

        }
        catch (InterruptedException e)
        {
            System.out.println(e.toString());
        }


        programs.addAll(newProgramList);
        programs.forEach(program->
        {
            try
            {
                repository.logPrgStateExec(program);
                System.out.println(program.toString()+"\n");
            }
            catch(MyException e)
            {
                System.out.println(e.toString());
            }
        });

        repository.setProgramList(programs);

        executor.shutdownNow();
    }
}
