package model.expressions;

import exceptions.MyException;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceHeap;
import model.types.InterfaceType;
import model.values.InterfaceValue;

import java.util.HashMap;

public class VariableExpression implements  InterfaceExpression{
    private String id;
    public VariableExpression(String Id)
    {
        this.id=Id;
    }
    @Override
    public InterfaceValue evaluate(InterfaceDictionary<String, InterfaceValue> symbolTable, InterfaceHeap<InterfaceValue> heap) throws MyException {
        return symbolTable.lookup(id);
    }

    @Override
    public InterfaceType typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        return assignedTypes.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
