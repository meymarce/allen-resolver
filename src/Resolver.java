import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Resolver {

	//private Output output;
	
	public static void resolveConditions(List<Condition> conditions) {
		boolean completeness = true;
		Condition condition1 = null, condition2 = null, toProof;
		for( int i = 0; i < 3; ++i) {
			switch(i) {
			case 0:
				condition1 = conditions.get(1);
				condition2 = conditions.get(2);
				break;
			case 1:
				condition1 = conditions.get(0);
				condition2 = conditions.get(2);
				break;
			case 2:
				condition1 = conditions.get(0);
				condition2 = conditions.get(1);
				break;
			}
			toProof = conditions.get(i);
			
			completeness = resolveCondition(condition1, condition2, toProof) && completeness;
			completeness = resolveCondition(condition1, condition2, new Condition(5, toProof.getTo(), toProof.getFrom(), toProof.getInverseEdge())) && completeness;
		}
		
		if( !completeness ) {
			System.out.println("Das Netz ist nicht vollständig");
		} else {
			System.out.println("Das Netz ist vollständig");
		}
	}
	
	private static boolean resolveCondition(Condition condition1, Condition condition2, Condition toProof) {		
		Condition step1 = null, step2 = null, used = null, unused = null;
		List<String> resolvedallen, toproofallen, resultallen = new ArrayList<String>();
		
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
  				if( (resolvedallen.get(i).equals(toproofallen.get(j))) && !resultallen.contains(toproofallen.get(j)) ) {
					resultallen.add(toproofallen.get(j));
				}
			}
		}
			
		if( resultallen.isEmpty() ) {
			System.out.println("Fehler: Schnittmenge der Bedingung " + toProof.toString() + " ist leer und damit die Kante nicht konsistent");
		} else {
			String resultallenstring = resultallen.get(0);
	    	for( int i = 1; i < resultallen.size(); ++i ) {
	    		resultallenstring += "," + resultallen.get(i);
	    	}
			
			System.out.println("Die Schnittmenge von Bedingung " + toProof.toString() + " ist " + resultallenstring);
		}
		
		return !resultallen.isEmpty();		
	}
}
