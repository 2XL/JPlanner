package lineal.stack.ops;

import lineal.stack.P;

/**
 * Created by j on 16/01/2016.
 */
public class Adjacent extends P {

    String o1;
    String o2;

    // this class is static
    public Adjacent() {
        // empty constructor to leave the variables undefined
    }

    public Adjacent(String o1, String o2) {
        this.o1 = o1;
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
        return "Adjacent(" + this.o1 + "," + this.o2 + ")";
    }
}
