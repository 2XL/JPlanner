package demo.predicate;

/**
 * Abstract class that defines a Predicate
 *
 */
public abstract class Predicate {

	private String name;
	private char x;

	public Predicate(String name) {
		this.name = name;
	}

	public Predicate(String name, char x) {
		this.name = name;
		this.x = x;
	}

	public String getName() {
		return name;
	}

	public char getX() {
		return x;
	}

	@Override
	public String toString() {
		return name + "(" + x + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Predicate) {
			Predicate p = (Predicate) obj;
			if (this.name == p.getName() && this.x == p.getX()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
