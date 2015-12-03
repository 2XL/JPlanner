package planner.graph;

import java.util.*;

/**
 * Created by x on 3/12/15.
 */
public class Office{

    Map<String, Box> box_list;
    Map<String, Office> adjacent_list;  // static
    String name; // static

    public Office(String name){
        this.name = name;
        this.adjacent_list = new HashMap<String, Office>();
    }

    public void putAdjacent(Office o){
        adjacent_list.put(o.name, o);
    }
    public Set<String> listAdjacent(){
        return adjacent_list.keySet();
    }

}
