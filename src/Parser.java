import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {
	private final Pattern COND_REGEX = Pattern.compile("^[A-Z]\\{(=|<|>|d|di|o|oi|m|mi|s|si|f|fi)(,(=|<|>|d|di|o|oi|m|mi|s|si|f|fi))*\\}[A-Z]$");
	private final Pattern COND_FROM_REGEX = Pattern.compile("^[A-Z]");
	private final Pattern COND_TO_REGEX = Pattern.compile("[A-Z]$");
	private final Pattern COND_EDGE_REGEX = Pattern.compile("\\{(=|<|>|d|di|o|oi|m|mi|s|si|f|fi)(,(=|<|>|d|di|o|oi|m|mi|s|si|f|fi))*\\}");
	private final Pattern COND_EDGE_CHOICE_REGEX = Pattern.compile("[0-2](,[0-2])*");
	
	private List<Condition> conditions;
	private List<String> bridge;
	private Resolver resolver;
	
	
	public Parser() {
		this.conditions = new ArrayList<Condition>();
		this.bridge = new ArrayList<String>();
	}
	
	public boolean startResolving() {
			Resolver.resolveConditions(conditions);
			
		return true;
	}
	
	public boolean addBridge(String input) {
		Matcher edgeChoiceMatcher = COND_EDGE_CHOICE_REGEX.matcher(input);
		
		if(edgeChoiceMatcher.find() == false) {
			System.out.println("Fehler: Ihre Auswahl " + input + " ist nicht korrekt.");
			
			return false;
		} else {
			System.out.println("Choice: " + input);
			List<String> splittedChoices = Arrays.asList(input.split(","));
			
			for(String choice : splittedChoices) {
				for(Condition condition : conditions) {
					if(condition.getId() == Integer.parseInt(choice)) {
						List<String> edgeList = new ArrayList<String>(Arrays.asList(condition.getEdge().split(",")));
						
						for(String bridgeRelation : bridge) {
							if( !edgeList.contains(bridgeRelation) ) {
								edgeList.add(bridgeRelation);
		    				}
						}
						
						String edgeWithBridge = edgeList.get(0);
				    	int i = 1;
				    	while( i < edgeList.size()) {
				    		edgeWithBridge += "," + edgeList.get(i);
				    		++i;
				    	}
				    	
				    	condition.setEdge(edgeWithBridge);
					}
				}
			}
		}
		
		return true;
	}
	
	public boolean readConditions(String input) {
		List<String> splittedConditions = Arrays.asList(input.split("\\|"));

		if(splittedConditions.size() != 3) {
			System.out.println("Fehler: Es müssen mindesten 3 Randbedingungen angegeben werden.");
			
			return false;
		}
		
		int i = 0;
		for (String condition : splittedConditions) {			
			Matcher syntaxMatcher = COND_REGEX.matcher(condition);
			
			if(syntaxMatcher.find() == false) {
				System.out.println("Fehler: Syntaxfehler in der Bedingung " + condition);
				
				return false;
			}
			
			Matcher fromMatcher = COND_FROM_REGEX.matcher(condition);
			Matcher toMatcher = COND_TO_REGEX.matcher(condition);
			Matcher edgeMatcher = COND_EDGE_REGEX.matcher(condition);
			
			if(fromMatcher.find() == true && toMatcher.find() == true && edgeMatcher.find() == true) {
				Character from = fromMatcher.group(0).charAt(0);
				System.out.println(from);	
				
				Character to = toMatcher.group(0).charAt(0);
				System.out.println(to);	
				
				String edge = edgeMatcher.group(0).substring(1, edgeMatcher.group(0).length()-1);
				System.out.println(edge);
				Condition parsedCondition = new Condition(i, from, to, edge);  
				System.out.println(condition.toString());
				this.conditions.add(parsedCondition);
			} else {
				System.out.println("Fehler: Parsen der Informationen fehlgeschlagen.");
				
				return false;
			}
		
			++i;
		}
		
		return true;
	}
	
	public boolean readBridge(String input) {
		Matcher edgeMatcher = COND_EDGE_REGEX.matcher(input);
		if(edgeMatcher.find() == true) {
			this.bridge.add(edgeMatcher.group(0).substring(1, edgeMatcher.group(0).length()-1));
		} else {
			System.out.println("Fehler: Syntaxfehler in der Bridge " + input);
			return false;
		}
		
		return true;
	}

	public List<Condition> getConditions() {
		return conditions;
	}	
}
