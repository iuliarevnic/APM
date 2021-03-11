package model.collections;

import java.util.ArrayList;

public interface InterfaceList<T> {int size();
    String toString();
    boolean add(T element);
    ArrayList<T> getAll();
    boolean isDefined(Integer key);
    T getElement(int index);
}
