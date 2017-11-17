package com.indexer.webApi;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import static java.lang.Math.toIntExact;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class parseJson {
	void readJSON(String fileName) {
		FileReader file = null;
		try {
			file = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			System.err.println("ERROR");
			e.printStackTrace();
			return;
		}
		try {
			webPageInfo webPageData = new webPageInfo();
	        JSONObject wholeJson = (JSONObject) new JSONParser().parse(file);
	        JSONObject titleObj = (JSONObject) wholeJson.get("title");
	        webPageData.title = (String) titleObj.get("title"); 
	        if(webPageData.title != null) {
	        	JSONArray titleIndexList = (JSONArray) titleObj.get("indicies");
	        	webPageData.titleIndex = toIntExact((long) titleIndexList.get(0));
	        }
	        
	        JSONObject metadataObj = (JSONObject) wholeJson.get("metadata");
	        for (Object key : metadataObj.keySet()) {
	        	webPageData.metadata.put((String)key, (String)metadataObj.get(key));
	        }

	        JSONObject ngramsObj = (JSONObject) wholeJson.get("ngrams");
	        ensureNgramListSize(webPageData.ngrams,ngramsObj.keySet().size());
	        for (Object n : ngramsObj.keySet()) {
	        	JSONObject gramObj = (JSONObject) ngramsObj.get(n);
	        	for (Object word : gramObj.keySet()) {
	        		ArrayList<Integer> index = new ArrayList<Integer>();
	        		JSONArray indexes  = (JSONArray) gramObj.get(word);
	        		for (Object i : indexes) {
	        			index.add(toIntExact((long)i));
	        		}
	        		webPageData.ngrams.get(Integer.valueOf((String) n)-1).put((String)word,index);
	        	}
	        }     
        } catch (ParseException e) {
        	System.err.println("ERROR");
            e.printStackTrace();// JSON Parsing error
            return;
        } catch (IOException e) {
        	System.err.println("ERROR");
            e.printStackTrace();// read error
            return;
		} catch (ArithmeticException e) {
        	System.err.println("ERROR");
            e.printStackTrace();// cast long to int
            return;
		} catch (NumberFormatException e) {
        	System.err.println("ERROR");
            e.printStackTrace();// cast string to int
            return;
		}
	}
	
	
	public static void ensureNgramListSize(ArrayList<HashMap<String,ArrayList<Integer>>> list, int size) {
	    list.ensureCapacity(size);
	    while (list.size() < size) {
	    	HashMap<String,ArrayList<Integer>> tmp = new HashMap<String,ArrayList<Integer>>();
	        list.add(tmp);
	    }
	}
}
