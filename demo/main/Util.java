package demo.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import demo.predicate.Clear;
import demo.predicate.EmptyHand;
import demo.predicate.Holding;
import demo.predicate.On;
import demo.predicate.OnTable;
import demo.predicate.Predicate;
import demo.predicate.Size;
import demo.tree.Node;
import demo.tree.State;

/**
 * This class is used in order to read and load a file
 *
 */
public class Util {

	/**
	 * Reads a file that matches the format specified in
	 * PracticalExercise2Planner.pdf
	 * 
	 * @param filename
	 * @return Info with the information read
	 * @throws IOException
	 */
	public static Info loadFile(String filename) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		int s;
		String line;
		char[] blocks;
		State iniState = null, endState = null;
		int countOnTable = 0;
		Map<Character, Integer> sizeMap = new HashMap<>();

		// Read the first line where S is defined
		line = reader.readLine();
		if (line.startsWith("S=")) {
			s = Integer.parseInt(line.split("S=")[1]);
		} else {
			reader.close();
			throw new IOException("First line is not started by S");
		}

		// Read Blocks
		line = reader.readLine();
		if (line.startsWith("Blocks=")) {
			String[] blocksAux = line.split("Blocks=")[1].replace(";", "").split(",");
			blocks = new char[blocksAux.length];
			for (int i = 0; i < blocksAux.length; i++) {
				blocks[i] = blocksAux[i].charAt(0);
			}
		} else {
			reader.close();
			throw new IOException("Second line is not started by Blocks");
		}

		// Read InitialState
		line = reader.readLine();
		if (line.startsWith("InitialState=")) {
			String[] aux = line.split("InitialState=")[1].split(";");
			List<String> predicates = new ArrayList<String>(Arrays.asList(aux));
			// Read lines until we find the GoalState
			while ((line = reader.readLine()) != null && !line.startsWith("GoalState=")) {
				predicates.addAll(Arrays.asList(line.split(";")));
			}
			// Once all lines are read, get predicates
			HashSet<Predicate> list = new HashSet<>();
			for (String p : predicates) {
				list.add(getPredicate(p));
				if (p.startsWith("ONTABLE")) {
					countOnTable++;
				} else if (p.startsWith("SIZE")) {
					char block = p.charAt(5);
					int size = p.charAt(7) - '0';
					sizeMap.put(block, size);
				}
			}
			iniState = new State(list, countOnTable);
		} else {
			reader.close();
			throw new IOException("Third line is not started by InitialState");
		}

		if (line.startsWith("GoalState=")) {
			String[] aux = line.split("GoalState=")[1].split(";");
			List<String> predicates = new ArrayList<String>(Arrays.asList(aux));
			// Read lines until we find the GoalState
			while ((line = reader.readLine()) != null) {
				predicates.addAll(Arrays.asList(line.split(";")));
			}
			countOnTable = 0;
			// Once all lines are read, get predicates
			HashSet<Predicate> list = new HashSet<>();
			for (String p : predicates) {
				list.add(getPredicate(p));
				if (p.startsWith("ONTABLE")) {
					countOnTable++;
				}
			}
			endState = new State(list, countOnTable);
		} else {
			reader.close();
			throw new IOException("Cannot find GoalState");
		}

		reader.close();
		return new Info(new Node(null, null, endState), new Node(null, null, iniState), blocks, s, sizeMap);

	}

	private static Predicate getPredicate(String pred) {
		Predicate predicate = null;

		String[] aux = pred.split("\\(");

		switch (aux[0]) {
		case "ONTABLE":
			predicate = new OnTable(aux[1].charAt(0));
			break;
		case "ON":
			predicate = new On(aux[1].charAt(0), aux[1].charAt(2));
			break;
		case "CLEAR":
			predicate = new Clear(aux[1].charAt(0));
			break;
		case "EMPTYHAND":
			predicate = new EmptyHand();
			break;
		case "HOLDING":
			predicate = new Holding(aux[1].charAt(0));
			break;
		case "SIZE":
			//The size must be a number from 0 to 9
			predicate = new Size(aux[1].charAt(0), aux[1].charAt(2) - '0');
			break;
		default:

		}

		return predicate;
	}
}