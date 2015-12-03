package planner.graph;

/**
 * Created by x on 3/12/15.
 */
public interface Predicate {

    public void Robot_location(String o);
    public void Box_location(String b, String o);
    public void Dirty(String o);
    public void Clean(String o);
    public void Empty(String o);
    public void Adjacent(String a, String b);
}
