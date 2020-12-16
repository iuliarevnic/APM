package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.collections.InterfaceHeap;
import model.expressions.InterfaceExpression;
import model.types.InterfaceType;
import model.types.ReferenceType;
import model.values.IntValue;
import model.values.InterfaceValue;
import model.values.ReferenceValue;
import model.values.StringValue;

import java.util.Map;

public class NewStatement implements InterfaceStatement {
    private StringValue variableName;
    private InterfaceExpression expression;
    public NewStatement(StringValue variableName, InterfaceExpression expression)
    {
        this.variableName=variableName;
        this.expression=expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        synchronized(state.getHeap()){
            if(state.getSymbolTable().containsKey(variableName.toString())&&state.getSymbolTable().get(variableName.toString()).getType() instanceof ReferenceType)
            {
                try{
                    InterfaceValue temp=expression.evaluate(state.getSymbolTable(),state.getHeap());
                    ReferenceValue valueSymbolTable=(ReferenceValue)state.getSymbolTable().get(variableName.toString());
                    InterfaceHeap<InterfaceValue> heap=state.getHeap();

                    if(temp.getType().equals(valueSymbolTable.getValue()))
                    {
                        ReferenceValue newVal=new ReferenceValue((Integer)heap.getCurrentFree(),temp.getType());
                        newVal.setAddress((Integer)heap.getCurrentFree());
                        state.getSymbolTable().put(variableName.toString(),newVal);
                        heap.add(temp);
                    /*
                    for(Map.Entry<Integer,InterfaceValue> element: heap.getEntrySet() )
                    {
                        if(element.getValue().getType() instanceof ReferenceValue)
                        {
                            ReferenceValue newrefvalue=(ReferenceValue)element.getValue();
                            if(newrefvalue.getAddress()==valueSymbolTable.getAddress())
                            {
                                IntValue intvalue= (IntValue) temp;
                                newrefvalue.setAddress(Integer.valueOf((Integer) intvalue.getValue()));
                                element.setValue(newrefvalue);
                            }
                        }
                    }*/
                    }

                }
                catch (MyException ex){
                    throw new MyException("Error newStatement expression.");}
            }
            else throw new MyException("Variable not declared or not referencetype.");
        }
        return null;
    }

    @Override
    public String toString() {
        return "new( "+variableName+" ,"+expression.toString()+" )";
    }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        InterfaceType variableType,expressionType;
        variableType=assignedTypes.lookup(variableName.toString());
        expressionType=expression.typecheck(assignedTypes);
        if(variableType.equals(new ReferenceType(expressionType)))
            return assignedTypes;
        else throw new MyException("Type not reference type.");
    }

    @Override
    public InterfaceStatement deepCopy() {
        return new NewStatement(variableName,expression);
    }
}
