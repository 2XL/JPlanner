package none.config;

import none.building.Box;
import none.building.Office;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class is has the role to load the initial state and final state from the configuration file
 * Created by j on 12/12/2015.
 */
public class Loader {

    private int dimension = 3; // this states the dimension of the puzzle
    String config_file_path;
    Map<String, List<String>> config;
    ArrayList<Box> boxes;
    ArrayList<Office> offices;
    HashMap<String, Set<String>> office_adjacent;

    public Loader(int level) {
        this.config_file_path = System.getProperty("user.dir") + "/src/none/config/config." + this.dimension + ".level." + level + ".txt";
    }

    private void load() {
        this.config = this.loadConfigHashMap();
        this.boxes = this.loadBoxes(config.get("Boxes"));
        this.offices = this.loadOffices(config.get("Offices"));
        this.office_adjacent = this.setupOfficeAdjacent(this.offices);
    }

    public List<String> getInitialState(){
        return this.config.get("InitialState");
    }
    public List<String> getGoalState(){
        return this.config.get("GoalState");
    }

    public Map loadConfigHashMap() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(this.config_file_path));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return configuration;
    }

    public ArrayList<Box> loadBoxes(List<String> box_list) {
        ArrayList<Box> boxes = new ArrayList<>();
        for (String key : box_list) {
            boxes.add(new Box(key));
        }
        return boxes;
    }

    public ArrayList<Office> loadOffices(List<String> office_list) {
        ArrayList<Office> offices = new ArrayList<>();
        for (String key : office_list) {
            offices.add(new Office(key));
        }
        return offices;
    }


    public HashMap<String, Set<String>> setupOfficeAdjacent(List<Office> offices) {
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

    public static void main(String[] args) throws IOException {
        Loader loader = new Loader(1);
        loader.load();
        for (String item : loader.config.keySet())
            System.out.println("[" + item + "] :" + loader.config.get(item));
        System.out.println(loader.office_adjacent);
    }

}
