package none.operator;

import none.building.Office;
import none.predicate.*;
import none.state.State;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by j on 12/12/2015.
 */
public class Move extends _Operator {
    Office office;
    Office nextOffice;
    State parent;

    public Move(Office o1, Office o2, State parent) {
        super(parent.getPredicates());
        this.office = o1;
        this.nextOffice = o2;
        this.parent = parent;

    }


    public boolean check() {

        if (this.office.isAdjacent(this.nextOffice) &&
                precondition.containsKey("Robot-Location(" + this.office.name + ")")) {
            //&& this.office.listAdjacents().contains(this.nextOffice))

            return true;
        }

        return false;
    }

    public void add() {
        //this.precondition.add(RobotLocation);
        _Predicate p = new RobotLocation(this.nextOffice);
        this.precondition.put(p.toString(), p);
    }

    public void remove() {
        //this.precondition.remove(RobotLocation);
        this.precondition.remove("Robot-Location(" + this.office.name + ")");
    }

    public State apply() {

        State result = null;


        if (!this.check()) {
            // none possible
        } else {
            this.add();
            this.remove();
            result = new State(
                    this.nextOffice,
                    new TreeSet<_Predicate>(this.precondition.values()),
                    this.parent,
                    this);
        }


        System.out.println("Move instance ");
        return result;
    }

    @Override
    public String toString() {
        return "Move("+this.office.name+","+this.nextOffice.name+")";
    }
}
