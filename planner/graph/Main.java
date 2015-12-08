package planner.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by x on 3/12/15.
 */
public class Main {

    private static int currentBestPath = Integer.MAX_VALUE;
    private static Map<Integer, String> bestSolution;
    private static int level = 2;
    private static int dim = 3;
    private static boolean found = false;
    private static int estimatedDepth = 16;
    private static int impossiblePath = 0;
    // private static int limitDepthPath = 0;
    private static int looperPath = 0;
    private static int operationCounter = 0;
    private static int foundPath = 0;
    private static int stoppedPath = 0;
    private static int skippedPath = 0;
    private static int dummyPath = 0;

    private static String config_file_name = System.getProperty("user.dir") + "/src/config/config." + dim + ".level." + level + ".conf";

    public static void main(String[] args) throws IOException {

        System.out.println("Working Directory = " + config_file_name);
        Map<String, List<String>> config = loadConfigHashMap(config_file_name);
        for (String item : config.keySet())
            System.out.println("[" + item + "] :" + config.get(item));

        // start the boxes && offices
        ArrayList<Box> boxes = loadBoxes(config.get("Boxes"));
        ArrayList<Office> offices = loadOffices(config.get("Offices"));

        // hasSetup office adjacent
        HashMap<String, Set<String>> office_adjacent = setupOfficeAdjacent(offices);
        System.out.println(office_adjacent);

        List<String> initialState = config.get("InitialState");
        Collections.sort(initialState);
        List<String> finalState = config.get("GoalState");
        Collections.sort(finalState);
        List<String> testFinal = config.get("InitialState");
        Collections.sort(testFinal);

        // una matriu d'estats // una llista d'estats -> cada posicio del array correspon a un nivell d'expansio
        List<HashMap<String, List<String>>> state = new ArrayList<>();
        // un hash map stats, where String is the hash
        Map<String, List<String>> stateHash = new HashMap<>();
        // refactoring into a linkedlist of nodes

        State init = new State(boxes, offices, initialState);
        List<String> initial = init.getSetup();
        //System.out.println("My init: " + initial);
        //System.out.println("Real in: " + initialState);

        State fini = new State(boxes, offices, finalState);
        List<String> finnale = fini.getSetup();
        //System.out.println("My final: " + finnale);
        //System.out.println("Real fin: " + finalState.toString());


        State test = new State(boxes, offices, testFinal);
        List<String> testi = test.getSetup();
        // every level
        HashMap<String, Integer> current = new HashMap<>();


        State currentState = new State(boxes, offices, initialState);
        List<State> currState = new ArrayList<>();
        /**
         * Sort by diff, lower diff first
         */
        Map<Integer, List<State>> nextMap = new HashMap<>();

        String finalhash = digestHash(finalState);
        int diff = compareSetup(initialState, finalState);
        currState.add(currentState);
        nextMap.put(diff, currState);
        int depth = 0;
        try {
            while (!found && nextMap.keySet().size() != 0) {
                System.out.println(depth++ + " Size " + nextMap.keySet());

                nextMap = iterative(nextMap, finnale, stateHash);
                /*
                int index = 0;
                for (Integer key : nextMap.keySet()) {
                    if (index > 10)
                        nextMap.remove(key);
                    index++;
                }
                */
/*
                for (Integer key : nextMap.keySet()) {
                    System.out.print("[" + key + ":" + nextMap.get(key).size() + "],");
                }
                System.out.println(finalhash);
*/
            }
        } catch (NullPointerException e) {
            //  System.out.print(e.getStackTrace());
        }

        // current is to void recursion and stack is to know the paths or hops
        // recursive(currentState, 2, testi, current, new ArrayList<>()); // report with hash stack


        // recursive(currentState, estimatedDepth, finnale, current, new ArrayList<>(), compareSetup(initialState, finalState)); // report with hash stack


        // setps means
        if (found == false) {
            System.out.println("Not found");
        } else {
            System.out.println(bestSolution.size() + " -> " + bestSolution);
        }
        System.out.println("\n" +
                "------------------------------------------" +
                "Results:" +
                "\n\tIMPOSSIBLE: " + impossiblePath + "" +
                // "\n\tDEPTHLIMIT: " + limitDepthPath + "" +
                "\n\tLOOPERPATH: " + looperPath + "" +
                "\n\tFOUND_PATH: " + foundPath + "" +
                "\n\tFORCE_QUIT: " + stoppedPath + "" +
                "\n\tDUMMY_QUIT: " + dummyPath + "" +
                "\n\tSKIPP_QUIT: " + skippedPath + "" +
                "\n\tTOTAL_CONF: " + operationCounter + "" +
                "\n" +
                "------------------------------------------");
    }

