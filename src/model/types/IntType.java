package model.types;

import model.values.*;

public class IntType implements InterfaceType{

    public String toString(){
        return "int";
    }
    @Override
    public InterfaceValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof  IntType;
    }
}
