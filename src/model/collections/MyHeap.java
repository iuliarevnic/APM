package model.collections;

import model.values.InterfaceValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MyHeap<V> implements InterfaceHeap<V> {
    private Integer nextMemorySpace;
    private Map<Integer,V> table;

    public MyHeap(){
        this.table=new ConcurrentHashMap<>();
        this.nextMemorySpace=1;
        //table.put(0,null);
    }
    @Override
    public boolean isDefined(Integer id) {
        return table.containsKey(id);
    }

    @Override
    public void update(Integer id, V value) {
         table.put(id,value);
    }

    @Override
    public void add(V value) {
         table.put(nextMemorySpace,value);
         nextMemorySpace+=1;
    }

    @Override
    public Integer getCurrentFree() {
        return nextMemorySpace;
    }

    @Override
    public Set<Map.Entry<Integer, V>> getEntrySet() {
        return this.table.entrySet();
    }


    @Override
    public V getValue(Integer key) {
        return table.get(key);
    }

    @Override
    public void setContent(Map<Integer,V> newContent) {
        this.table= newContent;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
         table.keySet().forEach(key -> {
            string.append(key.toString()).append(" --> ");
            if (table.get(key) == null)
                string.append("null").append("\n");
            else
                string.append(table.get(key).toString()).append("\n");
        });
        return string.toString();
    }
}