    public static Map loadConfigHashMap(String config_file_name) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(config_file_name));
        Map<String, List<String>> configuration = new HashMap();
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            String currentKey = null; // seek for XXX=YYYY
            while (line != null) {
                line = line.replaceAll("\\s+", ""); // remove empty spaces
                if (line.indexOf("=") != -1) {
                    String[] key_value = line.split("=");
                    currentKey = key_value[0];
                    configuration.put(currentKey, new ArrayList<String>());
                    line = key_value[1];
                }
                String reg = ",";
                if (line.indexOf(";") != -1) {
                    reg = ";";
                }
                for (String item : line.split(reg)) {
                    configuration.get(currentKey).add(item);
                }
                // configuration
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return configuration;
    }

    public static ArrayList<Box> loadBoxes(List<String> box_list) {
        ArrayList<Box> boxes = new ArrayList<>();
        for (String key : box_list) {
            boxes.add(new Box(key));
        }
        return boxes;
    }

    public static ArrayList<Office> loadOffices(List<String> office_list) {
        ArrayList<Office> offices = new ArrayList<>();
        for (String key : office_list) {
            offices.add(new Office(key));
        }
        return offices;
    }

    public static HashMap<String, Set<String>> setupOfficeAdjacent(List<Office> offices) {
        HashMap<String, Set<String>> adjacent = new HashMap<>();
        int dim = (int) Math.sqrt(offices.size());
        Office[][] building = new Office[dim][dim];
        int index = 0;
        // hasSetup the building index
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                building[x][y] = offices.get(index++);
            }
        }
        // check if adjacent
        for (int row = 0; row < dim; row++)
            for (int column = 0; column < dim; column++) {
                Office office = building[row][column];
                try {
                    if (building[row + 1][column] != null) {
                        office.putAdjacent(building[row + 1][column]);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                try {

                    if (building[row][column + 1] != null) {
                        office.putAdjacent(building[row][column + 1]);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                try {
                    if (building[row - 1][column] != null) {
                        office.putAdjacent(building[row - 1][column]);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                try {
                    if (building[row][column - 1] != null) {
                        office.putAdjacent(building[row][column - 1]);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        // retrieve adjacent list
        for (Office o : offices) {
            adjacent.put(o.name, o.listAdjacent());
        }
        return adjacent;
    }

    /**
     * compare the differences [0, no diff; X, x differences foound]
     *
     * @param currState
     * @param goalState
     * @return
     */
    public static int compareSetup(List<String> currState, List<String> goalState) {
        List<String> tempCurr = new ArrayList<>(currState);
        tempCurr.removeAll(goalState);
        if (currentBestPath > tempCurr.size()) // need a global variable to keep track of the current best
        {
            System.out.println("There is a closer one: " + currentBestPath + " --> " + tempCurr.size());
            currentBestPath = tempCurr.size();

        }
        return tempCurr.size();
    }


    /**
     * @param mapExpState
     * @param finalState
     * @param visitedState
     * @return
     */
    public static Map<Integer, List<State>> iterative(Map<Integer, List<State>> mapExpState, List<String> finalState, Map<String, List<String>> visitedState) {
        Map<Integer, List<State>> nextMap = new HashMap<>();
        SortedSet<Integer> keys = new TreeSet<>(mapExpState.keySet());
        for (Integer key : keys) {
            List<State> expandedState = mapExpState.get(key);
            for (State state : expandedState) {
                HashMap<String, List<String>> candidates = state.expand();
                for (String operation : candidates.keySet()) {
                    operationCounter++;
                    List<String> candState = candidates.get(operation);

                    if (visitedState.containsKey(digestHash(candState))) {
                        looperPath++;
                        continue;
                    }

                    int newDiff = compareSetup(candState, finalState);

                    if (newDiff == 0) {
                        System.out.print("Solution: ");
                        bestSolution = new HashMap<>();
                        while (state.parent != null) {
                            bestSolution.put(state.depth, state.operation);
                            state = state.parent;
                        }
                        found = true;
                        bestSolution.put(bestSolution.size() + 1, operation);
                        return null; // quit
                    }
                    if (state.parent != null) {


                        if (state.difference < newDiff) {
                            //
                            dummyPath++;
                            continue; // going the wrong way
                        }


                        // this op, parent op, parent.parent op
                        /*
                        if (state.parent.parent != null) {
                            int mLoop = (1 + operation + state.operation + state.parent.operation).split("M").length;
                            int pLoop = (1 + operation + state.operation + state.parent.operation).split("P").length;
                            // System.out.println("Match"+match);
                            if (pLoop == 4 || mLoop == 4) {
                                Pattern pattern = Pattern.compile("\\((.*?)\\)");
                                Matcher match = pattern.matcher(operation);
                                if (match.find()) {
                                    String[] methodArgs = (match.group(1)).split(",");
                                    // System.out.println(match.group(1));
                                    if (state.parent.parent.robot.office.adjacent_list.containsKey(methodArgs[0])) {
                                        skippedPath++;
                                        continue;
                                    }
                                }

                            }
                        }
                        */


                    }


                    if (!nextMap.containsKey(newDiff)) {
                        nextMap.put(newDiff, new ArrayList<>());
                    }
                    nextMap.get(newDiff).add(new State(state, candState, operation, newDiff));


                    visitedState.put(digestHash(candState), candState);

                }
            }
        }
        return nextMap;
    }

    /**
     * This implementation requires to being serialized
     *
     * @param state      : current state to be expanded
     * @param depth      : current state depth
     * @param goalState  : the goal state to be achieved
     * @param stateStack : stack Of states to avoid loops
     * @param ops
     */
    public static void recursive(State state, int depth, List<String> goalState, HashMap<String, Integer> stateStack, ArrayList<String> ops, int oldDiff) {
        // System.out.println("Depth: " + ops.size()); // no es lineal...


        HashMap<String, List<String>> candidates;
        candidates = state.expand();

        for (String key : candidates.keySet()) {
            List<String> nextConfig = candidates.get(key);
            //Collections.sort(nextConfig);
            ArrayList<String> operationStack = new ArrayList<>(ops);
            operationStack.add(key);
            int newDiff = compareSetup(nextConfig, goalState); // not further to the goalstate

            if (newDiff == 0) {
                if (bestSolution == null) {
                    // bestSolution = operationStack;
                } else {
                    //  if (bestSolution.size() > operationStack.size())
                    //   bestSolution = operationStack;
                }
                foundPath++;
                continue;
            } else {
                if ((depth - operationStack.size()) < newDiff) {
                    // System.out.println("Impossible: (" + operationStack.size() + "/" + depth + ") remain [" + newDiff+"]");
                    impossiblePath++;
                    continue; // impossible
                } else {

                    // more loggic to skip, local skip, need to keep track of self operation tail.


                    //
                    //System.out.println("Continue: [" + key + "] " + diff + "/" + currentBestPath + " --> " + newDiff + " [" + ops.toString() + "] ");
                    State nextState = new State(new ArrayList<>(state.boxes.values()),
                            new ArrayList<>(state.offices.values()), nextConfig);

                    if (!stateStack.containsKey(nextConfig)) {// check if state already exists
                        if (bestSolution != null) { // quit if there is already an solution... el que pasa aqui es que no se sincronitza la profunditat
                            stoppedPath++;
                            continue;
                        }
                        stateStack.put(nextConfig.toString(), newDiff); //
                        recursive(nextState, depth, goalState, stateStack, operationStack, newDiff);
                    } else {
                        looperPath++;
                        continue; // state repetition
                    }
                }
            }
        }
    }

    public static String digestHash(List<String> state) {
        Collections.sort(state);
        String result = "";
        /*
        for (String s : state) {
            result += s.hashCode();
        }
        */
        result += state.toString().hashCode();
        return result;
    }

    public static boolean skipThis() {


        return false; //
    }

}

