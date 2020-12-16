package model.expressions;

import exceptions.MyException;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceHeap;
import model.types.InterfaceType;
import model.types.ReferenceType;
import model.values.InterfaceValue;
import model.values.ReferenceValue;

public class ReadHeapExpression implements InterfaceExpression {
    private InterfaceExpression expression;
    public ReadHeapExpression(InterfaceExpression expression)
    {
        this.expression=expression;
    }

    @Override
    public String toString() {
        return "readHeap( "+expression.toString()+" )";
    }

    @Override
    public InterfaceValue evaluate(InterfaceDictionary<String, InterfaceValue> symbolTable, InterfaceHeap<InterfaceValue> heap) throws MyException {
        synchronized (heap) {
            InterfaceValue value = expression.evaluate(symbolTable, heap);
            if (!(value instanceof ReferenceValue)) {
                throw new MyException("Not ReferenceValue.");
            }
            Integer address = ((ReferenceValue) value).getAddress();
            if (!heap.isDefined(address))
                throw new MyException("Not key in heap table.");
            return heap.getValue(address);
        }
    }

    @Override
    public InterfaceType typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        InterfaceType expressionType= expression.typecheck(assignedTypes);
        if(expressionType instanceof ReferenceType)
        {
            ReferenceType reference=(ReferenceType)expressionType;
            return reference.getInner();
        }
        else throw new MyException("Expression not referenceType.");
    }
}
