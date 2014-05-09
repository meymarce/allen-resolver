import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class parses a given string by the user to define the net for the resolution
 * 
 * @author woitzik
 *
 */
public class Parser {
	private final Pattern COND_REGEX = Pattern.compile("^[A-Z]\\{(=|<|>|d|di|o|oi|m|mi|s|si|f|fi)(,(=|<|>|d|di|o|oi|m|mi|s|si|f|fi))*\\}[A-Z]$");
	private final Pattern COND_FROM_REGEX = Pattern.compile("^[A-Z]");
	private final Pattern COND_TO_REGEX = Pattern.compile("[A-Z]$");
	private final Pattern COND_EDGE_REGEX = Pattern.compile("\\{(=|<|>|d|di|o|oi|m|mi|s|si|f|fi)(,(=|<|>|d|di|o|oi|m|mi|s|si|f|fi))*\\}");
	private final Pattern COND_EDGE_CHOICE_REGEX = Pattern.compile("^[0-2](,[0-2])*$");
	
	private UserInterface ui;
	private Resolver resolver;
	private List<Condition> conditions;
	private List<String> bridge;
	
	/**
	 * Default constructor
	 */
	public Parser(UserInterface ui) {
		this.conditions = new ArrayList<Condition>();
		this.bridge = new ArrayList<String>();
		this.ui = ui;
	}
	
	/**
	 * Starts resolving of the net
	 */
	public void startResolving() {
		resolver = new Resolver(this.ui, conditions);	
		resolver.resolveConditions();
	}
	
	/**
	 * Parses the conditions string given by the user
	 * 
	 * @param input A string of at least 3 conditions to create the net. Format: Edge{Relation/s}Edge,Edge{Relation/s}Edge,Edge{Relation/s}Edge
	 * @return True {Parsing completed} False {Error while parsing the given conditions}
	 */
	public boolean readConditions(String input) {
		List<String> splittedConditions = Arrays.asList(input.split("\\|"));
		
		// The user entered < 3 conditions
		if(splittedConditions.size() != 3) {
			ui.showErrorMessage("Es müssen genau 3 Randbedingungen angegeben werden.");
			
			return false;
		}
		
		// Check syntax of the complete conditions string
		int i = 0;
		for (String condition : splittedConditions) {			
			Matcher syntaxMatcher = COND_REGEX.matcher(condition);
			
			// Checks if the syntax of the conditions string is correct
			if(syntaxMatcher.find() == false) {
				ui.showErrorMessage("Syntaxfehler in der Bedingung " + condition);
				
				return false;
			}
			
			// Applies the regex definitions on the condition
			// e.g. A{<,=}B
			Matcher fromMatcher = COND_FROM_REGEX.matcher(condition); // A
			Matcher toMatcher = COND_TO_REGEX.matcher(condition); // B
			Matcher edgeMatcher = COND_EDGE_REGEX.matcher(condition); // <,=
			
			// Checks whether a valid condition was given
			if(fromMatcher.find() == true && toMatcher.find() == true && edgeMatcher.find() == true) {
				Character from = fromMatcher.group(0).charAt(0);		
				Character to = toMatcher.group(0).charAt(0);
				String edgeWithPossibleDupl = edgeMatcher.group(0).substring(1, edgeMatcher.group(0).length()-1);
				String edge = "";
				
				// Check for duplicates in the given relation part, e.g. {<,<,<,m,s,m}
				List<String> relationList = new LinkedList<String>(Arrays.asList(edgeWithPossibleDupl.split(",")));
				if(relationList.size() == 1) {
					edge = relationList.get(0);
				} else {
					edge = relationList.get(0);
					relationList.remove(edge);
					
					for (String relation : relationList) {
						if(!edge.contains(relation)) {
							edge+="," + relation;
						}
					}
				}
				
				Condition parsedCondition = new Condition(i, from, to, edge);  
				this.conditions.add(parsedCondition);
				
			} else {
				ui.showErrorMessage("Parsen der Informationen fehlgeschlagen.");
				
				return false;
			}
		
			++i;
		}
		
		// Check for any contradictions in the parsed conditions
		if(checkForContradictions() == false) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Parses the bridge given by the user
	 * @param input A string of the format {<,=} or ({Relation/s})
	 * @return True {Parsing completed} False {Error while parsing the given bridge}
	 */
	public boolean readBridge(String input) {
		Matcher edgeMatcher = COND_EDGE_REGEX.matcher(input);
		if(edgeMatcher.find() == true) {
			this.bridge.add(edgeMatcher.group(0).substring(1, edgeMatcher.group(0).length()-1));
		} else {
			ui.showErrorMessage("Syntaxfehler in der Bridge " + input);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Adds the bridge to the edges of the conditions, chosen by the user
	 * @param input The chosen edges, e.g. 0,2
	 * @return True {Adding completed} False {Error because of an invalid input}
	 */
	public boolean addBridge(String input) {
		Matcher edgeChoiceMatcher = COND_EDGE_CHOICE_REGEX.matcher(input);
		
		// Checks the validation of the choice input
		if(edgeChoiceMatcher.find() == false) {
			ui.showErrorMessage("Ihre Auswahl " + input + " ist nicht korrekt.");
			
			return false;
		} else {
			List<String> splittedChoices = Arrays.asList(input.split(","));
			
			// Adds for each chosen edge the bridge to the relations string
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
	
	/**
	 * This method searches for contradictions in the given conditions. Contradictions are:
	 * A->A
	 * A->B and B->A
	 * @return True {No contradictions found} False {If a contradiction was found}
	 */
	public boolean checkForContradictions() {
		//A->A
		for(Condition condition : conditions) {
			if(condition.getFrom() == condition.getTo()) {
				ui.showErrorMessage("Diese Bedingung ist nicht möglich " + condition.toString());
				
				return false;
			}
		}
		
		//A->B and B->A or A->B and A->B 
		for(int i=0; i<conditions.size();++i) {
			for(int j=1; j<conditions.size();++j) {
				//A->B and B->A
				if((conditions.get(i).getFrom() == conditions.get(j).getTo()) && (conditions.get(j).getFrom() == conditions.get(i).getTo())) {
					ui.showErrorMessage("Diese Bedingungen sind nicht möglich " + conditions.get(i) + " " + conditions.get(j));
					
					return false;
				}
				
				//A->B and A->B
				if((conditions.get(i) != conditions.get(j)) && (conditions.get(i).getFrom() == conditions.get(j).getFrom()) && (conditions.get(j).getTo() == conditions.get(i).getTo())) {
					ui.showErrorMessage("Diese Bedingungen sind nicht möglich " + conditions.get(i) + " " + conditions.get(j));
					
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Returns a list with the entered conditions
	 * @return The list with the conditions
	 */
	public List<Condition> getConditions() {
		return conditions;
	}	
}
