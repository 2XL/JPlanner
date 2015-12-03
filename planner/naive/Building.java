package planner.naive;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Building {

    // start the N x Y points
    Robot robot; // the dealer
    int matrix_dim; // = 3;
    HashMap<String, Box> boxes;
    HashMap<String, Office> offices; // replication of the office indexing
    Office building[][]; // for the adjacent list
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
        this.setupRobot();
    }

    private void loadConfigHashMap(String config_file_name) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(config_file_name));

        try {

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            String currentKey = null; // seek for XXX=YYYY

            while (line != null) {
                // System.out.println(line);
                line = line.replaceAll("\\s+", ""); // remove empty spaces
                if (line.indexOf("=") != -1) {
                    // there is = in the string
                    String[] key_value = line.split("=");
                    currentKey = key_value[0];
                    this.configuration.put(currentKey, new ArrayList<String>());
                    line = key_value[1];
                    System.out.println("\n <<<<<Key: " + currentKey + " >>>>> ");
                } else {
                    // there is no = in the string.
                    line = line;
                }
                // detectar si n'hi ha == then
                String reg = ",";
                if (line.indexOf(";") != -1) {
                    reg = ";";
                }
                for (String item : line.split(reg)) {
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

    private void setupBoxes() {
        // setup the boxes
        this.boxes = new HashMap<String, Box>();
        for (String box : this.configuration.get("Boxes")) {
            this.boxes.put(box, new Box(box));
        }
        // System.out.println(this.boxes.toString());

    }

    private void setupAdjacentBox() {
        // add next as neighbors
        for (int row = 0; row < this.matrix_dim; row++) {
            for (int column = 0; column < this.matrix_dim; column++) {

                Office office = this.building[row][column];
                // System.out.println("[" + office.row + "]:[" + office.column + "]");

                try {
                    if (this.building[row + 1][column] != null) {
                        office.addAjacentOffice(this.building[row + 1][column].name, this.building[row + 1][column]);
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

    private void setupOffices() {        // setup the Offices
        this.matrix_dim = (int) Math.sqrt(this.configuration.get("Offices").size());
        System.out.println("\n Matrix Dimension:" + this.matrix_dim);
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

    private void setupRobot() {
        this.robot = new Robot(this.offices); // initial state out of the map
    }

    private void setupOfficeNames() {
        int officeIndex = 0;
        this.offices = new HashMap<String, Office>();
        for (Office[] office : this.building) {
            for (Office item : office) {
                String name = this.configuration.get("Offices").get(officeIndex++); // linked list of office names
                item.name = name;
                // System.out.println("\n[" + item.column + "]:[" + item.row + "]: " + name);
                this.offices.put(name, item); // logical pointer...
            }
        }
    }

    /**
     * 0: initial = InitialState
     * 1: final = GoalState
     *
     * @param state
     */
    public void applyState(int state) {

        String toApply = "";
        if (state == 0) {
            toApply = "InitialState";
        } else {
            toApply = "GoalState";
        }

        //String parameter
        Class[] paramString = new Class[1];
        paramString[0] = String.class;

        //

        System.out.println("\napplyState: " + toApply);
        List<String> ops = this.configuration.get(toApply); // list of operations
        for (String op : ops) {
            // System.out.print(op); // apply operation to the building
            String methodName = op.substring(0, op.indexOf('('));
            String methodNameNormal = methodName.replaceAll("-", "_");
            Pattern pattern = Pattern.compile("\\((.*?)\\)");
            Matcher match = pattern.matcher(op);
            if (match.find()) {
                // System.out.println(match.group(1));
                String[] methodArgs = (match.group(1)).split(",");
                // for (String args : methodArgs)
                // System.out.println(args);
                // System.out.println();
                try {
                    Method todo;
                    System.out.println("::: Print: " + op + " ");
                    switch (methodArgs.length) {
                        case 1:
                            System.out.println(methodArgs.length + " 1");
                            todo = this.getClass().getDeclaredMethod(methodNameNormal, String.class);
                            todo.invoke(this, methodArgs[0]);
                            break;
                        case 2:
                            System.out.println(methodArgs.length + " 2");
                            todo = this.getClass().getDeclaredMethod(methodNameNormal, String.class, String.class);
                            todo.invoke(this, methodArgs[0], methodArgs[1]);
                            break;
                        default:
                            System.out.println("not match");
                            break;
                    }
                    // System.out.println("Print: "+methodNameNormal+ " DONE");

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.print("NO operation Match");
            }
        }
    }

    /**
     * Get the hash code of the office distribution
     *
     * @return
     */
    public int getStateHash() {
        String status = "";
        for (String key : this.offices.keySet()) {
            Office o = this.offices.get(key);
            status += o.boxes.keySet() + (o.isDirty ? "1" : "0") + (o.hasRobot ? "1" : "0");
        }
        return status.hashCode();
    }

    public String getState() {
        String status = "";
        for (String key : this.offices.keySet()) {
            Office o = this.offices.get(key);
            status += o.boxes.keySet() + (o.isDirty ? "1" : "0") + (o.hasRobot ? "1" : "0");
        }
        return status;
    }

    public List<String> exportBuildingConfig() {
        List<String> config = new ArrayList<String>();

        // Boxes
        String state = "InitialState=";
        String box_config = "Boxes=";
        String office_config = "Offices=";

        for (String key : this.boxes.keySet()) {
            box_config += key + ",";
        }
        for (String key : this.offices.keySet()) {
            Office o = this.offices.get(key);
            office_config += key + ",";
            state += (o.isDirty ? "Dirty" : "Clean"); // isDirty
            state += (o.hasRobot ? "Planner-location(" + o.name + ");" : ""); // has robot
            for (String box : o.boxes.keySet())
                state += ("DecorateBox-location(" + box + ")"); // has robot
        }

        return config;
    }

    private boolean checkGoalState() {
        // where the boxes are

        return true;
    }


    // Building State operations
    // have to cast - into _


    public void printIt() {
        System.out.println("printIt() no param");
    }

    /**
     * where o is the Compenent name
     *
     * @param o
     */
    public void Robot_location(String o) {
        //System.out.println("Robot_location");
        Office office = this.offices.get(o); // retrieve the office
        office.hasRobot = true;
        this.robot.setOffice(o);
    }

    /**
     * where o is teh office name and b box name
     *
     * @param b : boxname
     * @param o : office
     */
    public void Box_location(String b, String o) {
        //System.out.println("Box_location");
        Office office = this.offices.get(o);
        Box box = this.boxes.get(b);
        // move the box to a certain office
        office.addBox(b, box);
    }

    public boolean Dirty(String o) {
        //System.out.println("Dirty");
        Office office = this.offices.get(o); // make the office dirty
        return office.setDirty();
    }


    public boolean Clean(String o) {

        //System.out.println("Clean");
        Office office = this.offices.get(o); // make the office clean
        return office.setClean();
    }

    public boolean Empty(String o) {

        //System.out.println("Empty");
        // an office has empty box stacks
        Office office = this.offices.get(o);
        return office.deleteAllBox();
    }

    public List<String> apply() {


        // bucle infinit que replica i retorna els estats
        List<String> candidates = this.robot.apply();
        if (candidates.size() == 0) {
        } else {
            for (String key : this.robot.submit()) {
                // each key is a configuration file
                Building building = new Building(key);
                return building.robot.apply();
            }
        }
        // each apply instead of modifying the model it returns the state after applying the operator :: means the stack of everything
        // todo
        return null;

    }


    public void prettyPrint() {
        System.out.format("%1$10s:%2$10s:%3$10s:%4$10s:%5$10s:%6$10s\n", "Row", "Column", "Name", "Boxes", "isDirty", "hasRobot");
        for (Office[] offices : this.building) {
            for (Office office : offices) {
                office.prettyPrint();
            }
        }
    }


}
