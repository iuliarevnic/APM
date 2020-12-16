package model.expressions;

import exceptions.MyException;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceHeap;
import model.types.BoolType;
import model.types.IntType;
import model.types.InterfaceType;
import model.values.BoolValue;
import model.values.InterfaceValue;

public class RelationalExpression implements InterfaceExpression {
    private InterfaceExpression expression1;
    private InterfaceExpression expression2;
    private int op;//1:<,2:<=,3:==,4:!=,5:>,6:>=
    public RelationalExpression(InterfaceExpression expression1,InterfaceExpression expression2,String givenOp)
    {
        this.expression1=expression1;
        this.expression2=expression2;
        if(givenOp=="<")
            op=1;
        if(givenOp=="<=")
            op=2;
        if(givenOp=="==")
            op=3;
        if(givenOp=="!=")
            op=4;
        if(givenOp==">")
            op=5;
        if(givenOp==">=")
            op=6;
    }
    @Override
    public InterfaceValue evaluate(InterfaceDictionary<String, InterfaceValue> symbolTable, InterfaceHeap<InterfaceValue> heap) throws MyException {
        InterfaceValue firstValue,secondValue;
        firstValue=expression1.evaluate(symbolTable,heap);
        if(firstValue.getType().equals(new IntType()))
        {
            secondValue=expression2.evaluate(symbolTable,heap);
            if(secondValue.getType().equals(new IntType()))
            {
                int firstOperand,secondOperand;
                firstOperand=(int)firstValue.getValue();
                secondOperand=(int)secondValue.getValue();
                if(op==1)
                    return new BoolValue(firstOperand<secondOperand);
                if(op==2)
                    return new BoolValue(firstOperand<=secondOperand);
                if(op==3)
                    return new BoolValue(firstOperand==secondOperand);
                if(op==4)
                    return new BoolValue(firstOperand!=secondOperand);
                if(op==5)
                    return new BoolValue(firstOperand>secondOperand);
                if(op==6)
                    return new BoolValue(firstOperand>=secondOperand);
            }
            else throw  new MyException("Second operand is not an integer.");
        }
        else throw new MyException("First operand is not an integer.");
        return null;
    }

    @Override
    public InterfaceType typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        InterfaceType firstType,secondType;
        firstType=expression1.typecheck(assignedTypes);
        secondType=expression2.typecheck(assignedTypes);
        if(firstType.equals(new IntType()))
        {
            if(secondType.equals(new IntType()))
            {
                return new BoolType();
            }
            else throw new MyException("Second operand is not an integer.");
        }
        else throw new MyException("First operand is not an integer.");
    }

    @Override
    public String toString() {
        String result=expression1.toString();
        if(op==1)
            result+="<";
        if(op==2)
            result+="<=";
        if(op==3)
            result+="==";
        if(op==4)
            result+="!=";
        if(op==5)
            result+=">";
        if(op==6)
            result+=">=";
        result+=expression2.toString();
        return result;
    }
}
