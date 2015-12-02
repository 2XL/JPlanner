package planner.naive;

import java.util.HashMap;

/**
 * Created by x on 1/12/15.
 */
public class Robot {
    HashMap<String, Office> offices;
    Office office;
    public Robot(HashMap<String, Office> office){
        this.offices = office;
    }

    protected void setOffice(String o){
        this.office = this.offices.get(o);
    }

    /**
     * TODO appy class where the Robot can choose the next step to forward
     * @param office
     * @return
     */
    public  boolean Clean_office(String office){

        // check the precondition
        if(this.office.name != office)
            return false;
        if(this.office.isDirty == true)
            return false;
        if(this.office.boxes.size() != 0)
            return false;

        // apply action
        this.office.isDirty = false;

        // delete action
        return (this.office.isDirty == false);
    }

    /**
     *
     * @param office1: initial office position
     * @param office2: after apply position
     * @return
     */
    public boolean Move(String office1, String office2){

        // precondition
        if(this.office.name != office1)
            return false;
        if(this.office.isAdjacent(office2) == false)
            return false;


        // apply action
        this.office = this.office.getOffice(office2);

        // delete action
        return (this.office.name != office1 && this.office.name == office2 && this.office.name == office2);

    }


    /**
     *
     * @param box
     * @param office1
     * @param office2
     * @return
     */
    public boolean Push(String box, String office1, String office2){

        // precondition
        if(this.office.name != office1)
            return false;
        if(this.office.isAdjacent(office2) == false)
            return false;
        if(this.office.boxes.size() == 0)
            return false;

        // apply action
        this.office.moveBox(box, office2); // removes the box from current office and moves it to the next office
        this.office = this.office.getOffice(office2);
        return (this.office.boxes.size() != 0);



    }






}
