package planner.graph;

import planner.naive.Building;
import planner.naive.Planner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by x on 3/12/15.
 */
public class Main {

    public static void main(String[] args) throws IOException {


        String config_file_name = System.getProperty("user.dir") + "/src/config/input.txt";
        System.out.println("Working Directory = " + config_file_name);
        Map<String, List<String>> config = loadConfigHashMap(config_file_name);

        // start the boxes && offices
        ArrayList<Box> boxes = loadBoxes(config.get("Offices"));
        ArrayList<Office> offices = loadOffices(config.get("Boxes"));
        HashMap<String, List<String>> office_adjacent = setupOfficeAdjacent(offices);


        // setup office adjacent








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

    public static ArrayList<Box> loadBoxes(List<String> box_list){
        ArrayList<Box> boxes = new ArrayList<>();
        for(String key : box_list){
            boxes.add(new Box(key));
        }
        return boxes;
    }

    public static ArrayList<Office> loadOffices(List<String> office_list){
        ArrayList<Office>  offices = new ArrayList<>();
        for(String key : office_list){
            offices.add(new Office(key));
        }
        return offices;
    }

    public static HashMap<String, List<String>> setupOfficeAdjacent(List<Office> offices){
        HashMap<String, List<String>> adjacent = new HashMap<>();
        int dim = (int)Math.sqrt(offices.size());
        String[][] building = new String[dim][dim];
        int index= 0;
        for(int x = 0; x < dim; x++){
            for(int y=0; y < dim; y++){
                building[x][y] = offices.get(index++).name;
            }
        }
        for(Office o : offices){
            adjacent.put(o.name, o.setupAdjacentOffices(building));
        }
        return adjacent;
    }

}
