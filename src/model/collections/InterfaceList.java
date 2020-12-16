package model.collections;

import java.util.ArrayList;

public interface InterfaceList<T> {int size();
    String toString();
    boolean add(T element);
    ArrayList<T> getAll();
    T getElement(int index);
}
