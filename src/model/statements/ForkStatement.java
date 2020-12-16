package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.collections.MyStack;
import model.types.InterfaceType;

public class ForkStatement implements InterfaceStatement{
    private InterfaceStatement statement;
    public ForkStatement(InterfaceStatement statement)
    {
        this.statement=statement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return new ProgramState(new MyStack<>(), state.getSymbolTable().deepCopy(), state.getOut(), statement, state.getFileTable(), state.getHeap());
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        return statement.typecheck(assignedTypes.deepCopy());
    }

    @Override
    public String toString() {
        return "Fork("+statement.toString()+")";
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new ForkStatement(statement);
    }

}
