package planner.graph;

/**
 * Created by x on 3/12/15.
 */
public class Robot {
    Office office;

    public Robot(Office o){
        this.office = o;
    }

    public Robot(){
        this.office = new Office("NONE");

    }


}