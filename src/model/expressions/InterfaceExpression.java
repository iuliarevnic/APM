package model.expressions;

import exceptions.MyException;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceHeap;
import model.types.InterfaceType;
import model.values.IntValue;
import model.values.InterfaceValue;

import java.util.HashMap;

public interface InterfaceExpression {
    InterfaceValue evaluate(InterfaceDictionary<String, InterfaceValue> symbolTable, InterfaceHeap<InterfaceValue> heap) throws MyException;
    String toString();

    InterfaceType typecheck(InterfaceDictionary<String,InterfaceType> assignedTypes)throws MyException;
}
