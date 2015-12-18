package none.state;

import none.building.Office;
import none.config.Loader;
import none.operator.CleanOffice;
import none.operator.Move;
import none.operator.Push;
import none.operator._Operator;
import none.predicate.*;
import sun.awt.image.ImageWatched;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by j on 12/12/2015.
 */
public class State extends Node {

    static HashMap<Integer, State> all_state;
    Office location;
    // _Operator operator; // the operation make to reach this state


    public State(State s){
        this.location = s.location;
        super.predicates = new HashSet<>(s.predicates);
        super.parent = s.parent;
        super.operator = s.operator;
    }


    public State(Office o, Set<_Predicate> lp, State parent, _Operator op) {
        this.location = o; // robot location
        super.predicates = lp;
        super.parent = parent; // parent node
        super.operator = op; // operation done
    }


    public boolean exist() {
        return this.all_state.containsKey(this.hashCode());
    }

    public boolean flush() {
        if (this.exist()) {
            return false;
        } else {
            this.all_state.put(this.hashCode(), this);
            return true;
        }
    }


    public State(List<String> config, Loader loader) {
        predicates = new TreeSet<>();

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
                        this.location = loader.getOffice(methodArgs[0]);
                        candPred = new RobotLocation(this.location); // im located at a certain office in the state
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
        for (Iterator<_Predicate> iterator = this.predicates.iterator(); iterator.hasNext(); ) {
            _Predicate p = iterator.next();
            state.add(p.toString());
        }
        return state;
    }


    public List<_Predicate> compareSetup(State goalState) {
        // replicate the current predicates
        List<String> tempCurr = new ArrayList<>(this.getState());
        tempCurr.removeAll(goalState.getState()); // remove all those that match with the goal
        List<_Predicate> result = new LinkedList<>();
        for (_Predicate p : this.predicates) {
            if (tempCurr.contains(p.toString())) {
                result.add(p);
            }
        }
        return result;
    }

    public List<State> expand() {
        // move to neighbors
        List<State> expansion = new LinkedList<>();

        for (Office o : this.location.getAdjacents()) {
            Move move = new Move(this.location, o, this);
            State s = move.apply();
            if (s instanceof State) {
                //System.out.println("Move to expansion");
                expansion.add(s);
            }
        }
        return expansion;
    }

    public List<State> expand(List<_Predicate> predicates) {

        List<State> expansion = new LinkedList<>();
        // els que calen arregar son els box location | clean | robot location
        // sort de predicates by priority
        Set<_Predicate> pred = new TreeSet<>();
        for (_Predicate p : predicates) {
            if (p.getOffice().equals(this.location))
                pred.add(p);
        }
        //System.out.println(predicates);
        // predicates to be considered
        //System.out.println(pred);
        State s;
        //boolean toMove = true; // default always move
        for (_Predicate p : pred) {
            // only handle the states where im involved
            String predicate = p.getClass().getSimpleName();
            switch (predicate) {
                case "Clean": // if there are no office to be cleaned then do nothing
                    //toMove = false;
                    //System.out.println("CleanOffice -> " + predicate);
                    // si predicat es clean intentare embrutarho
                    CleanOffice clean_office = new CleanOffice(p.getOffice(), this);
                    s = clean_office.revert();
                    if (s instanceof State) {
                        //System.out.println("Clean to expansion");
                        expansion.add(s); // returns a list of candidate nodes
                    }
                    // add operation clean myself
                    if (expansion.size() == 0) {
                        _Predicate pred_box = null;
                        //System.out.println(this.predicates);
                        // lookup for the predicate that has box at this location
                        for (_Predicate p_box : this.predicates) {
                            //System.out.print(p_box.toString());
                            if (p_box.getOffice().name == this.location.name && p_box.getBox().equals(null)) {
                                continue;
                            } else {
                                pred_box = p_box;
                                break;
                            }
                        }

                        // there is a box on this office
                        for (Office o : this.location.getAdjacents()) {
                            Push push = new Push(pred_box.getBox(), this.location, o, this);
                            s = push.apply();
                            if (s instanceof State) {
                                //System.out.println("Push to expansion");
                                expansion.add(s); // returns a list of candidates nodes to be expanded or pushed to the queue
                            }
                        }


                    }


                    break;

                case "BoxLocation": // if the box has to be moved ill move it even if its not within one step
                    //System.out.println("Push -> " + predicate);
                    // move the box to all it is adjacent
                    for (Office o : this.location.getAdjacents()) {
                        Push push = new Push(p.getBox(), this.location, o, this);
                        s = push.apply();
                        if (s instanceof State) {
                            //System.out.println("Push to expansion");
                            expansion.add(s); // returns a list of candidates nodes to be expanded or pushed to the queue
                        }
                    }
                    // move my box to my adjacents
                    break;
                case "Empty": // if there is empty
                    // toMove = true;
                    // noop
                    break;
                default:
                    // robot location
                    break;
            }

            //System.out.println(expansion);
            // only add if it doesn't already exist
            /*
            if (expansion.size() == 0) {
                System.out.println("List State Empty");
            }
            */
        }
        /*
        if (expansion.size() == 0) { // try to move
            System.out.println("N O O P!"); // NO OPERATION
        }
        */
        /*
        if (toMove) { // if there was no clean always move
            //
            for (Office o : this.location.getAdjacents()) {
                Move move = new Move(this.location, o, this);
                s = move.apply();
                if (s instanceof State) {
                    //System.out.println("Move to expansion");
                    expansion.add(s);
                }
            }
        }
        */
        // be naive always move
        /*
        if (expansion.size() == 0) { // try to move
            //System.out.println("N O O P!");
            for (Office o : this.location.getAdjacents()) {
                Move move = new Move(this.location, o, this);
                s = move.apply();
                if (s instanceof State) {
                    //System.out.println("Move to expansion");
                    expansion.add(s);
                }
            }
        }
        */
        //System.out.println("RETURN");
        //System.out.println(expansion);
        return expansion;
    }


    public Office getRobotLocation() {
        return this.location;
    }

    @Override
    public int hashCode() {
        this.getState(); // sort the list
        return this.getState().hashCode();
    }
}
