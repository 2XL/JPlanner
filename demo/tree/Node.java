package demo.tree;

import demo.operator.Operator;

/**
 * This class represents a node in the tree
 *
 */
public class Node {
	private Node parent;
	private State state;
	private Operator operator;

	public Node() {
	}

	public Node(Node parent, Operator operator, State state) {
		this.parent = parent;
		this.operator = operator;
		this.state = state;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Node getParent() {
		return parent;
	}

	public Operator getOperator() {
		return operator;
	}
}
