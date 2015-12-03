package planner.graph;

/**
 * Created by x on 3/12/15.
 */
public interface Predicate {

    public boolean Robot_location(String o);
    public boolean Box_location(String b, String o);
    public boolean Dirty(String o);
    public boolean Clean(String o);
    public boolean Empty(String o);
    public boolean Adjacent(String a, String b);
}
