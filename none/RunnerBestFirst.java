package none;

import none.config.Loader;
import none.operator._Operator;
import none.state.State;

import java.io.*;
import java.util.*;

/**
 * Created by j on 12/12/2015.
 */
public class RunnerBestFirst extends Runner {

    static int level = 10;
    int depth = 0;
    int depthLimit = 10000;
    static String output_path = "src/none/config/solution/";
    static String output;

    public RunnerBestFirst() {
        super();
        this.planFound = false;
    }

    public static void main(String[] args) throws IOException {
        RunnerBestFirst run = new RunnerBestFirst();
        InputStreamReader ISR = new InputStreamReader(System.in);
        BufferedReader BR = new BufferedReader(ISR);
        System.out.println("Choose a level [1-10]");
        String userInput = BR.readLine(); //program waits here until the user inserts a line of text
        while (true) {
            try {
                if (Integer.parseInt(userInput) >= 0 && Integer.parseInt(userInput) <= 10)
                    break;
            }catch (NumberFormatException ex){
             // wrong option
            }
            System.out.println("wrong option?!");
            userInput = BR.readLine();
        }
        level = Integer.parseInt(userInput);
        output = output_path + "output_" + level + ".log";
        BufferedWriter logger = new BufferedWriter(new FileWriter(new File(output)));
        System.out.println("level : " + level);
        Loader loader = new Loader(level);
        run.execute(loader);
        logger.write(run.metrics.toString());
        logger.flush();
        logger.close();
        BR.close();
        ISR.close();
    }

    @Override
    public void execute(Loader config) {
        this.metrics = new Metrics();
        this.metrics.keepTrack("run/Start");

        State initState = new State(config.getInitialState(), config);
        State goalState = new State(config.getGoalState(), config);

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

        expansionDeque.add(goalState);
        expansionHistory.add(goalState);

        goalState.compareSetup(initState); // instance the distance variable
        Deque<State> planDeque = new LinkedList<>(); // a linked list with all the plans that can reach to the goal
        // int depth = 0;

        // ----------------------------
        // -- lookup loop:  -----------
        // ----------------------------

        metrics.keepTrack("depth >> nextExpansionSize");
        while (expansionDeque.size() > 0 && !(this.planState instanceof State) && depth < depthLimit) {
            State s = expansionDeque.remove();
            s.expand(initState, expansionHistory, nextExpansionDeque, planDeque); // current always push to next
            // COMMENTthis line to lookup the optimal solution
            if (planDeque.size() != 0) break;

            if (expansionDeque.size() == 0) { // new depth
                this.depth++;
                metrics.keepTrack(this.depth+"\t >> "+nextExpansionDeque.size());
                while (nextExpansionDeque.size() > 0) {
                    State n = nextExpansionDeque.remove();
                    if (expansionTree.containsKey(n.getDistance())) {
                    } else {
                        expansionTree.put(n.getDistance(), new LinkedList<>());
                    }
                    expansionTree.get(n.getDistance()).add(n);
                }
                // loop through the expansionTree from 0 to N and pick the 1st match
                int match = 1;
                while (expansionDeque.size() == 0) { // asume there is always a solution
                    if (expansionTree.containsKey(match)) {
                        if (expansionTree.get(match).size() > 0) {
                            expansionDeque = expansionTree.get(match);
                            if (expansionTree.containsKey(match)) {
                                expansionDeque.addAll(expansionTree.get(match));
                            }
                            break;
                        }
                    }
                    match++;
                }
            }
        }
        // fi mientras

        this.metrics.keepTrack("run/Finish");
        if (planDeque.size() != 0) {
            System.out.println("Deque size: " + planDeque.size());
            this.planState = planDeque.getLast(); // last appended is certainly the closest!
        }

        // ----------------------------
        // -- state result summary : --
        // ----------------------------

        this.metrics.keepTrack("report/Start");

        if (this.planState instanceof State) {
            this.plan = this.planState.getPlan();
            this.planFound = true;
        } else {
            this.planFound = false;
        }
        // this.metrics.keepTrack("\nExit Loop... " + this.planFound);
        if (this.planFound) {
            System.out.println("\nSOLUTION: \n");
            //System.out.println(this.plan);
            int steps = 1;
            for (_Operator op : this.plan) {
                System.out.println(steps + "\t --> " + op.toString());
                metrics.keepTrack(steps + "\t --> " + op.toString());
                steps++;
            }
            System.out.println("");
        // } else { // plan impossible to find
            State s = new State();
            String ss = s.bestMatch + "   \t   " + s.skipMatch + "   \t   " + s.loopMatch + "   \t   " + s.stateCounter;
            System.out.println("best \t   skip \t   loop \t   total states");
            System.out.println(ss);

            metrics.keepTrack("best match: "+s.bestMatch+" differences");
            metrics.keepTrack("skipped:    "+s.skipMatch+" repeated states");
            metrics.keepTrack("skipped:    "+s.loopMatch+" looping states");
            metrics.keepTrack("total:      "+s.stateCounter+" states");
        }
        /*
        while (planDeque.size() > 0) {
            State s = planDeque.removeFirst();
            System.out.println("State: " + s.getDepth() + " " + s.getDistance() + " " + s);
        }
        */
        System.out.println("Runner Regression Runner");
        System.out.println("Elapsed time: " + this.metrics.getElapsed());

        metrics.keepTrack("report/Finish");
    }


}
