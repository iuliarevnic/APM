package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.types.BoolType;
import model.types.IntType;
import model.types.InterfaceType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.InterfaceValue;

public class VariableDeclarationStatement implements InterfaceStatement {
    private String name;
    private InterfaceType type;

    public VariableDeclarationStatement(String n,InterfaceType type)
    {
        this.name=n;
        this.type=type;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        InterfaceDictionary<String, InterfaceValue> symbolTable=state.getSymbolTable();
        if(symbolTable.isDefined(name))
            throw  new MyException("Variable already exists\n");
        if(type.equals(new IntType()))
        {
            symbolTable.put(name, new IntValue(0));
        }
        else if(type.equals(new BoolType()))
        {
            symbolTable.put(name, new BoolValue(true));
        }
        state.getSymbolTable().put(name, type.defaultValue());
        return null;
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        assignedTypes.put(name,type);
        return assignedTypes;
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new VariableDeclarationStatement(name,type);
    }

    @Override
    public String toString() {
        return type.toString()+" "+name;
    }
}
