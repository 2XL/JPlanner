package lineal.stack;

import java.util.*;

/**
 * Created by j on 23/01/2016.
 */
public class PList implements E {
    // continue una lista de condiciones
    List<P> list;
    O op;
    public PList() {
        // empty constructor
        list = new LinkedList<>();
    }

    public void addCond(P p) {
        list.add(p);
        sortList();
    }

    public void setList(List<P> list){
    	this.list = list;
    	sortList();
    }
    
    
    public boolean rmCond(P p) {
        return list.remove(p);
    }

    public List<P> getList(){
        return this.list;
    }

    public void setOp(O op){
        this.op = op;
    }
    
    public void sortList(){
    	Collections.sort(list, new Comparator<P>(){
    		public int compare(P obj1, P obj2){
    	    	
    	    	if(obj1.priority - obj2.priority == 0){
    	    		return obj1.getOffice().compareTo(obj2.getOffice());
    	    	}
    	    	
    	    	return obj1.priority - obj2.priority;
    	    }
    	});
    }
    
    
    public boolean containsCond(P p){
    
    	
    	return this.list.contains(p);
    }
    

}
