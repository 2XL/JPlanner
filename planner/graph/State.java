package planner.graph;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by x on 3/12/15.
 */
public class State extends Building implements Predicate, Operator {

    // this loads the configuration file
    public State(List<Box> bs, List<Office> os, List<String> setup){
        super(bs, os);


        //apply a given setup
        // this.loadSetup(setup, false); // check
        // each state consists of an building

    }
    // this one checks  the supperone applies the setup ???
    public boolean loadSetup(List<String> ops, Boolean apply) {


        //
        // apply the configuration
        // the loaded configuration should be the same as the
        //

        //String parameter
        Class[] paramString = new Class[1];
        paramString[0] = String.class;

        //
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
                            if(apply) {
                                todo = this.getClass().getDeclaredMethod(methodNameNormal, String.class);
                                todo.invoke(this, methodArgs[0]);
                            }else{
                                todo = super.getClass().getDeclaredMethod(methodNameNormal, String.class);
                                todo.invoke(this, methodArgs[0]);
                            }
                            break;
                        case 2:
                            System.out.println(methodArgs.length + " 2");
                            if(apply) {
                                todo = this.getClass().getDeclaredMethod(methodNameNormal, String.class, String.class);
                                todo.invoke(this, methodArgs[0], methodArgs[1]);
                            }else{
                                todo = this.getClass().getDeclaredMethod(methodNameNormal, String.class, String.class);
                                todo.invoke(this, methodArgs[0], methodArgs[1]);

                            }
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


        return true;
    }

    @Override
    public boolean Robot_location(String o) {
        // Office office = this.offices.get(o); // retrieve the office
        // this.robot.office = office;
        return this.robot.office.name == o;
    }

    @Override
    public boolean Box_location(String b, String o) {
        Office office = this.offices.get(o);
        // Box box = this.boxes.get(b);
        // office.box_list.put(box.name, box);
        return office.box_list.containsKey(b);
    }

    @Override
    public boolean Dirty(String o) {
        Office office = this.offices.get(o);
        return this.dirty.isDirty(office);
    }

    @Override
    public boolean Clean(String o) {
        Office office = this.offices.get(o);
        // this.dirty.removeDirty(office);
        return !this.dirty.isDirty(office);
    }

    @Override
    public boolean Empty(String o) {
        // check each box not containing this office key
        Office office = this.offices.get(o);
        return (office.box_list.size() == 0);

        //for(String b : office.box_list.keySet())
          //  office.box_list.remove(b);
        // remove all the boxes int the office list
    }

    @Override
    public boolean Adjacent(String a, String b) {
        Office office = this.offices.get(a);
        return (office.adjacent_list.containsKey(b));
        //{
            // do nothing
          //  return true;
        //}else{
            // office.adjacent_list.put(b, this.offices.get(b));
          //  return false;
        // }
    }

    // this returns possible to apply

    @Override
    public boolean Clean_office() {
        return false;
    }

    @Override
    public boolean Move() {
        return false;
    }

    @Override
    public boolean Push() {
        return false;
    }
}
