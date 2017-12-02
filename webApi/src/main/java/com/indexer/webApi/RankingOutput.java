package com.indexer.webApi;

import java.util.HashMap;
import com.indexer.index.TokenData;

public class RankingOutput {
	public String ngram;
	// string = url, arrayList is the indicies
	public HashMap<String, TokenData> ngramIndex = new HashMap<String, TokenData>();
}
