

public class Condition {
	private int id;
	private Character from, to;
	private String edge;
	
	public Condition(int id, char from, char to, String edge) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.edge = edge;
	}	
	
	public int getId() {
		return this.id;
	}
	
	public char getFrom() {
		return this.from;
	}
	
	public char getTo() {
		return this.to;
	}
	
	public String getEdge() {
		return this.edge;
	}

	public String getInverseEdge() {
		return AllenTable.inverse(this.edge);
	}
	
	@Override
	public String toString() {
		return from + edge + to;	
	}
}
