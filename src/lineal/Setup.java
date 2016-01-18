package lineal;

import lineal.stack.State;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by j on 16/01/2016.
 */
public class Setup {

    private int dimension;
    private String config_file_path;
    private Map<String, List> rawConfig;

    public Setup(int level) {
        if (level == 0) {
            this.dimension = 2;
        } else {
            this.dimension = 3;
        }
        this.rawConfig = new HashMap<>();
        this.config_file_path = System.getProperty("user.dir") + "/src/lineal/setup/config." + this.dimension + ".level." + level + ".txt";

        // Boxes
        // Offices
        // InitialState
        // GoalState
    }


    public void load() {
        this.rawConfig = this.loadConfigHashMap();
    }

    public Map getConfig() {
        return rawConfig;
    }

    public State getState(String state) {
        List<String> strState = this.rawConfig.get(state);
        State s = new State();
        // set the state predicates
        for(String str : strState){
            s.addPre(str);
        }

        return s;
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


    public static void main(String[] args) {
        System.out.println("Setup");
        Setup s = new Setup(0);
        s.load();
        Map m = s.getConfig();
        System.out.println(m);
        System.out.println("Setup/End");
    }


}
