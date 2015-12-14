package none;

import none.config.Loader;
import none.predicate._Predicate;
import none.state.State;

import java.util.Stack;

/**
 * Created by j on 12/12/2015.
 */
public class RunnerLinear extends Runner {

    public RunnerLinear() {
        super();

    }

    @Override
    public void execute(Loader config) {

        System.out.print("Runner Linear Runner");

        //
        State initState = new State(config.getInitialState(), config);
        State goalState = new State(config.getGoalState(), config);


        Stack<State> stackGoals = new Stack<>(); // crear una pila vacia
        State currState = initState;

        stackGoals.push(goalState); // apilar estado final


        for(_Predicate p : goalState.getPredicates()){

        }




    }
}
