package com.indexer.index;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class TokenData {

	//doesn't need the url of doc b/c the key that points here is exactly that.
	int ngramSize = -1;
	// otherDocumentInfo contains strings like 'bolded' or 'italizied' if those are ever sent to us.
	ArrayList<String> otherDocumentInfo = null;
	public ArrayList<Integer> indicies = new ArrayList<Integer>(); 
	
	public TokenData(ArrayList<Integer> indxs) {
		this.indicies = (ArrayList<Integer>)indxs.clone();
	}
	
}