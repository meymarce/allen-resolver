import java.util.List;
import java.util.Scanner;


public class UserInterface {
	
	public void startConversation() {
		Parser parser = new Parser();
		String conditionsInput = "";
		String bridgeInput = "";
		String choiceInput = "";
		Scanner s = new Scanner(System.in); 
		
		System.out.println("*****Allenparser******");
		System.out.println("Programm zum Pr�fen der Vollst�ndigkeit eines Zeitnetzes, definiert in Allen'scher Zeitlogik");
		System.out.println();
		System.out.println("**Definitionen**");
		System.out.println("Knoten: Gro�buchstabe, z.B. A");
		System.out.println("Kante: Allen'sche Relationen, z.B. <,>,=,d,di,m,mi,o,oi,s,si,f,fi");
		System.out.println("Geben Sie ihr Zeitnetz mit Knoten und Kanten in folgender Syntax ein: " +
				"Kante{Relation/en}Kante,Kante{Relation/en}Kante,Kante{Relation/en}Kante");
		System.out.println("Beispiel: A{<,mi}B|B{=,o}C|C{d,>}A");
		System.out.println("Optional: Angabe einer Br�cke, welche wie eine Relation definiert wird und mehreren Kanten hinzugef�gt wird");
		System.out.println("Beispiel: {<,=,m}");
		System.out.println();
		System.out.println("Die �berpr�fung erfolgt je mit und ohne Br�cke in 2 Schritten:");
		System.out.println("1. �berpr�fung der Konsistenz jeder Kante mit/ohne Br�cke, A->B, B->C, C->A usw.");
		System.out.println("2. �berpr�fung, ob das gesamte Netz vollst�ndig ist.");
		System.out.println("**********************");
		
		System.out.println("Geben Sie nun das zu l�sende Zeitnetz gem�� der o.g. Syntax ein: ");
		conditionsInput = s.next();
		
		// Resolving the net without the bridge
		if(parser.readConditions(conditionsInput) == false ) {
			System.out.println("Fehler: Zeitnetz konnte nicht erfolgreich �berpr�ft werden.");
		} else {
			parser.startResolving();
			System.out.println("Ende: Zeitnetz wurde erfolgreich �berpr�ft.");
			
			System.out.println("Geben Sie nun optional die Br�cke b an (Tippen Sie die Eingabetaste, falls sie leer sein soll): ");
			bridgeInput = s.next();
							
			// Resolving the net with the bridge
			if(parser.readBridge(bridgeInput) == false) {
				System.out.println("Fehler: Zeitnetz konnte nicht inklusive der Br�cke erfolgreich �berpr�ft werden.");
			} else {
				
				System.out.println("An folgenden Kanten kann die Br�cke b eingef�gt werden: ");
				List<Condition> conditions = parser.getConditions();
				
				for(Condition condition : conditions) {
					System.out.println("[" + condition.getId() + "] " + condition.toString());
				}
				
				System.out.println("Geben Sie an, an welcher/n Kante/n die Br�cke b hinzugef�gt werden soll: ");
				System.out.println("Beispiel: 1,2 ");
				choiceInput = s.next();
				
				parser.addBridge(choiceInput);
				
				parser.startResolving();
				System.out.println("Ende: Zeitnetz wurde erfolgreich �berpr�ft.");
			}
		}
	}
}