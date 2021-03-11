package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.types.InterfaceType;

public class NopStatement implements  InterfaceStatement{
    @Override
    public String toString() {
        return "";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        return assignedTypes;
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new NopStatement();
    }
}
