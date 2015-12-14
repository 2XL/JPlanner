package none.operator;

import none.building.Office;
import none.predicate.Dirty;
import none.predicate.Empty;
import none.predicate.RobotLocation;
import none.predicate._Predicate;

import java.util.List;

/**
 * Created by j on 12/12/2015.
 */
public class CleanOffice extends _Operator {

    Office office;

    public CleanOffice(Office o) {
        this.office = o;
        this.priority = 0;
    }


    public boolean check() {
        if (this.precondition.contains(Dirty.class.getSimpleName()))
            if (this.precondition.contains(RobotLocation.class.getSimpleName()))
                if (this.precondition.contains(Empty.class.getSimpleName()))
                    return false;

        return true;
    }

    public void add(){
        // this.precondition.add(CleanOffice);
    }

    public void remove(){
        // this.precondition.remove(Dirty);
    }

}
