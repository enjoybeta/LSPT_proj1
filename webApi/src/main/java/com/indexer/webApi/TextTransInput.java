package com.indexer.webApi;

import java.util.ArrayList;
import java.util.HashMap;

public class TextTransInput {
	public String title;
	public int titleIndex;
	public HashMap<String, String> metadata = new HashMap<String, String>();
	public ArrayList<HashMap<String,ArrayList<Integer>>> ngrams = new ArrayList<HashMap<String,ArrayList<Integer>>>();
	public String originalJson;
}
