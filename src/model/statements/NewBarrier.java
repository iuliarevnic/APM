package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceBarrierTable;
import model.collections.InterfaceDictionary;
import model.expressions.InterfaceExpression;
import model.types.InterfaceType;
import model.values.IntValue;
import model.values.InterfaceValue;

public class NewBarrier implements InterfaceStatement{
    private String variable;
    private InterfaceExpression expression;
    public NewBarrier(String variable,InterfaceExpression expression)
    {
        this.variable=variable;
        this.expression=expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        synchronized (state.getBarrierTable())
        {
            InterfaceDictionary<String, InterfaceValue> symbolTable=state.getSymbolTable();
            InterfaceBarrierTable<InterfaceValue> barrierTable=state.getBarrierTable();
            InterfaceValue value=expression.evaluate(symbolTable,state.getHeap());
            barrierTable.add(value);
            if(symbolTable.isDefined(variable))
            {
                InterfaceValue newLocation=new IntValue(barrierTable.getCurrentOccupied());
                symbolTable.update(variable,newLocation);
            }
            else
            {
                InterfaceValue newLocation=new IntValue(barrierTable.getCurrentOccupied());
                symbolTable.put(variable,newLocation);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "newBarrier("+variable+","+expression.toString()+")";
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        expression.typecheck(assignedTypes);
        return assignedTypes;
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new NewBarrier(variable,expression);
    }
}
