package lineal.stack.ops;

import lineal.stack.P;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by j on 16/01/2016.
 */
public class BoxLocation extends P {

    String o;
    String b;

    public BoxLocation() {
        // empty constructor to leave the variables undefined
    }

    public BoxLocation(String b, String o) {
        this.o = o;
        this.b = b;
    }



    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    @Override
    public String toString() {

        // return super.toString();
        return "BoxLocation(" + this.b + "," + this.o + ")";
    }


    public void setAttr(String str) {
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher match = pattern.matcher(str);
        if (match.find()) {
            String[] methodArgs = (match.group(1)).split(",");
            this.o = methodArgs[1];
            this.b = methodArgs[0];
        }
    }
}
