package model.expressions;

import exceptions.MyException;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceHeap;
import model.types.BoolType;
import model.types.InterfaceType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.InterfaceValue;

import java.util.HashMap;

public class LogicalExpression implements InterfaceExpression{
    private int op;//1:and, 2:or
    private InterfaceExpression expression1;
    private InterfaceExpression expression2;

    public LogicalExpression(InterfaceExpression e1,InterfaceExpression e2,String logicOp)
    {
        this.expression1=e1;
        this.expression2=e2;
        if(logicOp == "&&")
            this.op=1;
        if(logicOp=="||")
            this.op=2;
    }
    @Override
    public InterfaceValue evaluate(InterfaceDictionary<String, InterfaceValue> symbolTable, InterfaceHeap<InterfaceValue> heap) throws MyException {

        InterfaceValue firstResult;//initialize again
        InterfaceValue secondResult;

        firstResult=this.expression1.evaluate(symbolTable,heap);
        if(firstResult.getType().equals(new BoolType()))
        {
            secondResult=this.expression2.evaluate(symbolTable,heap);
            if(secondResult.getType().equals(new BoolType()))
            {
                BoolValue bool1=(BoolValue)firstResult;
                BoolValue bool2=(BoolValue)secondResult;
                boolean n1,n2;
                n1= (boolean)bool1.getValue();
                n2=(boolean)bool2.getValue();
                if(op==1)
                    return new BoolValue(n1&&n2);
                if(op==2)
                    return new BoolValue(n1||n2);
            }else
                throw new MyException("Second operand is not boolean");
        }else
            throw new MyException("First operand is not boolean");



        return null;
    }

    @Override
    public InterfaceType typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        InterfaceType firstType,secondType;
        firstType=expression1.typecheck(assignedTypes);
        secondType=expression2.typecheck(assignedTypes);

        if (firstType.equals(new BoolType())) {
            if (secondType.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new MyException("Second operand is not an integer");
        }else
            throw new MyException("First operand is not an integer");
    }

    @Override
    public String toString() {
        if(op==1)
        return expression1.toString()+" && "+expression2.toString();
        else if(op==2)
            return expression1.toString()+" || "+expression2.toString();
        return "";
    }
}


