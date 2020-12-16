package model.values;

import model.types.InterfaceType;
import model.types.ReferenceType;

public class ReferenceValue implements InterfaceValue{
    private int address;
    private InterfaceType locationType;
    public ReferenceValue(int address, InterfaceType locationType)
    {
        this.address=address;
        this.locationType=locationType;
    }
    @Override
    public InterfaceType getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public Object getValue() {
        return locationType;
    }

    @Override
    public String toString() {
        return "( "+Integer.toString(address)+" , "+locationType.toString()+" )";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ReferenceValue;
    }
    public int getAddress()
    {
        return this.address;
    }
    public void setAddress(int newAddress)
    {
        this.address=newAddress;
    }
}
