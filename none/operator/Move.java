package none.operator;

import none.building.Office;
import none.predicate.Adjacent;
import none.predicate.Dirty;
import none.predicate.Empty;
import none.predicate.RobotLocation;

/**
 * Created by j on 12/12/2015.
 */
public class Move extends _Operator {
    Office office;
    Office nextOffice;
    public Move(Office o1, Office o2) {
        this.office = o1;
        this.nextOffice = o2;
        this.priority = 2;

    }


    public boolean check() {
        if (this.precondition.contains(Adjacent.class.getSimpleName()))
            if (this.precondition.contains(RobotLocation.class.getSimpleName()))
                    return false;
        return true;
    }

    public void add(){
         //this.precondition.add(RobotLocation);
    }

    public void remove(){
        //this.precondition.remove(RobotLocation);
    }

}
