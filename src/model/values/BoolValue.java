package model.values;

import model.types.*;

public class BoolValue implements InterfaceValue{
    private boolean value;
    public BoolValue(boolean value)
    {
        this.value=value;
    }
    public String toString() {
        if(value==false)
            return "false";
        else return "true";
    }
    @Override
    public InterfaceType getType() {
        return new BoolType();
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof BoolValue;
    }
}

