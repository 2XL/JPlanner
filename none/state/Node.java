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
    public List<Node> expand(){
        List<Node> expansion = new LinkedList<>();
        return expansion;
    }

    public boolean isSame(Node n){
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Deque<Node> getPlan(){
        Deque<Node> deque = new LinkedList<>();
        return deque;
    }

    public Deque<_Predicate> getDiff(Node node){
        Deque<_Predicate> diff = new LinkedList<>();

        return diff;
    }
}
