package model.statements;

import exceptions.MyException;
import javafx.util.Pair;
import model.ProgramState;
import model.collections.InterfaceBarrierTable;
import model.collections.InterfaceDictionary;
import model.collections.MyList;
import model.types.InterfaceType;
import model.values.InterfaceValue;

public class AwaitBarrierStatement implements InterfaceStatement{
    private String variable;
    public AwaitBarrierStatement(String v)
    {
        this.variable=v;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        synchronized (state.getBarrierTable())
        {
            InterfaceDictionary<String, InterfaceValue> symbolTable=state.getSymbolTable();
            InterfaceBarrierTable<InterfaceValue> barrierTable=state.getBarrierTable();
            if(symbolTable.isDefined(variable)){
                Integer index=(Integer)symbolTable.get(variable).getValue();
                if(!barrierTable.isDefined(index))
                    throw new MyException("Index not in barriertable");
                Pair<InterfaceValue, MyList<Integer>> value=barrierTable.get(index);
                Integer length=value.getValue().size();
                if((Integer)value.getKey().getValue()>length)
                {
                    if(value.getValue().isDefined(state.getId()))
                    {
                        state.getExecutionStack().push(this);
                    }
                    else
                    {
                        value.getValue().add(state.getId());
                        state.getExecutionStack().push(this);
                    }
                }
            }
            else throw new MyException("Variable sin't in symbolTable");


        }
        return null;
    }

    @Override
    public String toString() {
        return "awaitBarrier("+variable+")";
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        return assignedTypes;
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new AwaitBarrierStatement(variable);
    }
}
