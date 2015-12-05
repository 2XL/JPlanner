package planner.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.*;

/**
 * Created by x on 3/12/15.
 */
public class Main {

    private static int currentBestPath = Integer.MAX_VALUE;
    private static List<String> bestSolution;
    private static int estimatedDepth = 13;
    private static String config_file_name = System.getProperty("user.dir") + "/src/config/config.3.hard.conf";

    public static void main(String[] args) throws IOException {

        System.out.println("Working Directory = " + config_file_name);
        Map<String, List<String>> config = loadConfigHashMap(config_file_name);
        System.out.println(config);

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

        // current is to void recursion and stack is to know the paths or hops
        // recursive(currentState, 2, testi, current, new ArrayList<>()); // report with hash stack
        recursive(currentState, estimatedDepth, finnale, current, new ArrayList<>(), compareSetup(initialState, finalState)); // report with hash stack
        // setps means
        if (bestSolution == null) {
            System.out.println("Not found");
        } else {
            System.out.print(bestSolution.size() + " -> " + bestSolution);
        }
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
                    bestSolution = operationStack;
                } else {
                    if (bestSolution.size() > operationStack.size())
                        bestSolution = operationStack;
                }
                continue;
            } else {
                if (( depth - operationStack.size()) < newDiff) {
                    // System.out.println("Impossible: (" + operationStack.size() + "/" + depth + ") remain [" + newDiff+"]");
                    continue; // impossible
                } else {
                    //System.out.println("Continue: [" + key + "] " + diff + "/" + currentBestPath + " --> " + newDiff + " [" + ops.toString() + "] ");
                    State nextState = new State(new ArrayList<>(state.boxes.values()),
                            new ArrayList<>(state.offices.values()), nextConfig);
                    if (depth == ops.size())
                        continue; // depth limit reached
                    if (!stateStack.containsKey(nextConfig)) {
                        recursive(nextState, depth, goalState, stateStack, operationStack, newDiff);
                    }else{
                        continue; // state repetition
                    }
                }
            }
        }
    }

}

