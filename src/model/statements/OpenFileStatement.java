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
import java.io.FileReader;
import java.io.IOException;

public class OpenFileStatement implements InterfaceStatement{
    private InterfaceExpression expression;
    public OpenFileStatement(InterfaceExpression expression)
    {
        this.expression=expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        synchronized (state.getFileTable()){InterfaceValue result=expression.evaluate(state.getSymbolTable(), state.getHeap());
        if(!result.getType().equals(new StringType()))
        {
            throw new MyException("Result not a string type.");
        }
        else if(state.getFileTable().containsKey((StringValue)result))
        {
            throw new MyException("Key already exists.");
        }
        else
        {
            try{
                BufferedReader buffer=new BufferedReader(new FileReader((String)result.getValue()));
                state.getFileTable().put((StringValue)result,buffer);
            }
            catch(IOException exception)
            {
                throw new MyException(exception.getMessage());
            }
        }
        return null;
        }
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        return assignedTypes;
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new OpenFileStatement(this.expression);
    }

    @Override
    public String toString() {
        return "Open file ("+expression.toString()+")";
    }
}
