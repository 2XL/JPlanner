package planner.naive;

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
        this.mazeIni = new Building(building); // initial state
        this.mazeFin = new Building(building); // final state
        this.maze = new Building(building); // todo state
        System.out.println(this.maze);
        System.out.println(this.mazeIni);
        System.out.println(this.mazeFin);

    }


    public List<String> resolve(){
        List<String> result; // ordered list of the steps performed to obtain the final result
        result = new ArrayList<String>();



        this.maze.prettyPrint();
        this.maze.applyState(0);
        this.maze.prettyPrint();
        // this.mazeIni.prettyPrint();
        // this.mazeFin.applyState(1);

        // this.mazeFin.prettyPrint();
        // the current is this.maze and has to be converted into final




        return result;
    }

    // here todo the recursive function

    // todo define the state

    // todo next() means it retrieves on layer deeper by applying the planner



}
