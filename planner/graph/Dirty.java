package planner.graph;

import java.util.HashMap;
import java.util.List;

/**
 * Created by x on 3/12/15.
 */
public class Dirty {
    HashMap<String, Office> offices;

    public Dirty() {
        this.offices = new HashMap<>();
    }

    public void addDirty(Office o) {
        this.offices.put(o.name, o);
    }

    public void removeDirty(Office o) {
        this.offices.remove(o);
    }

    public boolean isDirty(Office o){
        return this.offices.containsKey(o.name);
    }

}
