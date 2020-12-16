package model.values;

import model.types.InterfaceType;

public interface InterfaceValue {
    InterfaceType getType();
    Object getValue();
    boolean equals(Object object);
}
