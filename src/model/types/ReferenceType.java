package model.types;

import model.values.InterfaceValue;
import model.values.ReferenceValue;

public class ReferenceType implements InterfaceType {
    private InterfaceType inner;
    public ReferenceType(InterfaceType inner)
    {
        this.inner=inner;
    }
    public ReferenceType()
    {
        this.inner=null;
    }
    @Override
    public boolean equals(Object object) {
        if(object instanceof ReferenceType)
            return inner.equals(((ReferenceType)object).getInner());
        else return false;
    }

    @Override
    public String toString() {
        return "Reference("+this.inner.toString()+")";
    }

    @Override
    public InterfaceValue defaultValue() {
        return new ReferenceValue(0,inner);
    }

    public InterfaceType getInner() {
        return inner;
    }
}
