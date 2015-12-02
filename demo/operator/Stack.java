package demo.operator;


import java.util.HashSet;

import predicate.Clear;
import predicate.EmptyHand;
import predicate.Holding;
import predicate.On;
import predicate.Predicate;

public class Stack extends Operator{

    private char y;

    public Stack(char x){
        super("Stack", x);
    }

    public Stack(char x, char y){
        super("Stack", x);
        this.y = y;
       
        preconditions = new HashSet<Predicate>();
        preconditions.add(new Holding(x));
        preconditions.add(new Clear(y));
        
        eliminate = new HashSet<Predicate>();
        eliminate.add(new Holding(x));
        eliminate.add(new Clear(y));
        
        add = new HashSet<Predicate>();
        add.add(new On(x,y));
        add.add(new EmptyHand());
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
