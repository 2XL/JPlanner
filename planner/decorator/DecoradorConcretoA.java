package planner.decorator;

/**
 * Created by x on 3/12/15.
 */
public class DecoradorConcretoA extends Decorador{
    private String _propiedadAñadida;

    public DecoradorConcretoA(Componente componente){
        super(componente);
    }

    public void operacion(){
        super.operacion();
        _propiedadAñadida = "Nueva propiedad";
        System.out.println("DecoradorConcretoA.operacion()");
    }
}