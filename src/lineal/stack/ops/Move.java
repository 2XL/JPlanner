package lineal.stack.ops;

import lineal.stack.E;
import lineal.stack.O;
import lineal.stack.P;
import lineal.stack.State;

import java.util.List;

/**
 * Created by j on 16/01/2016.
 */
public class Move extends O {

    String o1;
    String o2;


    public Move() {
        // constructor vacio
    }

    public Move(String o1, String o2) {
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
    public void add(E e){
        String str =  "RobotLocation("+this.o2+")";
    }

    public void remove(E e){
        String str = "RobotLocation("+this.o1+")";
    }

    public void apply(State s){
        this.add(s);
        this.remove(s);
    }
}
