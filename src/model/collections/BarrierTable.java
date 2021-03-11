package model.collections;

import javafx.util.Pair;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BarrierTable<V> implements InterfaceBarrierTable<V> {
    private Integer nextMemorySpace;
    private Map<Integer, Pair<V,MyList<Integer>>> table;
    public BarrierTable()
    {
        this.table=new ConcurrentHashMap<>();
        nextMemorySpace=1;
    }

    @Override
    public void add(V number) {
        table.put(nextMemorySpace,new Pair<>(number,new MyList<>()));
        nextMemorySpace++;
    }

    @Override
    public Integer getCurrentOccupied() {
        return nextMemorySpace-1;
    }

    @Override
    public String toString() {
        String string="";
        for(var key:table.keySet())
        {
            string+= key.toString()+"-->"+table.get(key).toString()+"\n";
        }
        return string;
    }

    @Override
    public Pair<V, MyList<Integer>> get(Integer key) {
        return table.get(key);
    }

    @Override
    public Set<Map.Entry<Integer, Pair<V,MyList<Integer>>>> getEntrySet() {
        return table.entrySet();
    }


    @Override
    public boolean isDefined(Integer index) {
        return table.containsKey(index);
    }
}
