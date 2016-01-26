package lineal.stack;

/**
 * Created by j on 16/01/2016.
 */
public abstract class P implements E{


    protected int priority = 0;
    protected boolean defined = false;


    public boolean isParcialDefined(){

        return this.defined;
    }

}
