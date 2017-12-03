package com.indexer.index;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TimeZone;

public class TokenIndex {
	//Token -> Map of ( Urls -> TokenData)
	Map<String, HashMap<String, TokenData>> index = new HashMap<String, HashMap<String, TokenData>>();
	Calendar lastStopWordUpdate;
	Queue<StopWord> stopWordList = new PriorityQueue<StopWord>(51); //head of pq is the 'least element'
																	//in the case of stopwords this is the least common (fewest occurences)
	
	public void removeDoc(String url) {
		Iterator it = index.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Map<String, TokenData>> pair = (Map.Entry<String, Map<String, TokenData>>)(it.next());
			Map<String, TokenData> tDatas = pair.getValue();
			if(tDatas.containsKey(url)) {
				tDatas.remove(url);
			}
			if(tDatas.size() <= 0) {
				index.remove(pair.getKey());
			}
		}
	}
	public void indexDocument( ArrayList<HashMap<String,ArrayList<Integer>>> ngrams, String url) {
		for(int i = 0; i < ngrams.size(); i++) {
			HashMap<String,ArrayList<Integer>> allXSizeNGrams = ngrams.get(i);
			Iterator it = allXSizeNGrams.entrySet().iterator();
			while(it.hasNext()) {
				HashMap.Entry<String, ArrayList<Integer>> ngramElem = (HashMap.Entry<String,ArrayList<Integer>>)it.next();
				String token = ngramElem.getKey();
				ArrayList<Integer> indicies = ngramElem.getValue();
				addToken(token, indicies, url);
			}
			
		}
	}
	private void addToken(String token, ArrayList<Integer> indxs, String url) {
		TokenData tData = new TokenData(indxs);
		
		if(index.containsKey(token)) {
			//the token is in the index already
			index.get(token).put(url, tData);
		}else {
			//new token
			HashMap<String, TokenData> urlData= new HashMap<String, TokenData>();
			urlData.put(url, tData); //overwrites old data for an update as well.
			index.put(token, urlData);
		}
		
	}
	
	private void deleteToken(String w) {
		index.remove(w);
	}
	
	public ArrayList<String> getTop50StopWords(){
		if(lastStopWordUpdate == null) {
			Locale locale1 = Locale.US;
			TimeZone tz1 = TimeZone.getTimeZone("EST");
			lastStopWordUpdate = Calendar.getInstance(tz1, locale1);
			accumulateStopWords();
		}
		if(check20MinsHavePassed() || stopWordList.size() < 50) {
			accumulateStopWords();
		}
		ArrayList<String> returnStopWords = convertStopWordsToArrayList();
		return returnStopWords;
	}

	private boolean check20MinsHavePassed() {
		Calendar now = Calendar.getInstance();
		long diff = now.getTimeInMillis() - lastStopWordUpdate.getTimeInMillis();
		if(diff >= 20*60*1000) { //If at least 20 minutes have passed update stopwords
			return true;
		}
		return false;
	}

	private ArrayList<String> convertStopWordsToArrayList() {
		StopWord w;
		ArrayList<String> returnStopWords = new ArrayList<String>();
		Iterator<StopWord> it = stopWordList.iterator();
		while(it.hasNext()) {
			w = it.next();
			returnStopWords.add(w.word);
		}
		return returnStopWords;
	}

	private void accumulateStopWords() {
		Iterator it = index.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Map<String, TokenData>> pair = (Map.Entry<String, Map<String, TokenData>>)(it.next());
			String token = pair.getKey();
			Map<String, TokenData> tDatas = pair.getValue();
			Iterator it2 = tDatas.entrySet().iterator();
			while(it2.hasNext()) {
				Map.Entry<String, TokenData> pairOfUrlAndTData = (Map.Entry<String, TokenData>)it2.next();
				String url = pairOfUrlAndTData.getKey();
				TokenData tData = pairOfUrlAndTData.getValue();
				addStopWord(token, tData);
			}
			
		}
		
	}

	private void addStopWord(String token, TokenData tData) {
		StopWord w = new StopWord(token, tData.ngramSize, tData.indicies.size());
		if(!stopWordList.contains(w) && w.occurences > stopWordList.peek().occurences) { 	
			stopWordList.poll();
			stopWordList.add(w);
		} else {
			// this is technically o(1) b/c stopWordList is always len 50 so always log(50)
			//this is updating a stopwords priority 
			stopWordList.remove(w);
			stopWordList.add(w);
		}
	}
	public HashMap<String, TokenData> getTokenData(String token) {
		// TODO Auto-generated method stub
		index.get(token);
		return null;
	}
	
}