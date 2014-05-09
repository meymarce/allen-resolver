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
