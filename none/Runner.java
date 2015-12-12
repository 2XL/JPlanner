package none;

import none.config.Loader;

/**
 * Created by j on 12/12/2015.
 */
public abstract class Runner {

    Metrics metrics;

    public Runner() {
    }

    public void execute(Loader config){
        this.metrics = new Metrics();
        this.metrics.keepTrack("start");



        this.metrics.getElapsed();
    }



}
