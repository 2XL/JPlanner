package demo.operator;

import java.util.HashSet;

import demo.predicate.Predicate;

/**
 * Abstract class that defines an Operator
 */
public abstract class Operator {

	private String name;
	private char x;
	protected HashSet<Predicate> preconditions, add, eliminate;

	protected Operator(String name, char x) {
		this.name = name;
		this.x = x;
	}

	public HashSet<Predicate> getPreconditions() {
		return preconditions;
	}

	public void setPreconditions(HashSet<Predicate> preconditions) {
		this.preconditions = preconditions;
	}

	public HashSet<Predicate> getAdd() {
		return add;
	}

	public void setAdd(HashSet<Predicate> add) {
		this.add = add;
	}

	public HashSet<Predicate> getEliminate() {
		return eliminate;
	}

	public void setEliminate(HashSet<Predicate> eliminate) {
		this.eliminate = eliminate;
	}

	protected Operator(String name) {
		this.name = name;
	}

	public char getX() {
		return x;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name + "(" + x + ")";
	}
}
