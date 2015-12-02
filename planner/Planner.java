package planner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x on 1/12/15.
 */
public class Planner {
    Building maze;
    Building mazeIni;
    Building mazeFin;

    public Planner(Building building){
        this.mazeIni = new Building(building);
        this.mazeFin = new Building(building);
        this.maze = new Building(building);
    }


    public List<String> resolve(){
        List<String> result; // ordered list of the steps performed to obtain the final result
        result = new ArrayList<String>();

        this.mazeIni.applyState(0);
        this.mazeFin.applyState(1);

        // the current is this.maze and has to be converted into final




        return result;
    }
}
