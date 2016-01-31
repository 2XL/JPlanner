package lineal.stack;

import lineal.stack.pre.*;

import java.util.*;

/**
 * Created by j on 16/01/2016.
 */
public class State implements E {
    // State is a list of predicates
    PList pre;
    // could be hash map


    RobotLocation robotLocation;
    Map<String, BoxLocation> boxLocations;
    public State() {
        this.pre = new PList();
        boxLocations = new HashMap<>();
        // somehow this has to be sorted... or maybe not.
    }

    public RobotLocation getRobotLocation(){
        return robotLocation;
    }

    public void setRobotLocation(RobotLocation rl){
        this.robotLocation = rl;
    }
    
    public State(List<P> list) {
        this.pre.setList(list);

    }

    public boolean removePre(P p){
        // recorrer la llista... per comprovar si son iguals
        if(this.pre.rmCond(p)){
            return true;
        }else{
            return false;
        }
    }

    public void addPre(P p){
        this.pre.addCond(p);
    }



    public void applyOp(O op){

        // do reflection
        op.apply(this); // this state apply ops
        // addPre(op.toString());

    }

    public void addPre(String p) {

        String methodName = p.substring(0, p.indexOf('('));
        switch (methodName) { // split the string and have the first halve
            case "Dirty":
                Dirty predD = new Dirty();
                predD.setAttr(p);
                pre.addCond(predD);
                break;
            case "Empty":
                Empty predE = new Empty();
                predE.setAttr(p);
                pre.addCond(predE);
                break;
            case "Clean":
                Clean predC = new Clean();
                predC.setAttr(p);
                pre.addCond(predC);
                break;
            case "Box-location":
                BoxLocation predBL = new BoxLocation();
                predBL.setAttr(p);
                // allow lookup by box or office
                this.boxLocations.put(predBL.getO(), predBL);
                this.boxLocations.put(predBL.getB(), predBL);
                pre.addCond(predBL);
                break;
            case "Robot-location":
                RobotLocation predRL = new RobotLocation();
                predRL.setAttr(p);
                this.robotLocation = predRL;
                pre.addCond(predRL);
                break;
            default:
                // unhandled error
                System.out.println("Unhandled predicate: "+methodName);
                break;
        }

    }

    public String getFreeAdjacent(TreeSet<String> adjacents){
        for(String key: adjacents) {
            if (this.boxLocations.containsKey(key)){
                continue;
            }else{
                return key;
            }
        }
        return null; // no possible todo this... then?

    }

    /**
     * Lookup what box is on this office
     * @param office
     * @return
     */
    public String getBoxLocation(String office){
        return this.boxLocations.get(office).getB();
    }
    
    public String getBoxLocationByBox(String box){
        return this.boxLocations.get(box).getO();
    }
    
    
    public void setBoxLocation(BoxLocation bl){
    	BoxLocation box = this.boxLocations.get(bl.getB());
        this.boxLocations.remove(box.getB());
        this.boxLocations.remove(box.getO());
        
        this.boxLocations.put(bl.getB(), bl);
        this.boxLocations.put(bl.getO(), bl);
        
    }
    
    public List<P> getPre() {
        return pre.getList();
    }




    public void fillNull(P p, Broker b){

        switch (p.type){
            case "Adjacent":
                // Map<String, List<String>>   b.adjacent
                // ((Adjacent) p).assignAdjacent(b.adjacent); // feed with its adjacent condition
                break;
            case "BoxLocation":
                break;
            case "Clean":
                break;
            case "Dirty":
                break;
            case "Empty":
                break;
            case "RobotLocation":
                break;
            default:
                break;
        }

    }

    public boolean hasPre(P p){
        return this.pre.containsCond(p); // if it contains a certain string.
    }

    /**
     * Returns a list of none matching
     * @return
     */
    public List<E> hasAllPre(PList pl){
        List<E> temp = new LinkedList<>();
        for(Object p : pl.getList())
        {
            // return the p that are not satisfied
            if(this.pre.containsCond((P)p)){
                continue;
            }else{
                if(((P) p).type.equals("Adjacent"))
                    continue;
                 temp.add((P) p);
            }
        }
        return temp;
    }

    public boolean hasCond(E cond){
        // buscar en el estado actual una instancia que satisfaga la condicion cond
        boolean hasCond = false;
        for(Object p : this.pre.getList())
        {
            // return the p that are not satisfied
            // if(this.pre.contains(cond)){
            // System.out.println("Has condition? "+p+" as "+cond +" ?");
            if(p.equals(cond)){
                hasCond = true;
                // transmitir a la pila????? WTF
                // todo No se esto, supongo que quiere que actualize la variable en PList
                //
                System.out.println("Has condition! "+p+"");
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
