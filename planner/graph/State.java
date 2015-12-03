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
        this.loadSetup(setup);
        // each state consists of an building

    }
    public boolean loadSetup(List<String> ops) {
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


        return true;
    }

    @Override
    public void Robot_location(String o) {

    }

    @Override
    public void Box_location(String b, String o) {

    }

    @Override
    public void Dirty(String o) {

    }

    @Override
    public void Clean(String o) {

    }

    @Override
    public void Empty(String o) {

    }

    @Override
    public void Adjacent(String a, String b) {

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
