package lineal.stack;

import lineal.stack.E;
import lineal.stack.P;
import lineal.stack.ops.*;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by j on 16/01/2016.
 */
public class State implements E {
    // State is a list of predicates
    List<P> pre;

    public State() {
        this.pre = new LinkedList<>();
        // somehow this has to be sorted... or maybe not.
    }

    public State(List<P> list) {
        this.pre = list;

    }

    public void addPre(String p) {

        String methodName = p.substring(0, p.indexOf('('));
        switch (methodName) { // split the string and have the first halve
            case "Dirty":
                Dirty predD = new Dirty();
                predD.setAttr(p);
                pre.add(predD);
                break;
            case "Empty":
                Empty predE = new Empty();
                predE.setAttr(p);
                pre.add(predE);
                break;
            case "Clean":
                Clean predC = new Clean();
                predC.setAttr(p);
                pre.add(predC);
                break;
            case "Box-location":
                BoxLocation predBL = new BoxLocation();
                predBL.setAttr(p);
                pre.add(predBL);
                break;
            case "Robot-location":
                RobotLocation predRL = new RobotLocation();
                predRL.setAttr(p);
                pre.add(predRL);
                break;
            default:
                // unhandled error
                System.out.println("Unhandled predicate: "+methodName);
                break;
        }

    }


    public List<P> getPre() {
        return pre;
    }


    public boolean hasPre(P p){
        return this.pre.contains(p); // if it contains a certain string.
    }

    /**
     * Returns a list of none matching
     * @return
     */
    public boolean hasAllPre(PList pl , Deque<E> pila){

        boolean hasAll = true;
        for(Object p : pl.getList())
        {
            // return the p that are not satisfied
            if(this.pre.contains(p)){
                continue;
            }else{
                hasAll = false;
                pila.add((P) p);
            }
        }
        return hasAll;
    }

    public boolean hasCond(E cond, Deque<E> pila){

        // buscar en el estado actual una instancia que satisfaga la condicion cond

        boolean hasCond = false;
        for(Object p : pl.getList())
        {
            // return the p that are not satisfied
            if(this.pre.contains(p)){
                hasCond = true;
                // transmitir a la pila????? WTF
            }else{
                continue;
            }
        }
        return hasCond;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
