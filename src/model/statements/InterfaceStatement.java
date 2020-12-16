package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.types.InterfaceType;

public interface InterfaceStatement {
    ProgramState execute(ProgramState state)throws MyException;
    String toString();

    InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String,InterfaceType> assignedTypes)throws MyException;

    InterfaceStatement deepCopy();
}
