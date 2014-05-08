import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Resolver {

	private List<Condition> conditions;
	//private Output output;
	
	public Resolver(List<Condition> conditions, String bridge) {
		this.conditions = conditions;	
	}
	
	public void resolveConditions() {
		
	}
	
	private boolean resolveCondition(Condition condition1, Condition condition2, Condition toProof) {
		Condition step1 = null, step2 = null, used = null, unused = null;
		List<String> resolvedallen, toproofallen, resultallen = new ArrayList<String>();
		boolean result = false;
		
		if( toProof.getFrom() == condition1.getFrom() ) {
			used=step1=condition1;			
		}
		if( toProof.getFrom() == condition2.getFrom() ) {
			used=step1=condition2;
		}
		if( toProof.getFrom() == condition1.getTo() ) {
			step1=new Condition(3, condition1.getTo(), condition1.getFrom(), condition1.getInverseEdge());
			used=condition1;
		}
		if( toProof.getFrom() == condition2.getTo() ) {
			step1=new Condition(3, condition2.getTo(), condition2.getFrom(), condition2.getInverseEdge());
			used = condition2;
		}
		if( used == condition1 ) {
			unused = condition2;
		} else {
			unused = condition1;
		}
		if( step1.getTo() == unused.getFrom() ) {
			step2 = unused;
		} else {
			step2 = new Condition(4, unused.getTo(), unused.getFrom(), unused.getInverseEdge());
		}
		
		resolvedallen = Arrays.asList(AllenTable.pfunction(step1.getEdge(), step2.getEdge()).split(","));
		toproofallen = Arrays.asList(toProof.getEdge().split(","));
		for( int i = 0; i < resolvedallen.size(); ++i ) {
			for( int j = 0; j < toproofallen.size(); ++j ) {
  				if( resolvedallen.get(i).equals(toproofallen.get(j)) && !resultallen.contains(toproofallen.get(j)) ) {
					resultallen.add(toproofallen.get(j));
				}
			}
		}
		
		// TODO: Output stuff to output
		if( resultallen.isEmpty() ) {
			System.out.println("Fehler: Schnittmenge der Bedingung " + toProof.toString() + " ist leer");
		}
		
		return !resultallen.isEmpty(); 
		
	}
	
	
	public static void main(String[] args) {
		Condition cond1, cond2, step;
		cond1 = new Condition(0, 'A', 'B', ">,<");
		cond2 = new Condition(1, 'B', 'C', "m,d");
		step = cond1;
		
		System.out.println((step == cond1));
		System.out.println(step.equals(cond1));
		
	}
}
