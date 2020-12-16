package model.statements;


import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceList;
import model.expressions.InterfaceExpression;
import model.types.InterfaceType;
import model.values.InterfaceValue;

public class PrintStatement implements InterfaceStatement{
    private InterfaceExpression expression;

    public PrintStatement(InterfaceExpression expression)
    {
        this.expression=expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        synchronized (state.getOut()) {
            InterfaceList<InterfaceValue> out = state.getOut();
            InterfaceDictionary<String, InterfaceValue> symbolTable = state.getSymbolTable();

            InterfaceValue value = expression.evaluate(symbolTable, state.getHeap());

            out.add(value);

            return null;
        }
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        expression.typecheck(assignedTypes);
        return assignedTypes;
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new PrintStatement(expression);
    }

    @Override
    public String toString() {
        return "print("+expression.toString()+")";
    }
}
