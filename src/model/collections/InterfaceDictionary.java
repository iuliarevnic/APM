package model.collections;

import exceptions.MyException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InterfaceDictionary<K,V> {
    V get(K key);
    void put(K key, V value);
    void update(K key, V value);
    String toString();
    V lookup(K key)throws MyException;
    Set<Map.Entry<K,V>> getEntrySet();
    void remove(K key);
    boolean isDefined(K key);
    Set<K> keySet();
    Iterable<Map.Entry<K,V>> getAll();
    InterfaceDictionary<K,V> deepCopy();
    boolean containsKey(K key);
    List<V> getValues();
}
