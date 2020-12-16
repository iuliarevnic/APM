package model.collections;

import model.values.InterfaceValue;

import java.util.Map;
import java.util.Set;

public interface InterfaceHeap<V> {
    boolean isDefined(Integer id);
    void update(Integer id, V value);
    void add(V value);
    Integer getCurrentFree();
    Set<Map.Entry<Integer,V>> getEntrySet();
    V getValue(Integer key);
    void setContent(Map<Integer,V> newContent);
}
