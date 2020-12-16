package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.expressions.InterfaceExpression;
import model.types.IntType;
import model.types.InterfaceType;
import model.values.IntValue;
import model.values.InterfaceValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements InterfaceStatement{
    private InterfaceExpression expression;
    private StringValue variableName;
    public ReadFileStatement(InterfaceExpression e,StringValue variablename)
    {
        expression=e;
        variableName=variablename;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        synchronized (state.getFileTable()) {
            if (state.getSymbolTable().containsKey((String) variableName.getValue()) && state.getSymbolTable().get((String) variableName.getValue()).getType().equals(new IntType())) {
                try {
                    InterfaceValue result = expression.evaluate(state.getSymbolTable(), state.getHeap());
                    if (result instanceof StringValue) {
                        BufferedReader openFile = state.getFileTable().get((StringValue) result);
                        String line = openFile.readLine();
                        IntValue value;
                        if (line == null)
                            value = new IntValue(0);
                        else
                            value = new IntValue(Integer.parseInt(line));
                        state.getSymbolTable().put((String) variableName.getValue(), value);

                    } else throw new MyException("Result not string value");
                } catch (IOException exception) {
                    throw new MyException(exception.getMessage());
                }
            } else throw new MyException("Variable name not defined in symbol table or type not int.");
            return null;
        }
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        InterfaceType variableType=assignedTypes.lookup(variableName.toString());
        if(variableType.equals(new IntType()))
            return assignedTypes;
        else throw new MyException("Variable not of int type.");
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new ReadFileStatement(expression,variableName);
    }

    @Override
    public String toString() {
        return "Read file ("+expression.toString()+","+variableName.toString()+")";
    }
}
