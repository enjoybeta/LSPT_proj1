package com.indexer.webApi;

import java.util.ArrayList;
import java.util.HashMap;

public class TextTransInput {
	public String url;
	public String title; //WebPage title -> could be Null
	public int titleIndex; // Could be null only if no title
	public HashMap<String, String> metadata = new HashMap<String, String>(); //just the random attrs in the file, author and such
	public ArrayList<HashMap<String,ArrayList<Integer>>> ngrams = new ArrayList<HashMap<String,ArrayList<Integer>>>();
	//[ Map(Word, Indicies )    ] ngram size
    public String originalJson;
}
