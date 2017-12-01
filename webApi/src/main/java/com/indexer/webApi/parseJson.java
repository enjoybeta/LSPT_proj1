package com.indexer.webApi;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class parseJson {
	// for text transform team - setToken
	public static TextTransInput readTextTransformJSON(String jsonStr) {
		TextTransInput textTransInput;
		try {
			textTransInput = new TextTransInput();
			JSONObject wholeJson = (JSONObject) new JSONParser().parse(jsonStr);
			JSONObject titleObj = (JSONObject) wholeJson.get("title");
			textTransInput.title = (String) titleObj.get("title");
			if (textTransInput.title != null) {
				JSONArray titleIndexList = (JSONArray) titleObj.get("indicies");
				textTransInput.titleIndex = Integer.valueOf((String) titleIndexList.get(0));
			}

			JSONObject metadataObj = (JSONObject) wholeJson.get("metadata");
			for (Object key : metadataObj.keySet()) {
				textTransInput.metadata.put((String) key, (String) metadataObj.get(key));
			}

			JSONObject ngramsObj = (JSONObject) wholeJson.get("ngrams");
			ensureNgramListSize(textTransInput.ngrams, ngramsObj.keySet().size());
			for (Object n : ngramsObj.keySet()) {
				JSONObject gramObj = (JSONObject) ngramsObj.get(n);
				for (Object word : gramObj.keySet()) {
					ArrayList<Integer> index = new ArrayList<Integer>();
					JSONArray indexes = (JSONArray) gramObj.get(word);
					for (Object i : indexes) {
						index.add(Integer.valueOf((String) i));
					}
					textTransInput.ngrams.get(Integer.valueOf((String) n) - 1).put((String) word, index);
				}
			}
		} catch (ParseException e) {
			System.err.println("ERROR");
			e.printStackTrace();// JSON Parsing error
			return null;
		} catch (NumberFormatException e) {
			System.err.println("ERROR");
			e.printStackTrace();// cast string to int
			return null;
		}
		return textTransInput;
	}

	// for ranking team - getToken(the string they pass in)
	public static RankingInput readRankingJSON(String jsonStr) {
		RankingInput rankingInput;
		try {
			rankingInput = new RankingInput();
			JSONArray wholeJson = (JSONArray) new JSONParser().parse(jsonStr);
			for (int i = 0; i < wholeJson.size(); ++i) {
				rankingInput.ngrams.add((String) wholeJson.get(i));
			}
		} catch (ParseException e) {
			System.err.println("ERROR");
			e.printStackTrace();// cast string to int
			return null;
		} catch (NumberFormatException e) {
			System.err.println("ERROR");
			e.printStackTrace();// cast string to int
			return null;
		}
		return rankingInput;
	}

	// for ranking team - getToken(the string we pass out)
	public String createRankingJSON(ArrayList<RankingOutput> rankingOutput) {
		JSONObject ret = new JSONObject();
		for (int i = 0; i < rankingOutput.size(); ++i) {//fore every given ngram
			JSONObject tmp = new JSONObject();
			for (String url : rankingOutput.get(i).ngramIndex.keySet()) {//for every url
				JSONArray indexesOfNgram_json = new JSONArray();
				ArrayList<Integer> indexesOfNgram = rankingOutput.get(i).ngramIndex.get(url);
				for (int k = 0; k < indexesOfNgram.size(); ++k) {//for every index of url
					indexesOfNgram_json.add(indexesOfNgram.get(k));
				}
				tmp.put(url,indexesOfNgram_json);
			}
			ret.put(rankingOutput.get(i).ngram, tmp);
		}
		String jsonText = ret.toJSONString();
		return jsonText;
	}

	// helper function for readTextTransformJSON()
	public static void ensureNgramListSize(ArrayList<HashMap<String, ArrayList<Integer>>> list, int size) {
		list.ensureCapacity(size);
		while (list.size() < size) {
			HashMap<String, ArrayList<Integer>> tmp = new HashMap<String, ArrayList<Integer>>();
			list.add(tmp);
		}
	}
}
