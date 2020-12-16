package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.expressions.InterfaceExpression;
import model.types.InterfaceType;
import model.types.StringType;
import model.values.InterfaceValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements InterfaceStatement{
    private InterfaceExpression expression;
    public CloseFileStatement(InterfaceExpression expression)
    {
        this.expression=expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        synchronized (state.getFileTable()) {
            InterfaceValue returnValue = expression.evaluate(state.getSymbolTable(), state.getHeap());
            if (!returnValue.getType().equals(new StringType()))
                throw new MyException("Return value not a string type.");
            if (!state.getFileTable().containsKey((StringValue) returnValue))
                throw new MyException("File not in File table.");
            try {
                BufferedReader openFile = state.getFileTable().get((StringValue) returnValue);
                openFile.close();
            } catch (IOException exception) {
                throw new MyException(exception.getMessage());
            }
            state.getFileTable().remove((StringValue) returnValue);
            return null;
        }
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        InterfaceType expressionType=expression.typecheck(assignedTypes);
        return assignedTypes;
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new CloseFileStatement(expression);
    }

    @Override
    public String toString() {
        return "Close file ("+expression.toString()+")";
    }
}
