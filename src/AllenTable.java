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

import java.util.*;

/**
 * The AllenTable class provides functions to work with Allens relations
 * 
 * @author marcel.meyer2@hp.com
 *
 */
public class AllenTable {
	private static final HashMap<String, String> inv;
	private static final HashMap<String, String> mapping;
	
	/**
	 * Static initializer
	 * 
	 */
    static
    {
        inv = new HashMap<String, String>();
        inv.put("=", "=");
        inv.put("<", ">");
        inv.put(">", "<");
        inv.put("d", "di");
        inv.put("di", "d");
        inv.put("o", "oi");
        inv.put("oi", "o");
        inv.put("m", "mi");
        inv.put("mi", "m");
        inv.put("s", "si");
        inv.put("si", "s");
        inv.put("f", "fi");
        inv.put("fi", "f");
        
        mapping = new HashMap<String, String>();
        
        // "="
        mapping.put("=,=", "=");
        mapping.put("=,<", "<");
        mapping.put("=,>", ">");
        mapping.put("=,d", "d");
        mapping.put("=,di", "di");
        mapping.put("=,o", "o");
        mapping.put("=,oi", "oi");
        mapping.put("=,m", "m");
        mapping.put("=,mi", "mi");
        mapping.put("=,s", "s");
        mapping.put("=,si", "si");
        mapping.put("=,f", "f");
        mapping.put("=,fi", "fi");        
        // "<"
        mapping.put("<,=", "<");
        mapping.put("<,<", "<");
        mapping.put("<,>", "=,<,>,d,di,o,oi,m,mi,s,si,f,fi");
        mapping.put("<,d", "<,o,m,d,s");
        mapping.put("<,di", "<");
        mapping.put("<,o", "<");
        mapping.put("<,oi", "<,o,m,d,s");
        mapping.put("<,m", "<");
        mapping.put("<,mi", "<,o,m,d,s");
        mapping.put("<,s", "<");
        mapping.put("<,si", "<");
        mapping.put("<,f", "<,o,m,d,s");
        mapping.put("<,fi", "<");
        // ">"
        mapping.put(">,=", ">");
        mapping.put(">,<", "=,<,>,d,di,o,oi,m,mi,s,si,f,fi");
        mapping.put(">,>", ">");
        mapping.put(">,d", ">,oi,mi,d,f");
        mapping.put(">,di", ">");
        mapping.put(">,o", ">,oi,mi,d,f");
        mapping.put(">,oi", ">");
        mapping.put(">,m", ">,oi,mi,d,f");
        mapping.put(">,mi", ">");
        mapping.put(">,s", ">,oi,mi,d,f");
        mapping.put(">,si", ">");
        mapping.put(">,f", ">");
        mapping.put(">,fi", ">");
        // "d"
        mapping.put("d,=", "d");
        mapping.put("d,<", "<");
        mapping.put("d,>", ">");
        mapping.put("d,d", "d");
        mapping.put("d,di", "=,<,>,d,di,o,oi,m,mi,s,si,f,fi");
        mapping.put("d,o", "<,o,m,d,s");
        mapping.put("d,oi", ">,oi,mi,d,f");
        mapping.put("d,m", "<");
        mapping.put("d,mi", ">");
        mapping.put("d,s", "d");
        mapping.put("d,si", ">,oi,mi,d,f");
        mapping.put("d,f", "d");
        mapping.put("d,fi", "<,o,m,d,s");
        // "di"
        mapping.put("di,=", "di");
        mapping.put("di,<", "<,o,m,di,fi");
        mapping.put("di,>", "<,oi,mi,di,si");
        mapping.put("di,d", "=,d,di,o,oi,s,si,f,fi");
        mapping.put("di,di", "di");
        mapping.put("di,o", "o,di,fi");
        mapping.put("di,oi", "oi,di,si");
        mapping.put("di,m", "o,di,fi");
        mapping.put("di,mi", "oi,di,si");
        mapping.put("di,s", "o,di,fi");
        mapping.put("di,si", "di");
        mapping.put("di,f", "oi,di,si");
        mapping.put("di,fi", "di");
        // "o"
        mapping.put("o,=", "o");
        mapping.put("o,<", "<");
        mapping.put("o,>", ">,oi,mi,di,si");
        mapping.put("o,d", "o,d,s");
        mapping.put("o,di", "<,o,m,di,fi");
        mapping.put("o,o", "<,o,m");
        mapping.put("o,oi", "=,d,di,o,oi,s,si,f,fi");
        mapping.put("o,m", "<");
        mapping.put("o,mi", "oi,di,si");
        mapping.put("o,s", "o");
        mapping.put("o,si", "di,fi,o");
        mapping.put("o,f", "d,s,o");
        mapping.put("o,fi", "<,o,m");
        // "oi"
        mapping.put("oi,=", "oi");
        mapping.put("oi,<", "<,o,m,di,fi");
        mapping.put("oi,>", ">");
        mapping.put("oi,d", "oi,d,f");
        mapping.put("oi,di", "<,oi,mi,di,si");
        mapping.put("oi,o", "=,d,di,o,oi,s,si,f,fi");
        mapping.put("oi,oi", ">,oi,mi");
        mapping.put("oi,m", "o,di,fi");
        mapping.put("oi,mi", ">");
        mapping.put("oi,s", "oi,d,f");
        mapping.put("oi,si", ">,oi,mi");
        mapping.put("oi,f", "oi");
        mapping.put("oi,fi", "oi,di,si");
        // "m"
        mapping.put("m,=", "m");
        mapping.put("m,<", "<");
        mapping.put("m,>", ">,oi,mi,di,si");
        mapping.put("m,d", "o,d,s");
        mapping.put("m,di", "<");
        mapping.put("m,o", "<");
        mapping.put("m,oi", "o,d,s");
        mapping.put("m,m", "<");
        mapping.put("m,mi", "=,f,fi");
        mapping.put("m,s", "m");
        mapping.put("m,si", "m");
        mapping.put("m,f", "d,s,o");
        mapping.put("m,fi", "<");
        // "mi"
        mapping.put("mi,=", "mi");
        mapping.put("mi,<", "<,o,m,di,fi");
        mapping.put("mi,>", ">");
        mapping.put("mi,d", "oi,d,f");
        mapping.put("mi,di", ">");
        mapping.put("mi,o", "oi,d,f");
        mapping.put("mi,oi", ">");
        mapping.put("mi,m", "=,s,si");
        mapping.put("mi,mi", ">");
        mapping.put("mi,s", "oi,d,f");
        mapping.put("mi,si", ">");
        mapping.put("mi,f", "mi");
        mapping.put("mi,fi", "mi");
        // "s"
        mapping.put("s,=", "=");
        mapping.put("s,<", "<");
        mapping.put("s,>", ">");
        mapping.put("s,d", "d");
        mapping.put("s,di", "<,o,m,di,fi");
        mapping.put("s,o", "<,o,m");
        mapping.put("s,oi", "oi,d,f");
        mapping.put("s,m", "<");
        mapping.put("s,mi", "mi");
        mapping.put("s,s", "s");
        mapping.put("s,si", "=,s,si");
        mapping.put("s,f", "d");
        mapping.put("s,fi", "<,o,m");
        // "si"
        mapping.put("si,=", "si");
        mapping.put("si,<", "<,o,m,di,fi");
        mapping.put("si,>", ">");
        mapping.put("si,d", "oi,d,f");
        mapping.put("si,di", "di");
        mapping.put("si,o", "o,di,fi");
        mapping.put("si,oi", "oi");
        mapping.put("si,m", "o,di,fi");
        mapping.put("si,mi", "mi");
        mapping.put("si,s", "=,s,si");
        mapping.put("si,si", "si");
        mapping.put("si,f", "oi");
        mapping.put("si,fi", "di");
        // "f"
        mapping.put("f,=", "f");
        mapping.put("f,<", "<");
        mapping.put("f,>", ">");
        mapping.put("f,d", "d");
        mapping.put("f,di", ">,oi,mi,di,si");
        mapping.put("f,o", "d,o,s");
        mapping.put("f,oi", ">,oi,mi");
        mapping.put("f,m", "m");
        mapping.put("f,mi", ">");
        mapping.put("f,s", "d");
        mapping.put("f,si", ">,oi,mi");
        mapping.put("f,f", "f");
        mapping.put("f,fi", "=,f,fi");
        // "fi"
        mapping.put("fi,=", "fi");
        mapping.put("fi,<", "<");
        mapping.put("fi,>", ">,oi,mi,di,si");
        mapping.put("fi,d", "o,d,s");
        mapping.put("fi,di", "di");
        mapping.put("fi,o", "o");
        mapping.put("fi,oi", "oi,di,si");
        mapping.put("fi,m", "m");
        mapping.put("fi,mi", "oi,di,si");
        mapping.put("fi,s", "o");
        mapping.put("fi,si", "di");
        mapping.put("fi,f", "=,f,fi");
        mapping.put("fi,fi", "fi");
        
    }
    
