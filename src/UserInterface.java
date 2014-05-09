/*  
 * Copyright (c) 2014, Marcel Meyer, Sebastian Woitzik
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the "Duale Hochschule Baden-Wuerttemberg" nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
				
				// Check if the bridge is correct
				if(!parser.addBridge(choiceInput)) {
					return;
				}
				
				// Resolve with the bridge
				showStatusMessage("Überprüfung des Netzes...\n");
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
		System.out.println("FEHLER: " + error);	
	}
	
	/**
	 * Shows the current status to the user
	 * @param status  The status message
	 */
	public void showStatusMessage(String status) {
		System.out.println("STATUS: " + status);
	}
}