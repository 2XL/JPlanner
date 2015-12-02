package demo.operator;


import java.util.HashSet;

import predicate.EmptyHand;
import predicate.Holding;
import predicate.OnTable;
import predicate.Predicate;

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
