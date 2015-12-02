package demo.predicate;

public class On extends Predicate {

	private char y;

	public On(char x, char y) {
		super("On", x);
		this.y = y;
	}

	public char getY() {
		return y;
	}

	@Override
	public String toString() {
		return getName() + "(" + getX() + ", " + y + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof On) {
			On o = (On) obj;
			return super.equals(obj) && this.y == o.getY();
		}
		return false;
	}
}
