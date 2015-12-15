package none;

import none.config.Loader;
import none.operator._Operator;
import none.state.Node;

import java.util.Deque;

/**
 * Created by j on 12/12/2015.
 */
public abstract class Runner {

    Metrics metrics;
    Boolean planFound;
    Deque<_Operator> plan; // ordered deck of queue // double ended list...

    public Runner() {
    }

    public void execute(Loader config){
        this.metrics = new Metrics();
        this.metrics.keepTrack("start");



        this.metrics.getElapsed();
    }



}
