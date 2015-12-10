package planner.graph;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by x on 3/12/15.
 */
public class State extends Building implements Predicate, Operator {

    Boolean hasSetup; // if true then it has already loaded hasSetup, otherwise its a template of building
    List<String> configuration;
    int depth = 0;
    int difference = Integer.MAX_VALUE;
    String operation;
    State parent;

    public State(State parent, List<String> setup, String operation, int diff){
        super(new ArrayList<>(parent.boxes.values()), new ArrayList<>(parent.offices.values()));

        this.operation = operation;
        this.parent = parent;
        this.depth = this.parent.depth + 1;
        this.difference = diff;
        this.configuration = setup;
        this.hasSetup = false;
        this.loadSetup(setup); // check
        this.hasSetup = true;
    }

    // this loads the configuration file
    public State(List<Box> bs, List<Office> os, List<String> setup) {
        super(bs, os);
        this.parent = null;
        this.operation = null;
        //apply a given hasSetup
        this.configuration = setup;
        this.hasSetup = false;
        this.loadSetup(setup); // check
        this.hasSetup = true;
        // each state consists of an building

    }

    // this one checks  the supperone applies the hasSetup ???
    public boolean loadSetup(List<String> ops) {
        //
        // apply the configuration
        // the loaded configuration should be the same as the
        //
        //String parameter
        Iterator<String> iter = ops.iterator();
        while (iter.hasNext()) {
            String op = iter.next();
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
                    MethodHandle test;
                    //System.out.println("\n::: Print: " + op + " ");
                    switch (methodArgs.length) {
                        case 1:
                            /*
                            switch (methodNameNormal){
                                case "Empty":
                                    this.Empty(methodArgs[0]);
                                    break;
                                case "Clean":
                                    this.Clean(methodArgs[0]);
                                    break;
                                case "Dirty":
                                    this.Dirty(methodArgs[0]);
                                    break;
                                case "Robot_location":
                                    this.Robot_location(methodArgs[0]);
                                    break;
                                default:
                                    System.out.print("Ops not found");
                                    break;
                            }
                            */
                            // System.out.println(methodArgs.length + " 1");
                            todo = this.getClass().getDeclaredMethod(methodNameNormal, String.class);
                            todo.invoke(this, methodArgs[0]);
                            break;
                        case 2:
                            //  System.out.println(methodArgs.length + " 2");
                            /*
                            switch (methodNameNormal){
                                case "Box_location":
                                    this.Box_location(methodArgs[0], methodArgs[1]);
                                    break;
                                default:
                                    System.out.print("Ops not found");
                                    break;
                            }
                            */
                            todo = this.getClass().getDeclaredMethod(methodNameNormal, String.class, String.class);
                            todo.invoke(this, methodArgs[0], methodArgs[1]);


                            break;
                        default:
                            System.out.println("not match");
                            break;
                    }
                    // System.out.println("Print: "+methodNameNormal+ " DONE");


                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            } else {
                System.out.print("NO operation Match");
            }
        }


