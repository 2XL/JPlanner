package none;

import none.config.Loader;
import none.operator._Operator;
import none.state.State;

import java.io.*;
import java.util.*;

/**
 * Created by j on 12/12/2015.
 */
public class RunnerRegression extends Runner {

    static int level = 7;

    int stateAppend = 0;
    int stateSkipped = 0;
    int stateRevert = 0;
    int depth = 0;
    int windowSize = 4; // move depth 3 by 3, only roll back if there are no operations remaining,
    int depthRevert = 0; // rever index
    int depthLimit = 10000;
    static String output_path = "src/none/config/solution/";
    static String output = output_path + "output_" + level + ".log";

    public RunnerRegression() {
        super();
        this.planFound = false;
    }

    public static void main(String[] args) throws IOException {
        RunnerRegression run = new RunnerRegression();
        BufferedWriter logger = new BufferedWriter(new FileWriter(new File(output)));
        Loader loader = new Loader(level);
        run.execute(loader);
        logger.write(run.metrics.toString());
        logger.flush();
        logger.close();
    }

    @Override
    public void execute(Loader config) {
        this.metrics = new Metrics();
        this.metrics.keepTrack("\nstart");

        State initState = new State(config.getInitialState(), config);
        State goalState = new State(config.getGoalState(), config);


        /*
        Comprovar la versió en xarxa de la combinació
         */

        System.out.println("InitialState: \t");
        System.out.println(config.getInitialState());
        System.out.println("GoalState: \t");
        System.out.println(config.getGoalState());

        HashSet<State> expansionHistory = new HashSet<>(); // contains a history of all the expanded states / hash, state
        Deque<State> expansionDeque = new LinkedList<>(); // cola de exploracion
        Deque<State> nextExpansionDeque = new LinkedList<>();
        HashMap<Integer, Deque<State>> expansionTree = new HashMap<>();
        Map<Integer, Map<String, State>> residualMapDeque = new HashMap<>();
        Map<String, State> residualDeque = new HashMap<>();
        residualMapDeque.put(depth, residualDeque);
        // nivel
        expansionDeque.add(goalState);
        expansionHistory.add(goalState);

        // int depth = 0;

        // ----------------------------
        // -- lookup loop:  -----------
        // ----------------------------

        while (expansionDeque.size() > 0 && !(this.planState instanceof State) && depth < depthLimit) {
            State s = expansionDeque.remove();
            this.planState = s.expand(initState, expansionHistory, nextExpansionDeque); // current always push to next

            // do the sorting here
            // loop the whole layer
            if (expansionDeque.size() == 0) { // new depth

                this.depth++;
                System.out.println("\n\nNew depth: "+this.depth+" >> "+nextExpansionDeque.size()+" \n ");
                while (nextExpansionDeque.size() > 0){
                    State n = nextExpansionDeque.remove();
                    // check the diff
                    int idxDiff = n.compareSetup(initState).size();
                    if(expansionTree.containsKey(idxDiff)){

                    }else{
                        expansionTree.put(idxDiff, new LinkedList<>());
                    }
                    expansionTree.get(idxDiff).add(n);
                }
                // loop through the expansionTree from 0 to N and pick the 1st match
                for(int match = 0; match < 100; match++){ // the 0 and 100 will never happen, 0 match 100 because the dim doesnt allow so much difference
                    if(expansionTree.containsKey(match)){
                        if(expansionTree.get(match).size() > 0){
                            expansionDeque = expansionTree.get(match); // doesnt have to do anything to remove because this will be removed a the 1st line of the lookup loop
                            // get current and push next
                            int j = 0;
                            while(j < windowSize) {
                                j++;
                                if(expansionTree.containsKey(match + j)) {
                                    expansionDeque.addAll(expansionTree.get(match + j));
                                }
                            }
                            System.out.println(match +" "+expansionDeque.size());
                            break;
                        }
                    }
                }
                // expansionDeque = nextExpansionDeque; //
                // nextExpansionDeque = new LinkedList<>();
            }
        }

        // fi mientras





        // ----------------------------
        // -- state result summary : --
        // ----------------------------

        if(this.planState instanceof State){
            this.plan = this.planState.getPlan();
            this.planFound = true;
        }else{
            this.planFound = false;
        }
        // this.metrics.keepTrack("\nExit Loop... " + this.planFound);
        if (this.planFound) {
            System.out.println("\nSOLUTION: \n");
            //System.out.println(this.plan);
            int steps = 1;
            for (_Operator op : this.plan) {
                System.out.println(steps++ + "\t --> " + op.toString());
            }
            System.out.println("");
        } else { // plan impossible to find
            State s = new State();
            String ss = s.bestMatch +"   \t   "+s.skipMatch + "   \t   "+s.loopMatch + "   \t   "+s.stateCounter;
            System.out.println("bm   \t   sm   \t   lm   \t   sc");
            System.out.println(ss);
        }


        System.out.println("Runner Regression Runner");
        System.out.println("Elapsed time: " + this.metrics.getElapsed());
        /*
        System.out.println("Skipped:      " + this.stateSkipped);
        System.out.println("Appended:     " + this.stateAppend);
        System.out.println("Revert:       " + this.stateRevert);
        System.out.println("Depth:        " + this.depth);
        System.out.println("Plan Found:   " + this.planFound);

        this.metrics.keepTrack("\nElapsed time: " + this.metrics.getElapsed());
        this.metrics.keepTrack("\nSkipped:      " + this.stateSkipped);
        this.metrics.keepTrack("\nAppended:     " + this.stateAppend);
        this.metrics.keepTrack("\nRevert:       " + this.stateRevert);
        this.metrics.keepTrack("\nDepth:        " + this.depth);
        */
    }


}
