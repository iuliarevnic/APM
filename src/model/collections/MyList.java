package model.collections;

import java.util.ArrayList;

public class MyList<T> implements InterfaceList<T> {
    private ArrayList<T> list;
    public MyList()
    {
        list=new ArrayList<T>();
    }
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean add(T element) {
        return list.add(element);
    }

    @Override
    public ArrayList<T> getAll() {
        return list;
    }

    @Override
    public boolean isDefined(Integer key) {
        return list.contains(key);
    }

    @Override
    public T getElement(int index) {
        return list.get(index);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
