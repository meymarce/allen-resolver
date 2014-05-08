import java.lang.reflect.Array;
import java.util.*;

public class AllenTable {
	private static final HashMap<String, String> inv;
	private static final HashMap<String, HashMap<String, String>> mapping;
	
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
        
        mapping = new HashMap<String, HashMap<String, String>>();
        
        // "<"
        mapping.put("=", new HashMap<String,String>() {{ put("<", "<"); }});
        mapping.put("=", new HashMap<String,String>() {{ put(">", ">"); }});
        mapping.put("=", new HashMap<String,String>() {{ put("d", "d"); }});
        mapping.put("=", new HashMap<String,String>() {{ put("di", "di"); }});
        mapping.put("=", new HashMap<String,String>() {{ put("o", "o"); }});
        mapping.put("=", new HashMap<String,String>() {{ put("oi", "oi"); }});
        mapping.put("=", new HashMap<String,String>() {{ put("m", "m"); }});
        mapping.put("=", new HashMap<String,String>() {{ put("mi", "mi"); }});
        mapping.put("=", new HashMap<String,String>() {{ put("s", "s"); }});
        mapping.put("=", new HashMap<String,String>() {{ put("si", "si"); }});
        mapping.put("=", new HashMap<String,String>() {{ put("f", "f"); }});
        mapping.put("=", new HashMap<String,String>() {{ put("fi", "fi"); }});        
        // "<"
        mapping.put("<", new HashMap<String,String>() {{ put("<", "<"); }});
        mapping.put("<", new HashMap<String,String>() {{ put(">", "=,<,>,d,di,o,oi,m,mi,s,si,f,fi"); }});
        mapping.put("<", new HashMap<String,String>() {{ put("d", "<,o,m,d,s"); }});
        mapping.put("<", new HashMap<String,String>() {{ put("di", "<"); }});
        mapping.put("<", new HashMap<String,String>() {{ put("o", "<"); }});
        mapping.put("<", new HashMap<String,String>() {{ put("oi", "<,o,m,d,s"); }});
        mapping.put("<", new HashMap<String,String>() {{ put("m", "<"); }});
        mapping.put("<", new HashMap<String,String>() {{ put("mi", "<,o,m,d,s"); }});
        mapping.put("<", new HashMap<String,String>() {{ put("s", "<"); }});
        mapping.put("<", new HashMap<String,String>() {{ put("si", "<"); }});
        mapping.put("<", new HashMap<String,String>() {{ put("f", "<,o,m,d,s"); }});
        mapping.put("<", new HashMap<String,String>() {{ put("fi", "<"); }});
        // ">"
        mapping.put(">", new HashMap<String,String>() {{ put("<", "=,<,>,d,di,o,oi,m,mi,s,si,f,fi"); }});
        mapping.put(">", new HashMap<String,String>() {{ put(">", ">"); }});
        mapping.put(">", new HashMap<String,String>() {{ put("d", ">,oi,mi,d,f"); }});
        mapping.put(">", new HashMap<String,String>() {{ put("di", ">"); }});
        mapping.put(">", new HashMap<String,String>() {{ put("o", ">,oi,mi,d,f"); }});
        mapping.put(">", new HashMap<String,String>() {{ put("oi", ">"); }});
        mapping.put(">", new HashMap<String,String>() {{ put("m", ">,oi,mi,d,f"); }});
        mapping.put(">", new HashMap<String,String>() {{ put("mi", ">"); }});
        mapping.put(">", new HashMap<String,String>() {{ put("s", ">,oi,mi,d,f"); }});
        mapping.put(">", new HashMap<String,String>() {{ put("si", ">"); }});
        mapping.put(">", new HashMap<String,String>() {{ put("f", ">"); }});
        mapping.put(">", new HashMap<String,String>() {{ put("fi", ">"); }});
        // "d"
        mapping.put("d", new HashMap<String,String>() {{ put("<", "<"); }});
        mapping.put("d", new HashMap<String,String>() {{ put(">", ">"); }});
        mapping.put("d", new HashMap<String,String>() {{ put("d", "d"); }});
        mapping.put("d", new HashMap<String,String>() {{ put("di", "=,<,>,d,di,o,oi,m,mi,s,si,f,fi"); }});
        mapping.put("d", new HashMap<String,String>() {{ put("o", "<,o,m,d,s"); }});
        mapping.put("d", new HashMap<String,String>() {{ put("oi", ">,oi,mi,d,f"); }});
        mapping.put("d", new HashMap<String,String>() {{ put("m", "<"); }});
        mapping.put("d", new HashMap<String,String>() {{ put("mi", ">"); }});
        mapping.put("d", new HashMap<String,String>() {{ put("s", "d"); }});
        mapping.put("d", new HashMap<String,String>() {{ put("si", ">,oi,mi,d,f"); }});
        mapping.put("d", new HashMap<String,String>() {{ put("f", "d"); }});
        mapping.put("d", new HashMap<String,String>() {{ put("fi", "<,o,m,d,s"); }});
        // "di"
        mapping.put("di", new HashMap<String,String>() {{ put("<", "<,o,m,di,fi"); }});
        mapping.put("di", new HashMap<String,String>() {{ put(">", "<,oi,mi,di,si"); }});
        mapping.put("di", new HashMap<String,String>() {{ put("d", "=,d,di,o,oi,s,si,f,fi"); }});
        mapping.put("di", new HashMap<String,String>() {{ put("di", "di"); }});
        mapping.put("di", new HashMap<String,String>() {{ put("o", "o,di,fi"); }});
        mapping.put("di", new HashMap<String,String>() {{ put("oi", "oi,di,si"); }});
        mapping.put("di", new HashMap<String,String>() {{ put("m", "o,di,fi"); }});
        mapping.put("di", new HashMap<String,String>() {{ put("mi", "oi,di,si"); }});
        mapping.put("di", new HashMap<String,String>() {{ put("s", "o,di,fi"); }});
        mapping.put("di", new HashMap<String,String>() {{ put("si", "di"); }});
        mapping.put("di", new HashMap<String,String>() {{ put("f", "oi,di,si"); }});
        mapping.put("di", new HashMap<String,String>() {{ put("fi", "di"); }});
        // "o"
        mapping.put("o", new HashMap<String,String>() {{ put("<", "<"); }});
        mapping.put("o", new HashMap<String,String>() {{ put(">", ">,oi,mi,di,si"); }});
        mapping.put("o", new HashMap<String,String>() {{ put("d", "o,d,s"); }});
        mapping.put("o", new HashMap<String,String>() {{ put("di", "<,o,m,di,fi"); }});
        mapping.put("o", new HashMap<String,String>() {{ put("o", "<,o,m"); }});
        mapping.put("o", new HashMap<String,String>() {{ put("oi", "=,d,di,o,oi,s,si,f,fi"); }});
        mapping.put("o", new HashMap<String,String>() {{ put("m", "<"); }});
        mapping.put("o", new HashMap<String,String>() {{ put("mi", "oi,di,si"); }});
        mapping.put("o", new HashMap<String,String>() {{ put("s", "o"); }});
        mapping.put("o", new HashMap<String,String>() {{ put("si", "di,fi,o"); }});
        mapping.put("o", new HashMap<String,String>() {{ put("f", "d,s,o"); }});
        mapping.put("o", new HashMap<String,String>() {{ put("fi", "<,o,m"); }});
        // "oi"
        mapping.put("oi", new HashMap<String,String>() {{ put("<", "<,o,m,di,fi"); }});
        mapping.put("oi", new HashMap<String,String>() {{ put(">", ">"); }});
        mapping.put("oi", new HashMap<String,String>() {{ put("d", "oi,d,f"); }});
        mapping.put("oi", new HashMap<String,String>() {{ put("di", "<,oi,mi,di,si"); }});
        mapping.put("oi", new HashMap<String,String>() {{ put("o", "=,d,di,o,oi,s,si,f,fi"); }});
        mapping.put("oi", new HashMap<String,String>() {{ put("oi", ">,oi,mi"); }});
        mapping.put("oi", new HashMap<String,String>() {{ put("m", "o,di,fi"); }});
        mapping.put("oi", new HashMap<String,String>() {{ put("mi", ">"); }});
        mapping.put("oi", new HashMap<String,String>() {{ put("s", "oi,d,f"); }});
        mapping.put("oi", new HashMap<String,String>() {{ put("si", ">,oi,mi"); }});
        mapping.put("oi", new HashMap<String,String>() {{ put("f", "oi"); }});
        mapping.put("oi", new HashMap<String,String>() {{ put("fi", "oi,di,si"); }});
        // "m"
        mapping.put("m", new HashMap<String,String>() {{ put("<", "<"); }});
        mapping.put("m", new HashMap<String,String>() {{ put(">", ">,oi,mi,di,si"); }});
        mapping.put("m", new HashMap<String,String>() {{ put("d", "o,d,s"); }});
        mapping.put("m", new HashMap<String,String>() {{ put("di", "<"); }});
        mapping.put("m", new HashMap<String,String>() {{ put("o", "<"); }});
        mapping.put("m", new HashMap<String,String>() {{ put("oi", "o,d,s"); }});
        mapping.put("m", new HashMap<String,String>() {{ put("m", "<"); }});
        mapping.put("m", new HashMap<String,String>() {{ put("mi", "=,f,fi"); }});
        mapping.put("m", new HashMap<String,String>() {{ put("s", "m"); }});
        mapping.put("m", new HashMap<String,String>() {{ put("si", "m"); }});
        mapping.put("m", new HashMap<String,String>() {{ put("f", "d,s,o"); }});
        mapping.put("m", new HashMap<String,String>() {{ put("fi", "<"); }});
        // "mi"
        mapping.put("mi", new HashMap<String,String>() {{ put("<", "<,o,m,di,fi"); }});
        mapping.put("mi", new HashMap<String,String>() {{ put(">", ">"); }});
        mapping.put("mi", new HashMap<String,String>() {{ put("d", "oi,d,f"); }});
        mapping.put("mi", new HashMap<String,String>() {{ put("di", ">"); }});
        mapping.put("mi", new HashMap<String,String>() {{ put("o", "oi,d,f"); }});
        mapping.put("mi", new HashMap<String,String>() {{ put("oi", ">"); }});
        mapping.put("mi", new HashMap<String,String>() {{ put("m", "=,s,si"); }});
        mapping.put("mi", new HashMap<String,String>() {{ put("mi", ">"); }});
        mapping.put("mi", new HashMap<String,String>() {{ put("s", "oi,d,f"); }});
        mapping.put("mi", new HashMap<String,String>() {{ put("si", ">"); }});
        mapping.put("mi", new HashMap<String,String>() {{ put("f", "mi"); }});
        mapping.put("mi", new HashMap<String,String>() {{ put("fi", "mi"); }});
        // "s"
        mapping.put("s", new HashMap<String,String>() {{ put("<", "<"); }});
        mapping.put("s", new HashMap<String,String>() {{ put(">", ">"); }});
        mapping.put("s", new HashMap<String,String>() {{ put("d", "d"); }});
        mapping.put("s", new HashMap<String,String>() {{ put("di", "<,o,m,di,fi"); }});
        mapping.put("s", new HashMap<String,String>() {{ put("o", "<,o,m"); }});
        mapping.put("s", new HashMap<String,String>() {{ put("oi", "oi,d,f"); }});
        mapping.put("s", new HashMap<String,String>() {{ put("m", "<"); }});
        mapping.put("s", new HashMap<String,String>() {{ put("mi", "mi"); }});
        mapping.put("s", new HashMap<String,String>() {{ put("s", "s"); }});
        mapping.put("s", new HashMap<String,String>() {{ put("si", "=,s,si"); }});
        mapping.put("s", new HashMap<String,String>() {{ put("f", "d"); }});
        mapping.put("s", new HashMap<String,String>() {{ put("fi", "<,o,m"); }});
        // "si"
        mapping.put("si", new HashMap<String,String>() {{ put("<", "<,o,m,di,fi"); }});
        mapping.put("si", new HashMap<String,String>() {{ put(">", ">"); }});
        mapping.put("si", new HashMap<String,String>() {{ put("d", "oi,d,f"); }});
        mapping.put("si", new HashMap<String,String>() {{ put("di", "di"); }});
        mapping.put("si", new HashMap<String,String>() {{ put("o", "o,di,fi"); }});
        mapping.put("si", new HashMap<String,String>() {{ put("oi", "oi"); }});
        mapping.put("si", new HashMap<String,String>() {{ put("m", "o,di,fi"); }});
        mapping.put("si", new HashMap<String,String>() {{ put("mi", "mi"); }});
        mapping.put("si", new HashMap<String,String>() {{ put("s", "=,s,si"); }});
        mapping.put("si", new HashMap<String,String>() {{ put("si", "si"); }});
        mapping.put("si", new HashMap<String,String>() {{ put("f", "oi"); }});
        mapping.put("si", new HashMap<String,String>() {{ put("fi", "di"); }});
        // "f"
        mapping.put("f", new HashMap<String,String>() {{ put("<", "<"); }});
        mapping.put("f", new HashMap<String,String>() {{ put(">", ">"); }});
        mapping.put("f", new HashMap<String,String>() {{ put("d", "d"); }});
        mapping.put("f", new HashMap<String,String>() {{ put("di", ">,oi,mi,di,si"); }});
        mapping.put("f", new HashMap<String,String>() {{ put("o", "d,o,s"); }});
        mapping.put("f", new HashMap<String,String>() {{ put("oi", ">,oi,mi"); }});
        mapping.put("f", new HashMap<String,String>() {{ put("m", "m"); }});
        mapping.put("f", new HashMap<String,String>() {{ put("mi", ">"); }});
        mapping.put("f", new HashMap<String,String>() {{ put("s", "d"); }});
        mapping.put("f", new HashMap<String,String>() {{ put("si", ">,oi,mi"); }});
        mapping.put("f", new HashMap<String,String>() {{ put("f", "f"); }});
        mapping.put("f", new HashMap<String,String>() {{ put("fi", "=,f,fi"); }});
        // "fi"
        mapping.put("fi", new HashMap<String,String>() {{ put("<", "<"); }});
        mapping.put("fi", new HashMap<String,String>() {{ put(">", ">,oi,mi,di,si"); }});
        mapping.put("fi", new HashMap<String,String>() {{ put("d", "o,d,s"); }});
        mapping.put("fi", new HashMap<String,String>() {{ put("di", "di"); }});
        mapping.put("fi", new HashMap<String,String>() {{ put("o", "o"); }});
        mapping.put("fi", new HashMap<String,String>() {{ put("oi", "oi,di,si"); }});
        mapping.put("fi", new HashMap<String,String>() {{ put("m", "m"); }});
        mapping.put("fi", new HashMap<String,String>() {{ put("mi", "oi,di,si"); }});
        mapping.put("fi", new HashMap<String,String>() {{ put("s", "o"); }});
        mapping.put("fi", new HashMap<String,String>() {{ put("si", "di"); }});
        mapping.put("fi", new HashMap<String,String>() {{ put("f", "=,f,fi"); }});
        mapping.put("fi", new HashMap<String,String>() {{ put("fi", "fi"); }});
        
    }
    
    public static String inverse(String relations) {
    	String[] splittedrelations = relations.split(",");
    	
    	String result = inv.get(splittedrelations[0]);
    	int i = 1;
    	while( i < splittedrelations.length ) {
    		result += "," + inv.get(splittedrelations[i]);
    		++i;
    	}
    	
    	return result;
    }
    
    public static String pfunction(String relation1, String relation2) {
    	List<String> splittedrelation1 = Arrays.asList(relation1.split(",")), splittedrelation2 = Arrays.asList(relation2.split(",")), resolvedrelations, resultrelations = null;
    	for( int i = 0; i < splittedrelation1.size(); ++i) {
    		for( int k = 0; k < splittedrelation2.size(); ++k) {
    			resolvedrelations = Arrays.asList(mapping.get(splittedrelation1.get(i)).get(splittedrelation2.get(k).split(",")));
    			for( int l = 0; l < resolvedrelations.size(); ++l) {
    				if( !resultrelations.contains(resolvedrelations.get(l)) ) {
    					resultrelations.add(resolvedrelations.get(l));
    				}
    			}
       		} 		
    	}
    	
    	return resultrelations.toString();
    }
    
	
	
	
}
