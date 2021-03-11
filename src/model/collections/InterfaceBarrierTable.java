package model.collections;

import javafx.util.Pair;

import java.util.Map;
import java.util.Set;

public interface InterfaceBarrierTable<V> {
    void add(V number);
    Integer getCurrentOccupied();
    Pair<V, MyList<Integer>> get(Integer key);
    Set<Map.Entry<Integer,Pair<V,MyList<Integer>>>> getEntrySet();
    boolean isDefined(Integer index);
}
