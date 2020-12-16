package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.collections.InterfaceDictionary;
import model.expressions.InterfaceExpression;
import model.types.InterfaceType;
import model.values.InterfaceValue;

public class AssignmentStatement implements InterfaceStatement{
    private String id;
    private InterfaceExpression expression;

    public AssignmentStatement(String Id, InterfaceExpression expression)
    {
        this.id=Id;
        this.expression=expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceDictionary<String, InterfaceValue> symbolTable= state.getSymbolTable();
        if(symbolTable.isDefined(id)) {
            InterfaceValue value = expression.evaluate(symbolTable, state.getHeap());
            InterfaceType idType = (symbolTable.lookup(id)).getType();
            if (value.getType().equals(idType)){
                symbolTable.update(id, value);}
            else throw new MyException("declared type of variable" + id + " and type of  the assigned expression do not match");
        }
        else throw new MyException("the used variable" +id + " was not declared before");
        return null;
        }

    @Override
    public InterfaceDictionary<String, InterfaceType> typecheck(InterfaceDictionary<String, InterfaceType> assignedTypes) throws MyException {
        InterfaceType variableType=assignedTypes.lookup(id);
        InterfaceType expressionType=expression.typecheck(assignedTypes);
        if(variableType.equals(expressionType))
            return assignedTypes;
        else
            throw new MyException("Assignment variable type doesn't match expression type\n");

    }

    @Override
    public InterfaceStatement deepCopy() {
        return new AssignmentStatement(id,expression);
    }

    @Override
    public String toString() {
        return id+" = "+expression.toString();
    }
}
