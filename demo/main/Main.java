package demo.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;

import demo.operator.Operator;
import demo.operator.PickUp;
import demo.operator.PutDown;
import demo.operator.Stack;
import demo.operator.Unstack;
import demo.predicate.On;
import demo.predicate.Predicate;
import demo.tree.Node;
import demo.tree.State;

public class Main {
	private static int S;
	private static Map<Character, Integer> sizeMap;
	private static BufferedWriter writer;
	private static int numTimesImpossible = 0, numTimesVisited = 0;

	public static void main(String[] args) throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		Info knewInfo = null;

		System.out.print("File: ");
		String filename = br.readLine();
		knewInfo = Util.loadFile(filename);

		writer = new BufferedWriter(new FileWriter(new File(filename + ".log")));

		S = knewInfo.getS();
		sizeMap = knewInfo.getSizeMap();

		writer.write("Authors: Edgar Zamora and Sergi Toda\nJanuary 2015\n");
		writer.write("------- Initial state -------\n");
		writer.write(knewInfo.getIniNode().getState() + "\n");
		writer.write("------- Final state -------\n");
		writer.write(knewInfo.getEndNode().getState() + "\n");
		writer.write("-----------------------------\n");

		// nodes will store the states that are not expand yet
		Queue<Node> nodes = new LinkedList<>();
		// tree is a HashSet that keeps in memory all the nodes or states
		// visited. Thanks to the hash we ensure that the cost of checking if a
		// node was visited is O(1)
		HashSet<State> tree = new HashSet<>();
		boolean found = false;

		nodes.add(knewInfo.getEndNode());

		long start = System.currentTimeMillis();
		while (nodes.size() > 0 && !found) {
			Node n = null;
			try {
				n = nodes.remove();
				found = expandNodes(n, nodes, knewInfo.getBlocks(), knewInfo.getIniNode(), tree);
				if (found) {
					writer.write("---------Solution found---------\n");
					Node aux = knewInfo.getIniNode();
					int i = 0;
					while (aux.getParent() != null) {
						writer.write("Node " + i + "\n");
						writer.write("Num. Columns Used: " + aux.getState().getNumColumsUsed() + "\n");
						writer.write(aux.getState().toString() + "\n");
						writer.write("Operation: " + aux.getOperator().toString() + "\n");
						aux = aux.getParent();
						i++;
					}
					// Last node
					writer.write("Node " + i);
					writer.write("Num. Columns Used: " + aux.getState().getNumColumsUsed() + "\n");
					writer.write(aux.getState().toString() + "\n");

					writer.write("Total size of the tree: " + tree.size() + "\n");
					writer.write("Height of the tree: " + i + "\n");
					writer.write("Total times an impossible state was found: " + numTimesImpossible + "\n");
					writer.write("Total times an state was visited more than once: " + numTimesVisited + "\n");

				}
			} catch (NoSuchElementException e) {
				System.out.println("Not found any possible solution");
			}
		}
		long end = System.currentTimeMillis();

		writer.write("Total time: " + (new SimpleDateFormat("mm:ss:SSS")).format(new Date((end - start))));

