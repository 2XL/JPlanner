package lineal.stack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by j on 26/01/2016.
 */
public class Broker {

    // keep track of addlist candidates and adjacency candidates
    List<String> boxes;
    List<String> offices;
    int dimension;
    Map<String, List<String>> adjacent;


    public Broker(HashMap<String, List> map, HashMap adjacent){
        // System.out.println(map);
        this.adjacent = adjacent;
        this.boxes = map.get("Boxes");
        this.offices = map.get("Offices");
        System.out.println(this.boxes);
        System.out.println(this.offices);
        System.out.println(this.adjacent);


    }

    public O getOperator(P p){

        // given a p -> precondition
        // return a operator that might fulfill it.
        // at their add list

        return null;
    }
}
