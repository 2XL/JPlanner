package none;

import none.config.Loader;
import none.operator._Operator;
import none.predicate._Predicate;
import none.state.Node;
import none.state.State;

import java.util.*;

/**
 * Created by j on 12/12/2015.
 */
public class RunnerRegression extends Runner {
    public RunnerRegression() {
        super();
        this.planFound = false;
    }

    @Override
    public void execute(Loader config) {
        this.metrics = new Metrics();
        this.metrics.keepTrack("start");

        State initState = new State(config.getInitialState(), config);
        State goalState = new State(config.getGoalState(), config);

//        State initState = new State(config.getGoalState(), config);
//        State goalState = new State(config.getInitialState(), config);

        System.out.print("InitialState: \t");
        System.out.println(config.getInitialState());
        System.out.print("GoalState: \t");
        System.out.println(config.getGoalState());

        Map<String, State> expansionHistory = new HashMap<>(); // contains a history of all the expanded states / hash, state
        Deque<State> expansionTree = new LinkedList<>(); // cola de exploracion
        Deque<State> nextExpansionTree = new LinkedList<>();
        expansionTree.add(goalState);
        expansionHistory.put(goalState.toString(), goalState);

        int depth = 0;
        int depthLimit = 100;
        while (expansionTree.size() > 0 && !this.planFound && depth < depthLimit) {
            System.out.println("\n:::: " + depth + "/" + depthLimit + " exp: "+expansionTree.size()+" next: "+nextExpansionTree.size());
            State n = expansionTree.removeFirst(); // primer nodo pendiente de explorar

            // n.getState()
            // System.out.println(n.getState());
            List<_Predicate> p = n.compareSetup(initState); // list of predicates that doesn't match
            System.out.println("CURR: "+n.getState());
            System.out.println("DIFF: "+p); // get list of predicates that doesn't match, this has to be resolved by operators
            List<State> candidates = n.expand(p); // expandir nodo pendiente de explorar en anchura
            System.out.println("CAND: "+candidates);
            // for each candidate configuration state
            for (State candState : candidates) {
                // System.out.println(candState.getState());
                if (candState.compareSetup(initState).size() == 0) { // if it matches the initial state
                    System.out.println("[Match] ");
                    this.planFound = true;
                    this.plan = candState.getPlan(); // return the parent stack...
                } // fi es igual
                String digest = candState.toString();
                if (expansionHistory.containsKey(digest)) {
                    // aixo es podria moure a la classe state que mantingui una llista estatic de totes les instancies.
                    System.out.println("Skipped");
                } else {
                    System.out.println("Append");
                    /*
                    for (State state : expansionHistory.values()) {
                        System.out.println(state.getState());
                    }
                    */
                    expansionHistory.put(digest, candState);
                    nextExpansionTree.add(candState); // add to the next level
                }
            } // fi si alguno
            // swap the current level with the next level
            if (expansionTree.size() == 0 && nextExpansionTree.size() != 0) {
                // next depth
                expansionTree = nextExpansionTree;
                nextExpansionTree = new LinkedList<>(); // instance a new expansion tree
                depth++;
                this.metrics.keepTrack("Depth: "+depth);
            } else {
                // this is the end
            }
        } // fi mientras

        this.metrics.keepTrack("Exit Loop... "+this.planFound);
        if(this.planFound){
            System.out.println("\nSOLUTION: \n");
            //System.out.println(this.plan);
            int steps = this.plan.size();
            for(_Operator op : this.plan){
                System.out.println(steps-- +"\t --> "+op.toString());
            }
            System.out.println("");
        }else{ // plan impossible to find
            System.out.println("There is no way to find the plan");
        }



        System.out.println("Runner Regression Runner");
        System.out.println("Elapsed time: " + this.metrics.getElapsed());
    }

    public static void main(String[] args) {
        RunnerRegression run = new RunnerRegression();
        Loader loader = new Loader(2);
        run.execute(loader);
    }
}