		br.close();
		writer.flush();
		writer.close();
		if (found) {
			System.out.println("A solutions was found. Please open the file" + filename + ".log");
		} else {
			System.out.println("No solution was found");
		}
	}

	/**
	 * This function expand the node passed by reference and checks from which
	 * states and which operators could be from. If it finds the initial node
	 * while it's expanding, it will stop and return true.
	 * 
	 * @param node
	 *            Node that we want to expand
	 * @param nodes
	 *            Queue of nodes where we'll enqueue the new nodes found that
	 *            are not the initial node and are possible
	 * @param blocks
	 *            Contains information about the blocks: A,B,C,D, etc.
	 * @param iniNode
	 *            When the initial node named as iniNode is found, this function
	 *            returns true
	 * @param tree
	 *            Maintains a Set with all nodes visited
	 * @return true if iniNode was found
	 * @throws IOException
	 */
	public static boolean expandNodes(Node node, Queue<Node> nodes, char[] blocks, Node iniNode, HashSet<State> tree)
			throws IOException {
		State s = node.getState();
		writer.write("Expanding node with state: \n");
		writer.write(s + "\n");

		// Check predicate by predicate which are the possible operators that
		// can bring us to the state s
		for (Predicate p : s.getPredicateList()) {
			List<Operator> operators = new LinkedList<>();
			switch (p.getName()) {
			case "OnTable":
				operators.add(new PutDown(p.getX()));
				break;
			case "Clear":
				for (char block : blocks) {
					operators.add(new Unstack(p.getX(), block));
				}
				break;
			case "EmptyHand":
				for (char block : blocks) {
					operators.add(new PutDown(block));
					for (char block2 : blocks) {
						// Checks that Size(x,a), Size (y,b), a<=b, x!=y
						if (block != block2 && sizeMap.get(block2) <= sizeMap.get(block)) {
							operators.add(new Stack(block2, block));
						}
					}
				}
				break;
			case "Holding":
				operators.add(new PickUp(p.getX()));
				for (char block : blocks) {
					operators.add(new Unstack(p.getX(), block));
				}
				break;
			case "On":
				for (char block : blocks) {
					// Checks that Size(x,a), Size (y,b), a<=b, x!=y
					if (p.getX() != block && sizeMap.get(p.getX()) <= sizeMap.get(block)) {
						operators.add(new Stack(p.getX(), block));
					}
				}
				break;
			case "Size":
				// This case is necessary in the state but, we don't need to
				// check anything because is check above (Size(x,a), Size (y,b),
				// a<=b, x!=y)
				break;
			default:
				return false;
			}

			// Some predicates can come from different operators. Here we'll
			// check if they can led to a possible state
			for (Operator o : operators) {
				int newNumColumsUsed = node.getState().getNumColumsUsed();
				// We are going back to the past!!
				if (o.getName() == "PutDown") {
					newNumColumsUsed--;
				}
				if (o.getName() == "PickUp") {
					newNumColumsUsed++;
				}

				// It's impossible to have less than 0 columns or more than S
				// because it's illegal
				if (newNumColumsUsed < 1 || newNumColumsUsed > S) {
					break;
				}

				State newState = createState(o, s.getPredicateList(), newNumColumsUsed);
				// If there's a possible node
				if (newState != null) {
					// //Check preconditions
					boolean impossible = false;

					impossible = isImpossible(o.getPreconditions(), newState.getPredicateList());
					State validState = new State(newState.getPredicateList(), newState.getNumColumsUsed());
					if (!impossible && !wasVisited(tree, validState)) {

						// System.out.println("LOG info: Is a valid Node :D ");
						// System.out.println("OPERATOR: " + o.toString());
						// System.out.println(validState.toString());

						// Check if it's the initial state
						if (validState.equals(iniNode.getState())) {
							// System.out.println("LOG info: Init node found it ");
							// System.out.println(validState.toString());
							iniNode.setParent(node);
							iniNode.setOperator(o);
							return true;
						} else {
							nodes.add(new Node(node, o, validState));
							tree.add(validState);
						}

					} else if (impossible) {
						numTimesImpossible++;
					} else {
						numTimesVisited++;
					}
				} else {
					numTimesImpossible++;
				}
			}
		}
		return false;

	}

	/**
	 * This function checks if some state was visited before
	 * 
	 * @param tree
	 *            HashSet that contains all the states visited
	 * @param newState
	 * @return
	 */
	private static boolean wasVisited(HashSet<State> tree, State newState) {
		if (tree.contains(newState)) {
			return true;
		}
		return false;
	}

	/**
	 * This function does the regression function using the following
	 * parameters:
	 * 
	 * @param o
	 *            Operator to check
	 * @param predicates
	 *            Predicates of the state
	 * @param numColumsUsed
	 *            Number of columns used
	 * @return newState with the predicates obtained using the regression
	 *         function. (To clarify, they don't have the predicates from the
	 *         operator's precondition)
	 */
	public static State createState(Operator o, HashSet<Predicate> predicates, int numColumsUsed) {
		HashSet<Predicate> list = new HashSet<>();
		for (Predicate p : predicates) {
			// do regression
			if (o.getEliminate().contains(p)) {
				return null; // State impossible
			}
			if (!o.getAdd().contains(p)) {
				list.add(p);
			}
		}

		return new State(list, numColumsUsed);
	}

	/**
	 * This function checks if a state is possible or not by looking at its
	 * operator preconditions and the predicates from the regression function
	 * 
	 * @param preconditions
	 * @param predicateList
	 * @return true if it is an impossible state
	 */
	private static boolean isImpossible(HashSet<Predicate> preconditions, HashSet<Predicate> predicateList) {
		// Check if the new state will be possible
		predicateList.addAll(preconditions);
		List<Predicate> list = new ArrayList<>();
		list.addAll(predicateList);
		for (int i = 0; i < list.size() - 1; i++) {
			Predicate p = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				Predicate p2 = list.get(j);
				switch (p.getName()) {
				case "OnTable":
					if (p2.getName() == "On" || p2.getName() == "Holding") {
						if (p.getX() == p2.getX()) {
							return true;
						}
					}
					break;
				case "Clear":
					if (p2.getName() == "On") {
						if (p.getX() == ((On) p2).getY()) {
							return true;
						}
					}

					break;
				case "EmptyHand":
					if (p2.getName() == "Holding") {
						return true;
					}
					break;
				case "Holding":
					if (p2.getName() == "EmptyHand") {
						return true;
					}
					if (p2.getName() == "On") {
						if (p.getX() == ((On) p2).getY() || p.getX() == p2.getX()) {
							return true;
						}
					}
					if (p2.getName() == "OnTable") {
						if (p.getX() == p2.getX()) {
							return true;
						}
					}

					break;
				case "On":
					if (p2.getName() == "On") {
						if (p.getX() == ((On) p2).getY() && p2.getX() == ((On) p).getY()) {
							return true;
						}
						if (p.getX() == p2.getX() && ((On) p).getY() != ((On) p2).getY()) {
							return true;
						}
					}
					if (p2.getName() == "OnTable" || p2.getName() == "Holding") {
						if (p.getX() == p2.getX()) {
							return true;
						}
					}
					if (p2.getName() == "Clear") {
						if (p2.getX() == ((On) p).getY()) {
							return true;
						}
					}
					break;
				case "Size":
					break;
				}
			}
		}

		return false;
	}

}
