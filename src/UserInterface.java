import java.util.List;
import java.util.Scanner;

/**
 * This class is used to interact with the user
 * 
 * @author woitzik
 *
 */
public class UserInterface {
		
	/**
	 * This method starts the interaction with the user
	 */
	public void startConversation() {
		Parser parser = new Parser(this);
		String conditionsInput = "";
		String bridgeInput = "";
		String choiceInput = "";
		Scanner s = new Scanner(System.in); 
		
		// Description of the application
		System.out.println("*****Allenparser******");
		System.out.println("Programm zum Prüfen der Vollständigkeit eines Zeitnetzes, definiert in Allen'scher Zeitlogik.\n");
		System.out.println("**Definitionen**");
		System.out.println("Knoten: Großbuchstabe, z.B. A");
		System.out.println("Kante: Allen'sche Relationen, z.B. <,>,=,d,di,m,mi,o,oi,s,si,f,fi\n");
		System.out.println("Geben Sie Ihr Zeitnetz mit Knoten und Kanten in folgender Syntax ein: " +
				"Kante{Relation/en}Kante|Kante{Relation/en}Kante|Kante{Relation/en}Kante");
		System.out.println("Beispiel: A{<}B|B{=,o}C|C{d,>}A\n");
		System.out.println("Optional: Im Anschluss Angabe einer Brücke, welche wie eine Relation definiert wird und mehreren Kanten hinzugefügt werden kann.");
		System.out.println("Beispiel: {<,=,m}\n");
		System.out.println("Die Überprüfung erfolgt je mit und ohne Brücke in 2 Schritten:");
		System.out.println("1. Überprüfung der Konsistenz jeder Kante mit/ohne Brücke, A->B, B->C, C->A usw.");
		System.out.println("2. Überprüfung, ob das gesamte Netz vollständig ist.");
		System.out.println("**********************\n");
		
		System.out.println("Geben Sie nun das zu lösende Zeitnetz gemäß der o.g. Syntax ein: ");
		conditionsInput = s.next();
		
		System.out.println();
		showStatusMessage("Überprüfung des Netzes...\n");
		
		// Resolving the net without the bridge
		if(parser.readConditions(conditionsInput) == false) {
			showErrorMessage("Zeitnetz konnte nicht erfolgreich überprüft werden.");
		} else {
			parser.startResolving();
			System.out.println();
			showStatusMessage("Zeitnetz wurde überprüft.\n");
			System.out.println("Geben Sie nun die Brücke b an oder beenden Sie das Programm: ");
			bridgeInput = s.next();
			System.out.println();
					
			// Resolving the net with the bridge
			if(parser.readBridge(bridgeInput) == false) {
				showErrorMessage("Zeitnetz konnte nicht inklusive der Brücke erfolgreich überprüft werden.");
			} else {
				
				System.out.println("An folgenden Kanten kann die Brücke b eingefügt werden: ");
				List<Condition> conditions = parser.getConditions();
				
				for(Condition condition : conditions) {
					System.out.println("[" + condition.getId() + "] " + condition.toString());
				}
				
				System.out.println("\nGeben Sie an, an welcher/n Kante/n die Brücke b hinzugefügt werden soll: ");
				System.out.println("Beispiel: 1,2 ");
				
				choiceInput = s.next();
				
				System.out.println();
				showStatusMessage("Überprüfung des Netzes...\n");
				
				parser.addBridge(choiceInput);
				parser.startResolving();
				System.out.println();
				showStatusMessage("Zeitnetz wurde mit der Brücke b überprüft.");
			}
		}
	}
	
	/**
	 * Shows an error to the user
	 * @param error The error message
	 */
	public void showErrorMessage(String error) {
		System.err.println("Fehler: " + error);
	}
	
	/**
	 * Shows the current status to the user
	 * @param status  The status message
	 */
	public void showStatusMessage(String status) {
		System.out.println("Status: " + status);
	}
}
