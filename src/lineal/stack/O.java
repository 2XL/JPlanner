package lineal.stack;
/**
 * Created by j on 16/01/2016.
 */
public abstract class O implements E {


    protected boolean precondition(){
        // lista de condiciones que deben de ser cumplidas para aplicar el operador

        return false;
    }

    protected boolean add(){
        return false;

    }


    protected boolean delete(){
        return false;

    }


    @Override
    public String toString() {
        return super.toString();
    }
}
