package planner.decorator;

/**
 * Created by x on 3/12/15.
 */
public class DecoradorConcretoB extends Decorador{
    public DecoradorConcretoB(Componente componente){
        super(componente);
    }

    public void operacion(){
        super.operacion();
        comportamientoAñadido();
        System.out.println("DecoradorConcretoB.operacion()");
    }

    public void comportamientoAñadido(){
        System.out.println("Comportamiento B añadido");
    }
}
