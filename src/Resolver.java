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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Resolver is a class for providing functions proof the completeness of a net and therefore it is able to proof the consistency of single conditions
 * 
 * @author marcel.meyer2@hp.com
 *
 */
public class Resolver {
	private UserInterface ui;
	private List<Condition> conditions;
	
	/**
	 * Constructor of the Resolver
	 * 
	 * @param ui The {link UserInterface} that is going to be used
	 * @param conditions The list of conditions needed for the resolution and the proofing
	 */
	public Resolver(UserInterface ui, List<Condition> conditions) {
		this.ui = ui;
		this.conditions = conditions;
	}
	
	/**
	 * This function tries to resolve all possible variations and prints the output using the {@link UserInterface}
	 *  
	 */
	public void resolveConditions() {
		boolean completeness = true;
		Condition condition1 = null, condition2 = null, toProof;
		
		// mark the toProof condition and the two to use for proofing
		for( int i = 0; i < 3; ++i) {
			switch(i) {
			case 0:
				condition1 = this.conditions.get(1);
				condition2 = this.conditions.get(2);
				break;
			case 1:
				condition1 = this.conditions.get(0);
				condition2 = this.conditions.get(2);
				break;
			case 2:
				condition1 = this.conditions.get(0);
				condition2 = this.conditions.get(1);
				break;
			}
			toProof = this.conditions.get(i);
			
			// resolve and proof the condition and also its reverse
			completeness = resolveCondition(condition1, condition2, toProof) && completeness;
			completeness = resolveCondition(condition1, condition2, new Condition(5, toProof.getTo(), toProof.getFrom(), toProof.getInverseEdge())) && completeness;
		}
		
		// output the completeness using the UserInterface
		if( !completeness ) {
			ui.showErrorMessage("Das Netz ist nicht vollst�ndig");
		} else {
			ui.showStatusMessage("Das Netz ist vollst�ndig");
		}
	}
	
	/**
	 * This function tries to proof a condition and returns a boolean of success. It also prints the output of success using the {@link UserInterface}
	 * 
	 * @param condition1 The first condition to use for the proof (conditions are not sequence-faithful)
	 * @param condition2 The second condition to use for the proof (conditions are not sequence-faithful)
	 * @param toProof The condition to proof. This condition has to be in the order to be proven.
	 * @return True if {@link toProof} could be proven and false if not
	 */
	
	private boolean resolveCondition(Condition condition1, Condition condition2, Condition toProof) {		
		Condition step1 = null, step2 = null, used = null, unused = null;
		List<String> resolvedallen, toproofallen, resultallen = new ArrayList<String>();
		
		// Determine path to follow for proof
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
		
		// proof by using the pfunction and creating the intersection
		resolvedallen = Arrays.asList(AllenTable.pfunction(step1.getEdge(), step2.getEdge()).split(","));
		toproofallen = Arrays.asList(toProof.getEdge().split(","));
		for( int i = 0; i < resolvedallen.size(); ++i ) {
			for( int j = 0; j < toproofallen.size(); ++j ) {
  				if( (resolvedallen.get(i).equals(toproofallen.get(j))) && !resultallen.contains(toproofallen.get(j)) ) {
					resultallen.add(toproofallen.get(j));
				}
			}
		}

		// output the proofs result using the UserInterface
    	if( resultallen.isEmpty() ) {
			ui.showErrorMessage("Schnittmenge der Bedingung " + toProof.toString() + " �ber " + step1.toString() + ", " + step2.toString() + " ist leer und damit die Kante nicht konsistent");
		} else {
			String resultallenstring = resultallen.get(0);
	    	for( int i = 1; i < resultallen.size(); ++i ) {
	    		resultallenstring += "," + resultallen.get(i);
	    	}

			ui.showStatusMessage("Die Schnittmenge von Bedingung " + toProof.toString() + " �ber " + step1.toString() + ", " + step2.toString() + " ist " + resultallenstring);
		}
		
		return !resultallen.isEmpty();	//return if there is an intersection
	}
}
