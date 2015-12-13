package none;

import none.config.Loader;
import none.state.Node;
import none.state.State;

import java.util.*;

/**
 * Created by j on 12/12/2015.
 */
public class RunnerRegression extends Runner{


    public RunnerRegression() {
        super();
        this.planFound = false;
    }

    @Override
    public void execute(Loader config){
        this.metrics = new Metrics();
        this.metrics.keepTrack("start");

        Node initState = new State(config.getInitialState());
        Node goalState = new State(config.getGoalState());
        Deque<Node> expansionTree = new LinkedList<>(); // cola de exploracion
        expansionTree.add(initState);
        while (expansionTree.size() > 0 && !this.planFound){
            Node n = expansionTree.removeFirst(); // primer nodo pendiente de explorar
            List<Node> candidates = n.expand(); // expandir nodo pendiente de eplorar en anchura
            // candidate configuration files


            for(Node candState: candidates){
                if(candState.isSame(goalState)){
                    this.planFound = true;
                    this.plan = candState.getPlan();
                } // fi es igual
            } // fi si alguno
        } // fi mientras


        if(this.planFound){
            System.out.println(this.plan);
        }else{ // plan impossible to find
            System.out.println("They is no way to find the plan");
        }





        System.out.println("Runner Regression Runner");
        System.out.println("Elapsed time: " + this.metrics.getElapsed());
    }
}
