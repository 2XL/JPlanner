package planner.graph;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by x on 3/12/15.
 */
public abstract class Building implements Predicate {

    HashMap<String, Office> offices;
    HashMap<String, Box> boxes;
    Robot robot;
    Dirty dirty;

    public Building(List<Box> boxes, List<Office> offices) {

        // this.offices = offices;
        this.offices = new HashMap<String, Office>();
        this.boxes = new HashMap<String, Box>();

        for (Office o : offices) {
            this.offices.put(o.name, o);
            o.box_list = new HashMap<String, Box>(); // box_list at each office is reset
        }
        //this.boxes = boxes;
        for (Box b : boxes) {
            this.boxes.put(b.name, b);
        }
        this.robot = new Robot(); // un related robot
        this.dirty = new Dirty(); //
        // this.loadSetup(hasSetup);
    }


    public List<String> getSetup() {
        // export only the operations
        List<String> state = new ArrayList<>();
        for (String key : this.offices.keySet()) {
            Office o = this.offices.get(key);
            if (this.dirty.offices.containsKey(o.name)) {
                state.add("Dirty(" + o.name + ")");
            }

            else {
                state.add("Clean(" + o.name + ")");
            }

            if (o.box_list.keySet().size() == 0) {
                state.add("Empty(" + o.name + ")");
            } else {
                for (String box : o.box_list.keySet()) {
                    Box b = o.box_list.get(box);
                    state.add("Box-location(" + b.name + "," + o.name + ")");
                }
            }
        }
        // robot location
        state.add("Robot-location(" + this.robot.office.name + ")");
        Collections.sort(state);
        return state;
    }

    public String getSetupHash(List<String> setup) {
        int result = 0;
        for (String key : setup) {
            result += key.hashCode();
        }
        return String.valueOf(result);
    }


    public boolean Robot_location(String o) {
        //System.out.println("Building Robot_location "+o);
        Office office = this.offices.get(o); // retrieve the office
        this.robot.office = office;
        return this.robot.office.name == o;
    }

    public boolean Box_location(String b, String o) {
        //System.out.println("Building Box_location [" + b + "] " +o);
        Office office = this.offices.get(o);
        Box box = this.boxes.get(b);
        office.box_list.put(box.name, box);
        return office.box_list.containsKey(b);
    }

    public boolean Dirty(String o) {
        //System.out.println("Building Dirty " +o);
        Office office = this.offices.get(o);
        this.dirty.addDirty(office);
        return this.dirty.isDirty(office);
    }

    public boolean Clean(String o) {
        //System.out.println("Building Clean " +o);
        Office office = this.offices.get(o);
        this.dirty.removeDirty(office);
        return !this.dirty.isDirty(office);
    }

    public boolean Empty(String o) {
        // check each box not containing this office key
        //System.out.println("Building Empty " +o);
        Office office = this.offices.get(o);
        for (String b : office.box_list.keySet())
            office.box_list.remove(b);
        return (office.box_list.size() == 0);
        // remove all the boxes int the office list
    }

    public boolean Adjacent(String a, String b) {
        Office office = this.offices.get(a);
        if (office.adjacent_list.containsKey(b)) {
            // do nothing
            // return true;
        } else {
            office.adjacent_list.put(b, this.offices.get(b));
            //return false;
        }

        return (office.adjacent_list.containsKey(b));
        //{
        // do nothing
        //  return true;
        //}else{
        // office.adjacent_list.put(b, this.offices.get(b));
        //  return false;
        // }
    }


}