    /**
     * Function to inverse a comma-separated set of Allen relations
     * 
     * @param relations The set of comma-separated Allen relations
     * @return The inverse set of comma-separated Allen relations
     */
    public static String inverse(String relations) {
    	String[] splittedrelations = relations.split(",");
    	
    	//inverse each relation and concatenate them comma-separated
    	String result = inv.get(splittedrelations[0]);
    	for( int i = 1; i < splittedrelations.length; ++i ) {
    		result += "," + inv.get(splittedrelations[i]);
    	}
    	
    	return result;
    }
    
    /**
     * Function to calculate the pfunction of Allen
     * 
     * @param relation1 The first set of comma-separated Allen relations
     * @param relation2 The second set of comma-separated Allen relations
     * @return The result of the pfunction
     */
    public static String pfunction(String relation1, String relation2) {
    	List<String> splittedrelation1 = Arrays.asList(relation1.split(",")), splittedrelation2 = Arrays.asList(relation2.split(",")), resolvedrelations = new ArrayList<String>(), resultrelations = new ArrayList<String>();
    	
    	// map all relations with each other and add each relation of the result to the resultrelation if not already present
    	for( int i = 0; i < splittedrelation1.size(); ++i) {
    		for( int k = 0; k < splittedrelation2.size(); ++k) {
    			resolvedrelations = Arrays.asList(mapping.get(splittedrelation1.get(i) + "," + splittedrelation2.get(k)).split(","));
    			for( int l = 0; l < resolvedrelations.size(); ++l) {
    				if( !resultrelations.contains(resolvedrelations.get(l)) ) {
    					resultrelations.add(resolvedrelations.get(l));
    				}
    			}
       		} 		
    	}
    	
    	// build a string out of it
    	String result = resultrelations.get(0);
    	for( int i = 1; i < resultrelations.size(); ++i ) {
    		result += "," + resultrelations.get(i);
    	}
    	
    	return result;
    }
    
	
	
	
}
