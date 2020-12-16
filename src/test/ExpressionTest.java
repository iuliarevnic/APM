package test;

import exceptions.MyException;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceHeap;
import model.collections.MyDictionary;
import model.collections.MyHeap;
import model.expressions.ArithmeticExpression;
import model.expressions.InterfaceExpression;
import model.expressions.LogicalExpression;
import model.expressions.ValueExpression;
import model.types.BoolType;
import model.types.IntType;
import model.types.InterfaceType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.InterfaceValue;
import model.values.StringValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExpressionTest {
    @Test
    public void arithmeticExpressionTest(){
        InterfaceDictionary<String, InterfaceValue> symbolTable=new MyDictionary<>();
        InterfaceHeap<InterfaceValue> heap=new MyHeap<>();
        InterfaceExpression multiplication=new ArithmeticExpression(new ValueExpression(new IntValue(5)),new ValueExpression(new IntValue(17)),"*");
        InterfaceValue result;
        try{
            result=multiplication.evaluate(symbolTable,heap);
            assertEquals(result.getValue(),85);

        }catch(MyException exception)
        {
            System.out.println(exception.getMessage());
        }
        InterfaceExpression divisionByZero=new ArithmeticExpression(new ValueExpression(new IntValue(25)),new ValueExpression(new IntValue(0)),"/");
        try{
            result=divisionByZero.evaluate(symbolTable,heap);
        }catch(MyException exception)
        {
            assertEquals("Division by zero",exception.getMessage());
            //System.out.println(exception.getMessage());
        }
        InterfaceExpression addition=new ArithmeticExpression(new ValueExpression(new IntValue(29)),new ValueExpression(new IntValue(28)),"+");
        try{
            result=addition.evaluate(symbolTable,heap);
            assertEquals(57,result.getValue());
        }catch (MyException exception)
        {
            System.out.println(exception.getMessage());
        }
        InterfaceExpression subtraction=new ArithmeticExpression(new ValueExpression(new IntValue(35)),new ValueExpression(new IntValue(8)),"-");
        try{
            result=subtraction.evaluate(symbolTable,heap);
            assertEquals(27,result.getValue());
        }catch(MyException exception)
        {
            System.out.println(exception.getMessage());
        }
        String subtractionString=subtraction.toString(),additionString=addition.toString(),multiplicationString=multiplication.toString(),divisionString=divisionByZero.toString();
        assertEquals("29 + 28",additionString);
        assertEquals("5 * 17",multiplicationString);
        assertEquals("25 / 0",divisionString);
        assertEquals("35 - 8",subtractionString);
        addition=new ArithmeticExpression(new ValueExpression(new StringValue("this is a string")),new ValueExpression(new IntValue(5)),"+");
        try{
            addition.evaluate(symbolTable,heap);
        }catch(MyException exception)
        {
            assertEquals("First operand is not an integer",exception.getMessage());
        }
        multiplication=new ArithmeticExpression(new ValueExpression(new IntValue(975)),new ValueExpression(new BoolValue(true)),"*");
        try{
            multiplication.evaluate(symbolTable,heap);
        }catch(MyException exception)
        {
            assertEquals("Second operand is not an integer",exception.getMessage());
        }
        InterfaceDictionary<String,InterfaceType> assignedTypes=new MyDictionary<>();
        InterfaceType typeResult;
        try{
            typeResult=subtraction.typecheck(assignedTypes);
            assertTrue(typeResult instanceof IntType);
        }catch(MyException exception)
        {
            System.out.println(exception.getMessage());
        }
        try{
            typeResult=addition.typecheck(assignedTypes);
        }catch (MyException exception)
        {
            assertEquals("First operand is not an integer",exception.getMessage());
        }
        try{
            typeResult= multiplication.typecheck(assignedTypes);
        }catch(MyException exception)
        {
            assertEquals("Second operand is not an integer",exception.getMessage());
        }
        InterfaceExpression division=new ArithmeticExpression(new ValueExpression(new IntValue(25559)),new ValueExpression(new IntValue(7)),"/");
        try{
            result=division.evaluate(symbolTable,heap);
            assertEquals(3651,result.getValue());
        }catch(MyException exception)
        {
            System.out.println(exception.getMessage());
        }
    }
    @Test
    public void logicalExpressionTest(){
        InterfaceDictionary<String,InterfaceValue> symbolTable=new MyDictionary<>();
        InterfaceHeap<InterfaceValue> heap=new MyHeap<>();
        InterfaceExpression and=new LogicalExpression(new ValueExpression(new BoolValue(true)),new ValueExpression(new BoolValue(false)),"&&");
        InterfaceValue result;
        try{
            result=and.evaluate(symbolTable,heap);
            assertEquals(false,result.getValue());
        }catch (MyException exception)
        {
            System.out.println(exception.getMessage());
        }
        InterfaceExpression or=new LogicalExpression(new ValueExpression(new BoolValue(true)),new ValueExpression(new BoolValue(true)),"||");
        try{
            result=or.evaluate(symbolTable,heap);
            assertEquals(true,result.getValue());
        }catch (MyException exception)
        {
            System.out.println(exception.getMessage());
        }
        InterfaceDictionary<String,InterfaceType> assignedTypes=new MyDictionary<>();
        InterfaceType typeResult;
        try{
            typeResult=and.typecheck(assignedTypes);
            assertTrue(typeResult instanceof BoolType);
        }catch (MyException exception)
        {
            System.out.println(exception.getMessage());
        }
        and=new LogicalExpression(new ValueExpression(new StringValue("this is a string")),new ValueExpression(new IntValue(597)),"&&");
        try
        {
            and.evaluate(symbolTable,heap);
        }catch (MyException exception)
        {
            assertEquals("First operand is not boolean",exception.getMessage());
        }


    }
    @Test
    public void relationalExpressionTest(){

    }
    @Test
    public void readHeapExpression(){

    }
    @Test
    public void valueExpressionTest(){

    }
    @Test
    public void variableExpressionTest(){

    }

}
