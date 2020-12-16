package model.types;

import model.values.InterfaceValue;
import model.values.StringValue;

public class StringType implements InterfaceType{
    @Override
    public InterfaceValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof StringType;
    }
}
