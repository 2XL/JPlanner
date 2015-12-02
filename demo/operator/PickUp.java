package demo.operator;


import java.util.HashSet;

import predicate.Clear;
import predicate.EmptyHand;
import predicate.Holding;
import predicate.OnTable;
import predicate.Predicate;

public class PickUp extends Operator{
	
    public PickUp(char x) {
        super("PickUp", x);
        
        preconditions = new HashSet<Predicate>();
        preconditions.add(new OnTable(x));
        preconditions.add(new EmptyHand());
        preconditions.add(new Clear(x));
        
        eliminate = new HashSet<Predicate>();
        eliminate.add(new OnTable(x));
        eliminate.add(new EmptyHand());
        
        add = new HashSet<Predicate>();
        add.add(new Holding(x));
    }


}
