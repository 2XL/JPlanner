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
        State initState = new State(config.getInitialState(), config);
        State goalState = new State(config.getGoalState(), config);
        // System.out.println(config.getInitialState());
        // System.out.println(config.getGoalState());
        Deque<State> expansionTree = new LinkedList<>(); // cola de exploracion
        expansionTree.add(initState);
        while (expansionTree.size() > 0 && !this.planFound){
            State n = expansionTree.removeFirst(); // primer nodo pendiente de explorar
            // n.getState()
            // System.out.println(n.getState());
            List<Node> candidates = n.expand(n.compareSetup(goalState)); // expandir nodo pendiente de eplorar en anchura
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
            System.out.println("There is no way to find the plan");
        }
        System.out.println("Runner Regression Runner");
        System.out.println("Elapsed time: " + this.metrics.getElapsed());
    }

    public static void main(String[] args){
        RunnerRegression run = new RunnerRegression();
        Loader loader = new Loader(1);
        run.execute(loader);
    }
}
