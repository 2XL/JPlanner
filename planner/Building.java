package planner;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Building {

	// start the N x Y points
	Robot robot; // the dealer
	int matrix_dim; // = 3;
	Map<String, Box> boxes;
	Map<String, Office> offices; // replication of the office indexing
	Office building[][];
	public Map<String, List<String>> configuration = new HashMap<String, List<String>>();

	public Building(Building another) {
		System.out.println("Clone Building ");
		this.matrix_dim = another.matrix_dim; // you can access
		this.boxes = another.boxes; // you can access
		this.building = another.building; // you can access
		this.configuration = another.configuration; // you can access
		this.robot = another.robot;
		this.offices = another.offices;
	}

	public Building(String config) {
		this.robot = new Robot(-1, -1); // initial state out of the map
		// set office name!!!
		try {
			this.loadConfigHashMap(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setupBoxes();
		this.setupOffices();
		this.setupOfficeNames();
		this.setupAdjacentBox();
	}

	private void loadConfigHashMap(String config_file_name) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(config_file_name));

		try {

			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			String currentKey = null; // seek for XXX=YYYY

			while (line != null) {
				// System.out.println(line);
				line = line.replaceAll("\\s+",""); // remove empty spaces
				if(line.indexOf("=")!= -1){
					// there is = in the string
					String[] key_value = line.split("=");
					currentKey = key_value[0];
					this.configuration.put(currentKey, new ArrayList<String>());
					line = key_value[1];
					System.out.println(">>>> <<<<<Key: "+currentKey);
				}else{
					// there is no = in the string.
					line = line;
				}
				// detectar si n'hi ha == then
				String reg = ",";
				if(line.indexOf(";") != -1){
					reg = ";";
				}
				for (String item: line.split(reg)){
					// System.out.print(item);
					this.configuration.get(currentKey).add(item);
				}

				// configuration
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			br.close();
		}

	}
	private void setupBoxes(){
		// setup the boxes
		this.boxes = new HashMap<String, Box>();
		for (String box : this.configuration.get("Boxes")){
			this.boxes.put(box, new Box(box));
		}
		System.out.println(this.boxes.toString());

	}
	private void setupAdjacentBox() {
		// add next as neighbors
		for (int row = 0; row < this.matrix_dim; row++) {
			for (int column = 0; column < this.matrix_dim; column++) {
				
				Office office = this.building[row][column];
				System.out.println("[" + office.row + "]:[" + office.column + "]");

				try {
					if (this.building[row + 1][column] != null) {
						office.addAjacentOffice(this.building[row +1][column].name, this.building[row + 1][column]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				try {

					if (this.building[row][column + 1] != null) {
						office.addAjacentOffice(this.building[row][column + 1].name, this.building[row][column + 1]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				try {
					if (this.building[row - 1][column] != null) {
						office.addAjacentOffice(this.building[row - 1][column].name, this.building[row - 1][column]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				try {
					if (this.building[row][column - 1] != null) {
						office.addAjacentOffice(this.building[row][column - 1].name, this.building[row][column - 1]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}

			}
		}
	}
	private void setupOffices(){		// setup the Offices
		this.matrix_dim = (int)Math.sqrt(this.configuration.get("Offices").size());
		System.out.println("Matrix Dimension:" +this.matrix_dim);
		this.building = new Office[matrix_dim][matrix_dim];
		// invoice offices
		int officeIndex = 0;
		for (int column = 0; column < matrix_dim; column++) {
			for (int row = 0; row < matrix_dim; row++) {
				// System.out.println(column);
				// System.out.println(row);
				building[column][row] = new Office(column, row);
			}
		}


	}
	private void setupOfficeNames(){
		int officeIndex = 0;
		this.offices = new HashMap<String, Office>();
		for (Office[] office : this.building) {
			for (Office item : office) {
				String name = this.configuration.get("Offices").get(officeIndex++); // linked list of office names
				item.name = name;
				System.out.println("[" + item.column + "]:[" + item.row + "]: "+name);
				this.offices.put(name, item); // logical pointer...
			}
		}
	}

	/**
	 * 0: initial = InitialState
	 * 1: final = GoalState
	 * @param state
	 */
	public void applyState(int state){

		String toApply = "";
		if(state == 0){
			toApply = "InitialState";
		}else{
			toApply = "GoalState";
		}

		System.out.println("\napplyState: "+toApply);
		List<String> ops = this.configuration.get(toApply); // list of operations

		for(String op : ops){

			// System.out.print(op); // apply operation to the building
			Pattern pattern = Pattern.compile("\\((.*?)\\)");
			Matcher match = pattern.matcher(op);
			if(match.find()) {
				// System.out.println(match.group(1));
				for (String args : (match.group(1)).split(","))
					System.out.println(args);
			}else{
				System.out.print("NO Match");
			}
		}
	}
	private boolean checkGoalState(){
		// where the boxes are

		return true;
	}

	private String getStatusHash(){

		// checks the dirtyness of state

		return "";
	}


	// Building State operations
	// have to cast - into _

	/**
	 * where o is the Office name
	 * @param o
	 */
	private void Robot_location(String o){


		Office office = this.offices.get(o); // retrieve the office
		this.robot.column = office.column;
		this.robot.row = office.row;

		office.hasRobot = true;
	}

	/**
	 * where o is teh office name and b box name
	 * @param b : boxname
	 * @param o : officename
	 */
	private void Box_location(String b, String o){
		Office office = this.offices.get(o);
		Box box = this.boxes.get(b);

		// move the box to a certain office

		box.column = office.column;
		box.row = office.row;

		office.addBox(box.name, box);



	}

	private boolean Dirty(String o){
		Office office = this.offices.get(o); // make the office dirty
		return office.setDirty();
	}


	private boolean Clean(String o){
		Office office = this.offices.get(o); // make the office clean
		return office.setClean();
	}

	private boolean Empty(String o){
		// an office has empty box stacks
		Office office = this.offices.get(o);
		return office.deleteAllBox();
	}


}
