package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceHeap;
import model.expressions.InterfaceExpression;
import model.types.BoolType;
import model.types.InterfaceType;
import model.values.BoolValue;
import model.values.InterfaceValue;

public class WhileStatement implements InterfaceStatement{
    private InterfaceStatement statement;
    private InterfaceExpression expression;
    public WhileStatement(InterfaceExpression expression,InterfaceStatement statement)
    {
        this.expression=expression;
        this.statement=statement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceDictionary<String, InterfaceValue> symbolTable= state.getSymbolTable();
        InterfaceHeap<InterfaceValue> heap=state.getHeap();
        InterfaceValue expressionValue=expression.evaluate(symbolTable,heap);
        if(!expressionValue.getType().equals(new BoolType()))
            throw new MyException("Condition not bool.");
        if(!((BoolValue)expressionValue).getValue().equals(false))
        {
            state.getExecutionStack().push(this);
            statement.execute(state);
        }
        return null;
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        InterfaceType expressionType=expression.typecheck(assignedTypes);
        if(expressionType.equals(new BoolType()))
        {
            return statement.typecheck(assignedTypes);
        }
        else throw new MyException("While expression not of bool type.");
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new WhileStatement(expression,statement);
    }

    @Override
    public String toString() {
        return "(while( "+expression.toString()+")"+statement.toString()+")";
    }
}
