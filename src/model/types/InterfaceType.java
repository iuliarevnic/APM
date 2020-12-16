package model.types;


import model.values.InterfaceValue;

public interface InterfaceType {
    boolean equals(Object objects);
    InterfaceValue defaultValue();
}


