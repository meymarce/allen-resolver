
/**
 * This class represents a condition in the net
 * 
 * @author marcel.meyer2@hp.com
 *
 */
public class Condition {
	private int id;
	private Character from, to;
	private String edge;
	
	/**
	 * Constructor of a condition
	 * 
	 * @param id A unique integer to identify the condition (uniqueness is not checked by the constructor)
	 * @param from The event the condition starts from
	 * @param to The event the condition ends at
	 * @param edge The relations between the two events
	 */
	public Condition(int id, Character from, Character to, String edge) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.edge = edge;
	}	
	
	/**
	 * Method to get the id of the condition
	 * 
	 * @return The conditions id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Method to get the starting event
	 * 
	 * @return A character representing the event
	 */
	public Character getFrom() {
		return this.from;
	}
	
	/**
	 * Method to get the finishing event
	 * 
	 * @return A character representing the event
	 */
	public Character getTo() {
		return this.to;
	}
	
	/**
	 * Method to get the relations of the condition
	 * 
	 * @return A string representing the relations (each relation is comma-separated)
	 */
	public String getEdge() {
		return this.edge;
	}
	
	/**
	 * Method to get the inverse relations of the condition
	 * 
	 * @return A string representing the inverse relations (each relation is comma-separated)
	 */
	public String getInverseEdge() {
		return AllenTable.inverse(this.edge);
	}
	
	/**
	 * Method to set the relations of the condition
	 * 
	 * @param value A string representing the relations to be set (each relation is comma-separated)
	 */
	public void setEdge(String value) {
		this.edge = value;
	}
	
	@Override
	public String toString() {
		return from + "{" + edge + "}" + to;	
	}
}
