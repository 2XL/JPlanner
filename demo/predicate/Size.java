package demo.predicate;

public class Size extends Predicate {

	private int a;

	public Size(char x, int a) {
		super("Size", x);
		this.a = a;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	@Override
	public String toString() {
		return this.getName() + "(" + this.getX() + "," + a + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Size) {
			Size s = (Size) obj;
			return super.equals(obj) && this.a == s.getA();
		}
		return false;
	}
}
