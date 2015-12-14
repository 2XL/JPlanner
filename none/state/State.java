package none.state;

import none.config.Loader;
import none.operator.CleanOffice;
import none.operator._Operator;
import none.predicate.*;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by j on 12/12/2015.
 */
public class State extends Node {



    public State(List<String> config, Loader loader) {
        predicates = new LinkedList<>();

        for (String pred : config) {


            String methodName = pred.substring(0, pred.indexOf('('));
            // String methodNameNormal = methodName.replaceAll("-", "_");
            Pattern pattern = Pattern.compile("\\((.*?)\\)");
            Matcher match = pattern.matcher(pred);
            if (match.find()) {
                String[] methodArgs = (match.group(1)).split(",");

                _Predicate candPred = null;
                switch (methodName.toLowerCase()) {
                    case "box-location": // b, o,
                        candPred = new BoxLocation(loader.getBox(methodArgs[0]), loader.getOffice(methodArgs[1]));
                        break;
                    case "robot-location": // o
                        candPred = new RobotLocation(loader.getOffice(methodArgs[0]));
                        break;
                    case "empty": // o
                        candPred = new Empty(loader.getOffice(methodArgs[0]));
                        break;
                    case "dirty": // o
                        candPred = new Dirty(loader.getOffice(methodArgs[0]));
                        break;
                    case "clean": // o
                        candPred = new Clean(loader.getOffice(methodArgs[0]));
                        break;
                    /*
                    // Static to each office.
                    case "adjacent": // o1, o2
                        candPred = new Adjacent(loader.getOffice(methodArgs[0]), loader.getOffice(methodArgs[1]));
                        break;
                        */
                    default:
                        break;
                }
                predicates.add(candPred);
            }
        }
    }

    public List<String> getState() {
        List<String> state = new LinkedList<>();
        for(_Predicate p : this.predicates){
            state.add(p.toString());
        }
        return state;
    }


    public List<_Predicate> compareSetup(State goalState) {
        // replicate the current predicates
        List<String> tempCurr = new ArrayList<>(this.getState());
        tempCurr.removeAll(goalState.getState()); // remove all those that match with the goal
        List<_Predicate> result = new LinkedList<>();
        for(_Predicate p : this.predicates){
            if(tempCurr.contains(p.toString())){
                result.add(p);
            }
        }
        return result;
    }

    public List<Node> expand(List<_Predicate> predicates){
        List<Node> expansion = new LinkedList<>();

        List<_Operator> ops;
        RobotLocation robot;
        // robot operation

        // CleanOffice
        if(predicates.contains(Dirty.class.getSimpleName()) || predicates.contains(Clean.class.getSimpleName())){

            return expansion;
        }


        // Push
        if(predicates.contains(Empty.class.getSimpleName()) || predicates.contains(BoxLocation.class.getSimpleName())){

            return expansion;
        }

        // Move
        if(predicates.contains(RobotLocation.class.getSimpleName())){

            return expansion;
        }


        // know the predicates that are pending to be achieved
        // expand for each predicate

        // know the operations that can be performed

        return expansion;
    }


}
