package demo.operator;


import java.util.HashSet;

import demo.predicate.EmptyHand;
import demo.predicate.Holding;
import demo.predicate.OnTable;
import demo.predicate.Predicate;

public class PutDown extends Operator {

    public PutDown(char x){
        super("PutDown", x);
        
        preconditions = new HashSet<Predicate>();
        preconditions.add(new Holding(x));
        
        eliminate = new HashSet<Predicate>();
        eliminate.add(new Holding(x));
        
        add = new HashSet<Predicate>();
        add.add(new OnTable(x));
        add.add(new EmptyHand());
    }
}
