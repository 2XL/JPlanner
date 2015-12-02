package planner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) throws IOException {
   
   
    	String config_file_name = System.getProperty("user.dir")+"/src/config/input.txt";
        System.out.println("Working Directory = " + config_file_name);
    	// String config_file_name = "/home/x/Code/JPlaner/src/planner/input.txt";
    	

    	Building building = new Building(config_file_name);
        
        // read the configuration file
		System.out.println(building.configuration.toString());


		Planner planner = new Planner(building);

		List<String> result = planner.resolve();


    }
}
