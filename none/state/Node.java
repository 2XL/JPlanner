package none.state;

import none.predicate._Predicate;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by j on 12/12/2015.
 */
public abstract class Node {
    Node parent;
    Set<Node> child;
    List<_Predicate> predicates;


    public boolean isSame(Node n){
        return true;
    }


    public Deque<Node> getPlan(){
        Deque<Node> deque = new LinkedList<>();
        return deque;
    }

    public Deque<_Predicate> getDiff(Node node){
        Deque<_Predicate> diff = new LinkedList<>();
        return diff;
    }

    public List<_Predicate> getPredicates(){
        return this.predicates;
    }


}
