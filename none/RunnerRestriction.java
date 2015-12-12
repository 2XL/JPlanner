package none;

import none.config.Loader;

/**
 * Created by j on 12/12/2015.
 */
public class RunnerRestriction extends Runner{

    public RunnerRestriction() {
        super();
    }

    @Override
    public void execute(Loader config){
        this.metrics = new Metrics();
        this.metrics.keepTrack("start");
        System.out.println("Runner Restriction Runner");
        System.out.println("Elapsed time: " + this.metrics.getElapsed());

    }
}
