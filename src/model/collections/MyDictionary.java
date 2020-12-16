package model.collections;

import exceptions.MyException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyDictionary<K,V> implements InterfaceDictionary<K,V> {
    private Map<K, V> dictionary;

    public MyDictionary() {
        dictionary = new ConcurrentHashMap<K,V>();
    }

    private MyDictionary(ConcurrentHashMap<K,V> map)
    {
        this.dictionary=map;
    }
    @Override
    public V get(K key) {
        return dictionary.get(key);
    }

    @Override
    public void put(K key, V value) {
        dictionary.put(key,value);
    }

    @Override
    public void update(K key, V value) {
        dictionary.put(key,value);
    }

    @Override
    public V lookup(K key)throws MyException {
        if(dictionary.containsKey(key))
         return dictionary.get(key);
        else throw new MyException("Variable not defined");
    }

    @Override
    public Set<Map.Entry<K, V>> getEntrySet() {
        return  dictionary.entrySet();
    }

    @Override
    public void remove(K key) {
        dictionary.remove(key);
    }

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public Set<K> keySet() {
        return dictionary.keySet();
    }

    @Override
    public Iterable<Map.Entry<K, V>> getAll() {
        return dictionary.entrySet();
    }

    @Override
    public InterfaceDictionary<K, V> deepCopy() {
        ConcurrentHashMap<K,V> copy=new ConcurrentHashMap<>();

        for(Map.Entry<K,V> element: dictionary.entrySet())
        {
            copy.put(element.getKey(),element.getValue());
        }
        return new MyDictionary<K,V>(copy);
    }

    @Override
    public boolean containsKey(K key) {
        for(K k: this.dictionary.keySet())
        {
            if(k.toString().equals(key.toString()))
                return true;
        }
        return false;
    }

    @Override
    public List<V> getValues() {
        return new LinkedList<>(this.dictionary.values());
    }

    @Override
    public String toString() {
        String result = "";
        for(K key : dictionary.keySet())
        result+= key.toString()+"->"+dictionary.get(key).toString()+"\n";
        return result;
    }
}
