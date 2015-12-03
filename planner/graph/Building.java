package planner.graph;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by x on 3/12/15.
 */
public abstract class Building{

    HashMap<String, Office> offices;
    HashMap<String, Box> boxes;
    Robot robot;
    Dirty dirty;

    public Building(){

    }

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
        // this.loadSetup(setup);
    }


    public List<String> getSetup() {
        // export only the operations
        List<String> state = new ArrayList<>();
        for (String key : this.offices.keySet()) {
            Office o = this.offices.get(key);
            if(this.dirty.offices.containsKey(o)){
                state.add("Dirty("+o.name+")");
            }else{
                state.add("Clean("+o.name+")");
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
        return state;
    }

    public int getSetupHash(List<String> setup){
        int result = 0;
        for(String key : setup){
            result += key.hashCode();
        }
        return result;
    }

    //

}
