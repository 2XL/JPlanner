package demo.operator;


import java.util.HashSet;

import demo.predicate.Clear;
import demo.predicate.EmptyHand;
import demo.predicate.Holding;
import demo.predicate.OnTable;
import demo.predicate.Predicate;

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
