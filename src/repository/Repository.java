package repository;

import exceptions.MyException;
import model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Repository implements InterfaceRepository{
    private String logFilePath;
    private ArrayList<ProgramState> repository;
    public Repository(String logFilePath)
    {
        repository=new ArrayList<ProgramState>();
        this.logFilePath=logFilePath;
    }
    public Repository(ArrayList<ProgramState> repository,String logFilePath)
    {
        this.repository=repository;
        this.logFilePath=logFilePath;
    }
    //@Override
    //public ProgramState getCurrentProgram() {
        //return repository.get(repository.size()-1);
    //}

    @Override
    public void addProgram(ProgramState program) {
        repository.add(program);
    }

    @Override
    public ProgramState getProgramIndex(int index) {
        return repository.get(index);
    }

    @Override
    public void logPrgStateExec(ProgramState programState) throws MyException {
        try{
            PrintWriter logFile=new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
            logFile.write(programState.toString1());
            logFile.close();
        }catch(IOException e)
        {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public int length() {
        return repository.size();
    }

    @Override
    public ArrayList<ProgramState> getAll() {
        return repository;
    }

    @Override
    public ArrayList<ProgramState> getProgramList() {
        return repository;
    }

    @Override
    public ProgramState getProgramStateById(int Id) {
        for(ProgramState programState:repository)
        {
            if(programState.getId()==Id)
                return programState;
        }
        return null;
    }

    @Override
    public String toString() {
        String string="";
        for(ProgramState p:repository)
            string+=p.getOriginalProgram().toString();
        return string;
    }

    @Override
    public void setProgramList(ArrayList<ProgramState> newRepository) {
     this.repository=newRepository;
    }
}
