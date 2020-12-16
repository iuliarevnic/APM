package model.values;

import model.types.*;

public class IntValue implements InterfaceValue{
    private int value;
    public IntValue(int i) {
        this.value=i;
    }
    public String toString()
    {
        return Integer.toString(value);
    }
    @Override
    public InterfaceType getType() {
        return new IntType();
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof IntValue;
    }
}