        return true;
    }

    @Override
    public boolean Robot_location(String o) {
        //System.out.println("State Robot_location " + o);
        // Office office = this.offices.get(o); // retrieve the office
        // this.robot.office = office;
        if (this.hasSetup)
            return (this.robot.office.name == o);
        else
            return super.Robot_location(o);
    }

    @Override
    public boolean Box_location(String b, String o) {
        //System.out.println("State Box_location [" + b + "] " + o);
        Office office = this.offices.get(o);
        // Box box = this.boxes.get(b);
        // office.box_list.put(box.name, box);
        if (this.hasSetup)
            return (office.box_list.containsKey(b));
        else
            return super.Box_location(b, o);
    }

    @Override
    public boolean Dirty(String o) {
        //System.out.println("State Dirty " + o);
        Office office = this.offices.get(o);
        if (this.hasSetup)
            return (this.dirty.isDirty(office));
        else
            return super.Dirty(o);

    }


    @Override
    public boolean Clean(String o) {
        //System.out.println("State Clean " + o);
        Office office = this.offices.get(o);
        // this.dirty.removeDirty(office);
        if (this.hasSetup)
            return (!this.dirty.isDirty(office));
        else
            return super.Clean(o);

    }

    @Override
    public boolean Empty(String o) {
        // System.out.println("State Empty " + o);
        // check each box not containing this office key
        Office office = this.offices.get(o);
        if (this.hasSetup)
            return (office.box_list.size() == 0);
        else
            return super.Empty(o);
        //for(String b : office.box_list.keySet())
        //  office.box_list.remove(b);
        // remove all the boxes int the office list
    }

    @Override
    public boolean Adjacent(String a, String b) {
        // System.out.println("State Adjacent " + a + " : " + b);
        Office office = this.offices.get(a);
        if (this.hasSetup)
            return (office.adjacent_list.containsKey(b));
        else
            return super.Adjacent(a, b);
        //{
        // do nothing
        //  return true;
        //}else{
        // office.adjacent_list.put(b, this.offices.get(b));
        //  return false;
        // }
    }


    // -----------------------------------------------------
    // this returns possible to apply this are operators
    // -----------------------------------------------------
    @Override
    public boolean Clean_office(String o) {
        // this should retrieve a list of possible operations
        if (this.Robot_location(o) && this.Dirty(o) && this.Empty(o))
            return true;
        else
            return false;
    }

    @Override
    public boolean Move(String o1, String o2) {
        if (this.Robot_location(o1) && Adjacent(o1, o2))
            return true;
        else
            return false;
    }

    @Override
    public boolean Push(String b, String o1, String o2) {
        if (this.Robot_location(o1) && this.Box_location(b, o1) && this.Adjacent(o1, o2) && this.Empty(o2))
            return true;
        else
            return false;
    }


    public HashMap<String, List<String>> expand() {

        List<String> candidateOperator = new ArrayList<>();
        if (this.Clean_office(this.robot.office.name)) {
            candidateOperator.add("Clean-Office(" + this.robot.office.name + ")");
        }

        for (String key : this.robot.office.adjacent_list.keySet())
            if (this.Move(this.robot.office.name, key))
                candidateOperator.add("Move(" + this.robot.office.name + "," + key + ")");
        // apply the operators

        for (String box : this.boxes.keySet())
            for (String key : this.robot.office.adjacent_list.keySet())
                if (this.Push(box, this.robot.office.name, key))
                    candidateOperator.add("Push(" + box + "," + this.robot.office.name + "," + key + ")");


        HashMap<String, List<String>> result = new HashMap<>();
        for (String key : candidateOperator) {
            result.put(key, new ArrayList<>(this.apply(key)));
        }
        return result;
    }

    private List<String> apply(String op){
     // cast operation type

        // load the configuration as a hash map
        HashMap<String, String> predicates = new HashMap<>();
        for(String key : this.configuration){
            predicates.put(key, key);
        }

        // System.out.print(op); // apply operation to the building
        String methodName = op.substring(0, op.indexOf('('));
        String methodNameNormal = methodName.replaceAll("-", "_");
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher match = pattern.matcher(op);
        if (match.find()) {
            // System.out.println(match.group(1));
            String[] args = (match.group(1)).split(",");

            switch (methodNameNormal) {

                case "Clean_Office":
                    // do add
                    String clean = "Clean("+ args[0]+")";
                    predicates.put(clean,clean);
                    // do remove
                    String dirty = "Dirty("+ args[0]+")";
                    predicates.remove(dirty);
                    break;
                case "Move": // ofice1 to office2
                    // do add
                    String robot_location_new = "Robot-location("+args[1]+")";
                    predicates.put(robot_location_new,robot_location_new);
                    // do remove
                    String robot_location_old = "Robot-location("+args[0]+")";
                    predicates.remove(robot_location_old);
                    break;
                case "Push":
                    // do add
                    String box_location_new = "Box-location("+args[0]+","+args[2]+")";
                    predicates.put(box_location_new, box_location_new);
                    String push_robot_location_new = "Robot-location("+args[2]+")";
                    predicates.put(push_robot_location_new, push_robot_location_new);
                    String empty_new = "Empty("+args[1]+")";
                    predicates.put(empty_new, empty_new );
                    // do remove
                    predicates.remove("");
                    String empty_old = "Empty("+args[2]+")";
                    predicates.remove(empty_old);
                    String box_location_old = "Box-location("+args[0]+","+args[1]+")";
                    predicates.remove(box_location_old);
                    String push_robot_location_old = "Robot-location("+args[1]+")";
                    predicates.remove(push_robot_location_old);
                    break;
                default:
                    System.out.println("Unknown operation " + op);
                    break;
            }
        }
        return new ArrayList<>(predicates.values());

    }

}
