package lineal.stack.pre;

import lineal.stack.E;
import lineal.stack.O;
import lineal.stack.P;

import java.util.List;

/**
 * Created by j on 16/01/2016.
 */
public class CleanOffice extends O {


    String o;

    public CleanOffice(){

    }

    public CleanOffice(String o){

        this.o = o;
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
        return "CleanOffice(" + this.o + ")";
    }

    public boolean check(List<P> p){
        String precond = "Empty("+this.o+"):Robot-location("+this.o+"):Dirty("+this.o+")";
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
        String str =  "Clean("+this.o+")";
    }

    public void remove(E e){
        String str = "Dirty("+this.o+")";
    }



}
