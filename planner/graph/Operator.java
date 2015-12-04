package planner.graph;

/**
 * Created by x on 3/12/15.
 */
public interface Operator {

    public boolean Clean_office(String office);
    public boolean Move(String office1, String office2);
    public boolean Push(String box, String office1, String office2);
}
