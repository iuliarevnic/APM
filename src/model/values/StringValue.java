package model.values;

import model.types.InterfaceType;
import model.types.StringType;

public class StringValue implements  InterfaceValue{
    private String string;
    public StringValue(String string)
    {
        this.string=string;
    }
    @Override
    public InterfaceType getType() {
        return new StringType();
    }

    @Override
    public Object getValue() {
        return string;
    }

    @Override
    public String toString() {
        return this.string;
    }

    @Override
    public int hashCode() {
        return string.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof StringValue)
        {
            StringValue stringObject=(StringValue)object;
            if(stringObject.getValue().equals(string))
                return true;
        }
        return false;
    }
}
