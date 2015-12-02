package demo.operator;


import java.util.HashSet;

import demo.predicate.Clear;
import demo.predicate.EmptyHand;
import demo.predicate.Holding;
import demo.predicate.On;
import demo.predicate.Predicate;

public class Unstack extends Operator{


    private char y;

    public Unstack(char x) {
        super("Unstack", x);

    }

    public Unstack(char x, char y) {
        super("Unstack", x);
        this.y = y;
        
        preconditions = new HashSet<Predicate>();
        preconditions.add(new On(x,y));
        preconditions.add(new Clear(x));
        preconditions.add(new EmptyHand());
        
        eliminate = new HashSet<Predicate>();
        eliminate.add(new On(x,y));
        eliminate.add(new EmptyHand());
        
        add = new HashSet<Predicate>();
        add.add(new Holding(x));
        add.add(new Clear(y));
    }

    public char getY() {
        return y;
    }

    public void setY(char y) {
        this.y = y;
    }
    public String toString() {
        return this.getName()+"("+this.getX()+", "+this.getY()+")";
    }
}
