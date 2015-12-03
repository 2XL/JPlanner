package planner.naive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by x on 1/12/15.
 */
public class Planner {
    Building mazeIni;
    Building mazeFin;

    public Planner(Building mazeIni, Building mazeFin){
        System.out.println(this.mazeIni);
        System.out.println(this.mazeFin);
        this.mazeFin = mazeFin;
        this.mazeIni = mazeIni;

    }


    public List<String> resolve(){
        List<String> result; // ordered list of the steps performed to obtain the final result
        result = new ArrayList<String>();
        List<Object> states;


        int initialHashIni = this.mazeIni.getStateHash();
        this.mazeIni.applyState(0);
        int finalHashIni = this.mazeIni.getStateHash();

        // todo ExtractState()


        int initialHashFin = this.mazeFin.getStateHash();
        this.mazeFin.applyState(1);
        int finalHashFin = this.mazeFin.getStateHash();

        System.out.print(this.mazeFin.apply());
        //

        // Apply action...
        System.out.println("\n");
        System.out.println(initialHashIni + " -> "+ finalHashIni);
        System.out.println(initialHashFin + " -> "+ finalHashFin);




        // this.mazeFin.prettyPrint();
        // the current is this.maze and has to be converted into final




        return result;
    }

    // here todo the recursive function

    // todo define the state

    // todo next() means it retrieves on layer deeper by applying the planner



}
