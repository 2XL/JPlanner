package none.operator;

import none.building.Box;
import none.building.Office;
import none.predicate.*;

/**
 * Created by j on 12/12/2015.
 */
public class Push extends _Operator {

    Box box;
    Office office;
    Office nextOffice;

    public Push(Box b, Office o1, Office o2) {
        this.box = b;
        this.office = o1;
        this.nextOffice = o2;
        this.priority = 1;
    }

    public boolean check() {
        if (this.precondition.contains(BoxLocation.class.getSimpleName()))
            if (this.precondition.contains(RobotLocation.class.getSimpleName()))
                if (this.precondition.contains(Adjacent.class.getSimpleName()))
                    if (this.precondition.contains(Empty.class.getSimpleName()))
                        return false;

        return true;
    }

    public void add() {
        //this.precondition.add(BoxLocation);
        //this.precondition.add(RobotLocation);
        //this.precondition.add(Empty);
    }

    public void remove() {
        // this.precondition.remove(BoxLocation);
        // this.precondition.remove(Empty);
        // this.precondition.remove(RobotLocation);
    }

}
