

public class Condition {
	private char from, to;
	private String edge;
	
	public Condition(char from, char to, String edge) {
		this.from = from;
		this.to = to;
		this.edge = edge;
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
	
}
