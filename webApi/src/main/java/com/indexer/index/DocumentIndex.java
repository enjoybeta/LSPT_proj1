package com.indexer.index;
import java.util.HashMap;
import java.util.Map;

public class DocumentIndex {
	Map<String, DocumentObject> docIndex = new HashMap<String, DocumentObject>();
	
	public void addDoc(String title, int titleIndex, HashMap<String, String> metaData, String url, long totalTokens) {
		//should be able to update or add a new doc.
		DocumentObject newDoc = new DocumentObject(title, titleIndex, metaData, url, totalTokens);
		docIndex.put(url, newDoc);
	}
	
	public void removeDoc(String s) {
		//Note: this removes it from here, but there may be some instances of it in the 
		//the token index make sure this gets destroyed!
		docIndex.remove(s);
	}
	
	public DocumentObject getDocObj(String url) {
		if(docIndex.containsKey(url)) {
			return docIndex.get(url);
		}else {
			return null;
		}
	}
	
	
}