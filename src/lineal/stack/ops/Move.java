package lineal.stack.ops;

import lineal.stack.*;
import lineal.stack.pre.Adjacent;
import lineal.stack.pre.RobotLocation;

import java.util.List;

/**
 * Created by j on 16/01/2016.
 */
public class Move extends O {

    String o1;
    String o2;


    public Move() {

        this.type = "Move";
        // constructor vacio
    }

    public Move(String o1, String o2) {

        this.type = "Move";
        this.o2 = o2;
        this.o1 = o1;
        this.defined = true;
    }

    public String getO1() {
        return o1;
    }

    public void setO1(String o1) {
        this.o1 = o1;
    }

    public String getO2() {
        return o2;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    @Override
    public String toString() {
        // return super.toString();
        return "Move(" + this.o1 + "," + this.o2 + ")";
    }

    public boolean check(List<P> p){
        String precond = "Robot-location("+this.o1+")";


        for(String prec : precond.split(":")){
            if(p.contains(prec)){
                // ok
            }else{
                return false;
            }
        }
        return true;
    }
    @Override
    public void add(State e){
        RobotLocation rl = new RobotLocation(this.o2);
        e.setRobotLocation(rl);
        // String str =  "RobotLocation("+this.o2+")";
        e.addPre(rl);
    }
    @Override
    public void remove(State e){
        RobotLocation rl = new RobotLocation(this.o1);
        // String str = "RobotLocation("+this.o1+")";
        e.removePre(rl);
    }

    public void apply(State s){
        System.out.println("Apply Move");

        this.add(s);
        this.remove(s);
    }

    @Override
    public PList getPredList(){
        PList pl = new PList();
        pl.addCond(new RobotLocation(this.o1));
        pl.addCond(new Adjacent(this.o1, this.o2));
        return pl;
    }



}
