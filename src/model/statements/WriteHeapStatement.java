package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceHeap;
import model.expressions.InterfaceExpression;
import model.types.InterfaceType;
import model.types.ReferenceType;
import model.values.InterfaceValue;
import model.values.ReferenceValue;
import model.values.StringValue;

public class WriteHeapStatement implements InterfaceStatement {
    private StringValue variableName;
    private InterfaceExpression expression;
    public WriteHeapStatement(StringValue variableName,InterfaceExpression expression)
    {
        this.variableName=variableName;
        this.expression=expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        synchronized (state.getHeap()){InterfaceDictionary<String, InterfaceValue> symbolTable= state.getSymbolTable();
        InterfaceHeap<InterfaceValue> heap=state.getHeap();
        if(!symbolTable.isDefined(variableName.toString()))
            throw new MyException("Variable not defined.");
        InterfaceValue oldValue=symbolTable.get(variableName.toString());
        if(!(oldValue.getType() instanceof ReferenceType))
        {
            throw new MyException("Type not referenceType.");
        }
        if(!heap.isDefined(((ReferenceValue)oldValue).getAddress()))
         throw new MyException("Address not defined.");
        InterfaceValue value=expression.evaluate(symbolTable,heap);
        if(!value.getType().equals(oldValue.getValue()))
            throw new MyException("Type not same as locationType.");
        heap.update(((ReferenceValue)oldValue).getAddress(),value);
        return null;
        }
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        InterfaceType variableType,expressionType;
        variableType=assignedTypes.lookup(variableName.toString());
        expressionType=expression.typecheck(assignedTypes);
        if(variableType.equals(new ReferenceType(expressionType)))
        {
            return assignedTypes;
        }
        else throw new MyException("Variable type and expression type differ.");
    }

    @Override
    public String toString() {
        return "writeHeap( "+variableName.toString()+", "+expression.toString()+" )";
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new WriteHeapStatement(variableName,expression);
    }
}
