package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceStack;
import model.types.InterfaceType;

public class CompoundStatement implements InterfaceStatement{
    private InterfaceStatement firstStatement;
    private InterfaceStatement secondStatement;

    public  CompoundStatement(InterfaceStatement first, InterfaceStatement second)
    {
        firstStatement=first;
        secondStatement=second;
    }
    @Override
    public String toString() {
        return firstStatement.toString() + ";" + secondStatement.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceStack<InterfaceStatement> stack=state.getExecutionStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        return null;
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        //InterfaceDictionary<String,Type> assignedTypes1 = first.typecheck(assignedTypes);
        //InterfaceDictionary<String,Type> assignedTypes2 = second.typecheck(assignedTypes1);
        //return assignedTypes2;
        return secondStatement.typecheck(firstStatement.typecheck(assignedTypes));
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new CompoundStatement(firstStatement,secondStatement);
    }
}
