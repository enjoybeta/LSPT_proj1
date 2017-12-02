package com.indexer.index;
import java.util.ArrayList;
import java.util.HashMap;

import com.indexer.webApi.RankingInput;
import com.indexer.webApi.RankingOutput;
import com.indexer.webApi.TextTransInput;

public class OverallIndex {
	private DocumentIndex docIndex;
	private TokenIndex tIndex;
	
	public ArrayList<RankingOutput> getNgramData(RankingInput rIn){
		ArrayList<RankingOutput> rOut = new ArrayList<RankingOutput>();
		for(int i = 0; i < rIn.ngrams.size(); i++) {
			String token = rIn.ngrams.get(i); 
			HashMap<String,TokenData> ngramIndex = tIndex.getTokenData(token);
			RankingOutput nextROUTElem = new RankingOutput();
			nextROUTElem.ngramIndex = ngramIndex;
			nextROUTElem.ngram = token;
			rOut.add(nextROUTElem);
			//ngramIndex = Map(url -> indicies).
		}
		
		return rOut;
	}
	
	public void deleteDocument(String url) {
		docIndex.removeDoc(url);
		tIndex.removeDoc(url);
	}
	//ArrayList<RankingOutput>
	public void addDocument(TextTransInput txtInput) {
		//Takes in a given documents "data" (url, tokens to be indexed and doc metadata) then 
		//stores properly in the current index.
		
		long totalTokens = findTotalTokenNumber(txtInput);
		docIndex.addDoc(txtInput.title, txtInput.titleIndex, txtInput.metadata, txtInput.url, totalTokens);
		tIndex.indexDocument(txtInput.ngrams, txtInput.url);
	}

	public ArrayList<String> getTop50StopWords(){
		return tIndex.getTop50StopWords();
	}
	
	private long findTotalTokenNumber(TextTransInput txtInput) {
		long totalTokens = 0;
		for(int i =0; i < txtInput.ngrams.size(); i++) {
			HashMap<String,ArrayList<Integer>> n_gramMap = (HashMap<String,ArrayList<Integer>>)(txtInput.ngrams).get(i);
			totalTokens += n_gramMap.entrySet().size(); //add the num of tokens in each hashmap to get total num of tokens
		}
		return totalTokens;
	}
	
}