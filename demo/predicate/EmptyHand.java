package demo.predicate;

public class EmptyHand extends Predicate {

	private boolean empty;

	public EmptyHand() {
		super("EmptyHand");
		empty = true;
	}

	public EmptyHand(boolean empty) {
		super("EmptyHand");
		this.empty = empty;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	@Override
	public String toString() {
		return getName();
	}
}
