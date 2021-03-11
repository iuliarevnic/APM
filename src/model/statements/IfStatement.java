package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceStack;
import model.expressions.InterfaceExpression;
import model.types.BoolType;
import model.types.IntType;
import model.types.InterfaceType;
import model.values.BoolValue;
import model.values.InterfaceValue;


public class IfStatement implements InterfaceStatement{
    private InterfaceExpression expression;
    private InterfaceStatement thenStatement;
    private InterfaceStatement elseStatement;

    public IfStatement(InterfaceExpression expression,InterfaceStatement then,InterfaceStatement elseSt)
    {
        this.expression=expression;
        this.thenStatement=then;
        this.elseStatement=elseSt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceStack<InterfaceStatement> stack=state.getExecutionStack();
        InterfaceDictionary<String, InterfaceValue> symbolTable=state.getSymbolTable();
        InterfaceValue value;

        try{
            value=expression.evaluate(symbolTable, state.getHeap());
            if(value.getValue().equals(true))
                stack.push(thenStatement);
            else
                stack.push(elseStatement);
        }
        catch(MyException exception)
        {
            throw new MyException(exception.getMessage());
        }


        return null;
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        InterfaceType expressionType= expression.typecheck(assignedTypes);
        if(expressionType.equals(new BoolType()))
        {
            thenStatement.typecheck(assignedTypes.deepCopy());
            elseStatement.typecheck(assignedTypes.deepCopy());
            return assignedTypes;
        }
        else throw new MyException("Expression not boolean\n");
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new IfStatement(expression,thenStatement,elseStatement);
    }

    @Override
    public String toString() {
        return "IF("+expression.toString()+") THEN("+thenStatement.toString()+") ELSE("+elseStatement.toString()+")";
    }
}
