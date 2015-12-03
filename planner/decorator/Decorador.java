package planner.decorator;

/**
 * Created by x on 3/12/15.
 */
public abstract class Decorador extends Compenent {
    private Compenent _componente;

    public Decorador(Compenent componente){
        _componente = componente;
    }

    public void operacion(){
        _componente.operacion();
    }
}
