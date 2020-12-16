package model.collections;

import model.statements.InterfaceStatement;

import java.util.List;

public interface InterfaceStack<T> {
    T pop();
    void push(T element);
    String toString();
    boolean isEmpty();
    String toString1();

    List<T> getAll();
}
