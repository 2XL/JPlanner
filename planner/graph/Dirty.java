package planner.graph;

import java.util.HashMap;
import java.util.List;

/**
 * Created by x on 3/12/15.
 */
public class Dirty {
    HashMap<String, Office> offices;

    public Dirty() {

        this.offices = new HashMap<String, Office>();
    }

    public void addDirty(Office o) {
        // if (!this.offices.containsKey(o))
            this.offices.put(o.name, o);
    }

    public void removeDirty(Office o) {
        // if(this.offices.containsKey(o))
            this.offices.remove(o.name);
    }

    public boolean isDirty(Office o){
        return this.offices.containsKey(o.name);
    }

}
