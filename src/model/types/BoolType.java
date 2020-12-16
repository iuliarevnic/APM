package model.types;

import model.values.*;

public class BoolType implements InterfaceType{
    public String toString(){
        return "bool";
    }

    @Override
    public InterfaceValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof BoolType;
    }
}
