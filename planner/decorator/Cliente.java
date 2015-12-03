package planner.decorator;

/**
 * Created by x on 3/12/15.
 */
public class Cliente{



    public static void main(String[] args){
        // El Cliente es el robot
        ComponenteConcreto c = new ComponenteConcreto();
        DecoradorConcretoA d1 = new DecoradorConcretoA(c);
        DecoradorConcretoB d2 = new DecoradorConcretoB(d1);
        d2.operacion();



    }
}