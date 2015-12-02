package demo.tree;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import demo.predicate.Predicate;

/**
 * This class represents an state with its predicates
 *
 */
public class State {
	private int hash = 0;
	private String state = null;
	private int numColumsUsed;
	private HashSet<Predicate> predicateList;

	public State(HashSet<Predicate> predicateList, int numColumsUsed) {

		this.predicateList = predicateList;
		this.numColumsUsed = numColumsUsed;
	}

	public HashSet<Predicate> getPredicateList() {
		return predicateList;
	}

	// This function returns all the predicates sorted.
	private String getState() {
		if (state == null) {
			// sort all the predicates
			List<String> predicates = new LinkedList<String>();
			for (Predicate p : predicateList) { // O(n)
				predicates.add(p.toString());
			}

			Collections.sort(predicates); // O(nlogn)

			// now that all the predicates are sorted is time to generate a hash
			// code
			String aux = "";
			for (String s : predicates) { // O(n)
				aux += s;
			}
			state = aux;
		}
		return state;
	}

	public int getNumColumsUsed() {
		return numColumsUsed;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof State) {
			State s = (State) obj;
			return this.getState().equals(s.getState());
		}
		return false;
	}

	@Override
	public String toString() {
		String output = "";
		for (Predicate p : predicateList) {
			output += p.toString() + "\n";
		}

		return output;
	}

	@Override
	public int hashCode() {
		if (hash == 0) {
			hash = getState().hashCode();
		}

		return hash;
	}
}
