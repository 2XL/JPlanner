package lineal.stack.pre;

import lineal.stack.E;
import lineal.stack.O;

/**
 * Created by j on 16/01/2016.
 */
public class Push extends O {

    String b;
    String o1;
    String o2;

    public Push(String b, String o1, String o2) {
        this.b = b;
        this.o1 = o1;
        this.o2 = o2;
    }

    public Push() {

    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getO2() {
        return o2;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    public String getO1() {
        return o1;
    }

    public void setO1(String o1) {
        this.o1 = o1;
    }

    @Override
    public String toString() {

        // return super.toString();
        return "Push(" + this.b + "," + this.o1 + "," + this.o2 + ")";
    }

    public void add(E e) {
        String str = "BoxLocation(" + this.b + "," + this.o2 + "):RobotLocation(" + this.o2 + "):Empty(" + this.o1 + ")";
    }

    public void remove(E e) {
        String str = "BoxLocation(" + this.b + "," + this.o1 + "):RobotLocation(" + this.o1 + "):Empty(" + this.o2 + ")";
    }

}
