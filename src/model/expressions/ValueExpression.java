package model.expressions;

import exceptions.MyException;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceHeap;
import model.types.InterfaceType;
import model.values.IntValue;
import model.values.InterfaceValue;

import java.util.HashMap;

public class ValueExpression implements InterfaceExpression{
    private InterfaceValue value;

    public ValueExpression(InterfaceValue v)
    {
        this.value=v;
    }
    @Override
    public InterfaceValue evaluate(InterfaceDictionary<String, InterfaceValue> symbolTable, InterfaceHeap<InterfaceValue> heap) throws MyException {
        return this.value;
    }

    @Override
    public InterfaceType typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
