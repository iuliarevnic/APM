package model.expressions;

import exceptions.MyException;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceHeap;
import model.types.IntType;
import model.types.InterfaceType;
import model.values.IntValue;
import model.values.InterfaceValue;

import java.util.HashMap;

public class ArithmeticExpression implements InterfaceExpression{
    private  InterfaceExpression expression1;
    private InterfaceExpression expression2;
    private int op;//1:+, 2:-, 3:*, 4:/

    public ArithmeticExpression(InterfaceExpression e1, InterfaceExpression e2,String arithmeticOp)
    {
        this.expression1=e1;
        this.expression2=e2;
        if(arithmeticOp=="+")
            op=1;
        if(arithmeticOp=="-")
            op=2;
        if(arithmeticOp=="*")
            op=3;
        if(arithmeticOp=="/")
            op=4;
    }
    @Override
    public InterfaceValue evaluate(InterfaceDictionary<String, InterfaceValue> symbolTable, InterfaceHeap<InterfaceValue> heap) throws MyException {
        InterfaceValue v1,v2;
        v1= expression1.evaluate(symbolTable,heap);
        if (v1.getType().equals(new IntType())) {
            v2 = expression2.evaluate(symbolTable,heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= (int) i1.getValue();
                n2 = (int) i2.getValue();
                if (op==1) return new IntValue(n1+n2);
                if (op ==2) return new IntValue(n1-n2);
                if(op==3) return new IntValue(n1*n2);
                if(op==4)
                    if(n2==0) throw new MyException("Division by zero");
                    else return new IntValue(n1/n2);
            }else
                throw new MyException("Second operand is not an integer");
        }else
            throw new MyException("First operand is not an integer");

        return null;
    }

    @Override
    public InterfaceType typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        InterfaceType firstType,secondType;
        firstType=expression1.typecheck(assignedTypes);
        secondType=expression2.typecheck(assignedTypes);

        if (firstType.equals(new IntType())) {
            if (secondType.equals(new IntType())) {
                return new IntType();
            } else
                throw new MyException("Second operand is not an integer");
        }else
            throw new MyException("First operand is not an integer");
    }

    @Override
    public String toString() {
        if(op==1)
        return expression1.toString()+" + "+expression2.toString();
        else if(op==2)
            return expression1.toString()+" - "+expression2.toString();
        else if(op==3)
            return expression1.toString()+" * "+expression2.toString();
        else
            return expression1.toString()+" / "+expression2.toString();

    }
}
