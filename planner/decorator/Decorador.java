package planner.decorator;

/**
 * Created by x on 3/12/15.
 */
public abstract class Decorador extends Componente{
    private Componente _componente;

    public Decorador(Componente componente){
        _componente = componente;
    }

    public void operacion(){
        _componente.operacion();
    }
}
