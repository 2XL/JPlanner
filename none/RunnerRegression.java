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

    public static void main(String[] args) {
        RunnerRegression run = new RunnerRegression();
        Loader loader = new Loader(8);
        run.execute(loader);
    }

    int stateAppend = 0;
    int stateSkipped = 0;
    int stateRevert = 0;
    int depth = 0;
    int depthRevert = 0; // rever index
    int depthLimit = 100;
    int closest = Integer.MAX_VALUE;

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

        System.out.println("InitialState: \t");
        System.out.println(config.getInitialState());
        System.out.println("GoalState: \t");
        System.out.println(config.getGoalState());

        Map<String, State> expansionHistory = new HashMap<>(); // contains a history of all the expanded states / hash, state
        Deque<State> expansionDeque = new LinkedList<>(); // cola de exploracion
        Deque<State> nextExpansionDeque = new LinkedList<>();
        Map<Integer, Map<String, State>> residualMapDeque = new HashMap<>();
        Map<String, State> residualDeque = new HashMap<>();
        residualMapDeque.put(depth, residualDeque);
        // nivel
        expansionDeque.add(goalState);
        expansionHistory.put(goalState.toString(), goalState);

        // int depth = 0;
        while (expansionDeque.size() > 0 && !this.planFound && depth < depthLimit) {
            // System.out.println("\n:::: " + depth + "/" + depthLimit + " exp: " + expansionDeque.size() + " next: " + nextExpansionDeque.size());
            State n = expansionDeque.removeFirst(); // primer nodo pendiente de explorar

            // n.getState()
            // System.out.println(n.getState());
            List<_Predicate> p = n.compareSetup(initState); // list of predicates that doesn't match
            // System.out.println("CURR: "+n.getState());
            // System.out.println("DIFF: "+p); // get list of predicates that doesn't match, this has to be resolved by operators
            List<State> candidates = n.expand(p); // expandir nodo pendiente de explorar en anchura
            if (candidates.size() == 0)
                candidates = n.expand();
            // System.out.println("CAND: "+candidates);
            // for each candidate configuration state
            for (State candState : candidates) {
                //System.out.println(n.getOperator() +" --> "+candState.getOperator());
                int diff = candState.compareSetup(initState).size();
                if (diff == 0) { // if it matches the initial state
                    System.out.println("[Match] ");
                    this.planFound = true;
                    this.plan = candState.getPlan(); // return the parent stack...
                } else {
                    // fi es igual
                    if (diff < this.closest) {
                        this.closest = diff;
                    }
                }
                String digest = candState.toString();
                if (expansionHistory.containsKey(digest)) {
                    //if (false) {
                    // aixo es podria moure a la classe state que mantingui una llista estatic de totes les instancies.
                    // System.out.println("Skipped");
                    // System.out.println(residualDeque);
                    residualDeque.put(candState.toString(), candState); // bucle infinit fins que es troba la solució :D
                    // one residual
                    this.stateSkipped++;
                } else {
                    // System.out.println("Append");
                    this.stateAppend++;
                    /*
                    for (State state : expansionHistory.values()) {
                        System.out.println(state.getState());
                    }
                    */
                    expansionHistory.put(digest, candState);
                    nextExpansionDeque.add(candState); // add to the next level
                }
            } // fi si alguno
            // swap the current level with the next level
            if (expansionDeque.size() == 0 && nextExpansionDeque.size() != 0) {
                // next depth
                expansionDeque = nextExpansionDeque;
                nextExpansionDeque = new LinkedList<>(); // instance a new expansion tree
                depth++;

                if (residualMapDeque.containsKey(depth)) {
                    residualDeque = residualMapDeque.get(depth);
                } else {
                    residualMapDeque.put(depth, new HashMap<>());
                    residualDeque = residualMapDeque.get(depth);
                }

                //
                this.metrics.keepTrack("Depth: " + depth);
                System.out.println("Closest: " + this.closest + "    \tDepth: " + this.depth + " \tExpand: " + expansionDeque.size() + " ");

            } else {
                // this is the end
            }

            if (expansionDeque.size() == 0 && residualDeque.size() != 0) {
                this.closest = Integer.MAX_VALUE;
                // apply move operation
                Deque<State> st = null;
                do {
                    // there is something to be fixed here.
                    if(residualMapDeque.containsKey(depthRevert)) {
                        if(residualMapDeque.get(depthRevert).size() == 0) {
                            st = new LinkedList<>(residualMapDeque.remove(this.depthRevert).values()); // get the lowest index
                        }
                    }
                    depthRevert++;
                } while (st == null);
                System.out.println("REVERT   dep     " + this.depthRevert);
                System.out.println("REVERT   res     " + st.size());

                depth = depthRevert; // tornar al depth, pero el depth pot no correspondre
                if(residualMapDeque.containsKey(depth)){
                    residualDeque = residualMapDeque.get(depth); // switch the residual deque pointer to current deck
                }else{
                    residualDeque = new HashMap<>();
                }
                Map map = new HashMap<String, State>();
                for (State s : st) {
                    // State s = st.getLast();
                    for(State state : s.expand())
                        map.put(state.toString(), state);
                }
                expansionDeque.addAll(new LinkedList<>(map.values()));

                System.out.println("REVERT   exp     " + expansionDeque.size());

                //expansionDeque.add(s);
            }

        } // fi mientras

        this.metrics.keepTrack("Exit Loop... " + this.planFound);
        if (this.planFound) {
            System.out.println("\nSOLUTION: \n");
            //System.out.println(this.plan);
            int steps = 1;
            for (_Operator op : this.plan) {
                System.out.println(steps++ + "\t --> " + op.toString());
            }
            System.out.println("");
        } else { // plan impossible to find
            System.out.println("There is no way to find the plan");
        }


        System.out.println("Runner Regression Runner");
        System.out.println("Elapsed time: " + this.metrics.getElapsed());
        System.out.println("Skipped:      " + this.stateSkipped);
        System.out.println("Appended:     " + this.stateAppend);
        System.out.println("Revert:       " + this.stateRevert);
        System.out.println("Depth:        " + this.depth);
        System.out.println("Closest:      " + this.closest);

    }


}
