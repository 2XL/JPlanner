package planner.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by x on 3/12/15.
 */
public class Main {

    public static void main(String[] args) throws IOException {


        String config_file_name = System.getProperty("user.dir") + "/src/config/config.3.hard.conf";
        System.out.println("Working Directory = " + config_file_name);
        Map<String, List<String>> config = loadConfigHashMap(config_file_name);
        System.out.println(config);
        // start the boxes && offices
        ArrayList<Box> boxes = loadBoxes(config.get("Boxes"));
        ArrayList<Office> offices = loadOffices(config.get("Offices"));

        // hasSetup office adjacent
        HashMap<String, Set<String>> office_adjacent = setupOfficeAdjacent(offices);
        // System.out.println(office_adjacent);

        List<String> initialState = config.get("InitialState");
        Collections.sort(initialState);
        List<String> finalState = config.get("GoalState");
        Collections.sort(finalState);
        List<String> testFinal = config.get("State1");
        Collections.sort(testFinal);

        // una matriu d'estats
        List<HashMap<String, List<String>>> state = new ArrayList<>(); // una llista d'estats -> cada posicio del array correspon a un nivell d'expansio
        //
        Map<String, List<String>> stateHash = new HashMap<>(); // un hash map stats, where String is the hash
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
        recursive(currentState, 14, finnale, current, new ArrayList<>()); // report with hash stack
        // System.out.print(result);

        // currentSetup.expand(); // retrieve list of new setups

        // for each possible operations

        // expand() recursive function that retrieves all the applyable operations

        // for each applyable operation create a building to retrieve the next state after applying the configuration


        // after each apply check and compare the state obtained if state is new expand() else existing quit

        // util matching the finnale state

        // state.add(fini.getSetup());


        // compareSetup(state.get(0), initialState);

        // compareSetup(state.get(1), finalState);
        // System.out.print(init.getSetup());
        // Building building1 = new Building(config_file_name);
        // Building building2 = new Building(config_file_name);

        // read the configuration file
        // System.out.println(building.configuration.toString());


        //Planner planner = new Planner(building1, building2);
        //List<String> result = planner.resolve();


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

    public static boolean compareSetup(List<String> ini, List<String> fin) {
        java.util.Collections.sort(ini);
        java.util.Collections.sort(fin);
        if (ini.toString().equals(fin.toString())) {
            System.out.println("Found");
            return true;
        } else {
            // System.out.println("KeepLookUp");
            return false;
        }
    }

    /**
     * @param state      : current state to be expanded
     * @param depth      : current state depth
     * @param goalState  : the goal state to be achieved
     * @param stateStack : stack Of states to avoid loops
     * @param ops
     */
    public static void recursive(State state, int depth, List<String> goalState, HashMap<String, Integer> stateStack, ArrayList<String> ops) {

            if(depth == ops.size())
            return;
        // System.out.println("Depth: " + depth);
        HashMap<String, List<String>> candidates;
        candidates = state.expand();
        for (String key : candidates.keySet()) {

            // System.out.print(key);
            List<String> nextConfig = candidates.get(key);
            Collections.sort(nextConfig);
            // System.out.println(nextConfig);

            if(nextConfig.equals(goalState)) {
                System.out.println("Found! "+ops.toString());
            }
            else{
                // System.out.println("Continue");
                State nextState = new State(new ArrayList<Box>(state.boxes.values()),
                        new ArrayList<Office>(state.offices.values()), nextConfig);
                ArrayList<String> operationStack = new ArrayList<>(ops);
                operationStack.add(key);
                if(!stateStack.containsKey(nextConfig))
                    recursive(nextState, depth, goalState, stateStack, operationStack);
            }
            /*
            Collections.sort(config);

            if (compareSetup(config, goalState)) {
                // return "["+depth+"] "+key; // it the init is same as final quit
                ops.add(key);
                System.out.println("[" + ops.size() + "] Found \n" + ops.toString());
            } else {
                if (depth >= ops.size()) // if depth greater than zero then lookup, otherwise quit
                {
                    if(stateStack.containsKey(config.toString()))
                        continue;
                    else
                        stateStack.put(config.toString(), ops.size());
                    ArrayList ops_this = new ArrayList<>();
                    ops_this.add(key);
                    // System.out.println(ops_this);
                    recursive(
                            new State(
                                    new ArrayList<>(state.boxes.values()),
                                    new ArrayList<>(state.offices.values()), config),
                            depth,
                            goalState,
                            stateStack,
                            ops_this);
                }
            }
            */


        }
        // System.out.println("[" + depth + "] Limit Depth" + ops.toString());
    }

}


/*
   depth -= 1;
        // System.out.println("Depth: " + depth);
        if (depth >= 0) // if depth greater than zero then lookup, otherwise quit
        {
            HashMap<String, List<String>> candidate = null;
            candidate = state.expand();
            for (String key : candidate.keySet()) {

                List<String> config = candidate.get(key);
                // System.out.print(config);
                Collections.sort(config);
                if (stateStack.containsKey(config.toString())) { // loop
                    // return "["+depth+"] Loop"; // continue lookup
                     // System.out.println("[" + depth + "] Loop" + ops.toString());
                     if(stateStack.get(config.toString()) < ops.size()){
                        // continue; // hiha un que ha arribat aqui amb menys operacions que jo
                     //}else{
                       //  stateStack.put(config.toString(), ops.size()); // update with lower value
                        // ops.add(key);
                        // recursive(new State(new ArrayList<>(state.boxes.values()), new ArrayList<>(state.offices.values()), config), depth, goalState, stateStack, new ArrayList<>(ops));
                     }
                } else { // continue
                    stateStack.put(config.toString(), ops.size());
                    if (compareSetup(config, goalState)) { // finished
                        // return "["+depth+"] "+key; // it the init is same as final quit
                        ops.add(key);
                        System.out.println("[" + ops.size() + "] Found \n" + ops.toString());
                    }else // continue
                    {
                        ops.add(key);
                        recursive(new State(new ArrayList<>(state.boxes.values()), new ArrayList<>(state.offices.values()), config), depth, goalState, stateStack, new ArrayList<>(ops));
                    }
                }
            }
        }
 */