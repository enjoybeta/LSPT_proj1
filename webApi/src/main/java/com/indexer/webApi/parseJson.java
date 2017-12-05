package com.indexer.webApi;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class parseJson {
	// for text transform team - setToken
	public static TextTransInput readTextTransformJSON(String jsonStr) throws Exception {
		TextTransInput textTransInput;
		try {
			textTransInput = new TextTransInput();
			JSONObject wholeJson = (JSONObject) new JSONParser().parse(jsonStr);
			textTransInput.url = (String) wholeJson.get("url");
			if(textTransInput.url == null) {
				System.err.println("no url");
				throw new IllegalArgumentException("no url");
			}
			JSONObject titleObj = (JSONObject) wholeJson.get("title");
			if(titleObj == null) {
				System.err.println("no title");
				throw new IllegalArgumentException("no title");
			}
			textTransInput.title = (String) titleObj.get("title");
			if (textTransInput.title != null) {
				JSONArray titleIndexList = (JSONArray) titleObj.get("indicies");
				if(titleIndexList == null) {
					System.err.println("no title indicies");
					throw new IllegalArgumentException("no title indicies");
				}
				textTransInput.titleIndex = ((Long) titleIndexList.get(0)).intValue();
			}
			JSONObject metadataObj = (JSONObject) wholeJson.get("metadata");
			if(metadataObj == null) {
				System.err.println("no metadata");
				throw new IllegalArgumentException("no metadata");
			}
			for (Object key : metadataObj.keySet()) {
				textTransInput.metadata.put((String) key, (String) metadataObj.get(key));
			}

			JSONObject ngramsObj = (JSONObject) wholeJson.get("ngrams");
			if(ngramsObj == null) {
				System.err.println("no ngrams");
				throw new IllegalArgumentException("no ngrams");
			}
			ensureNgramListSize(textTransInput.ngrams, ngramsObj.keySet().size());
			for (Object n : ngramsObj.keySet()) {
				JSONObject gramObj = (JSONObject) ngramsObj.get(n);
				for (Object word : gramObj.keySet()) {
					ArrayList<Integer> index = new ArrayList<Integer>();
					JSONArray indexes = (JSONArray) gramObj.get(word);
					for (Object i : indexes) {
						index.add(((Long) i).intValue());
					}
					textTransInput.ngrams.get(Integer.valueOf((String) n) - 1).put((String) word, index);
				}
			}
		} catch (IllegalArgumentException e) {
			System.err.println(jsonStr);
			throw new IllegalArgumentException("parsing failed-ParseException");
		}  catch (ParseException e) {
			System.err.println(jsonStr);
			throw new IllegalArgumentException("parsing failed-ParseException");
		} catch (Exception e) {
			System.err.println(jsonStr);
			e.printStackTrace();
			throw new IllegalArgumentException("parsing failed-Exception");
		}
		textTransInput.originalJson = jsonStr;
		return textTransInput;
	}

	// for ranking team - getToken(the string they pass in)
	public static RankingInput readRankingJSON(String jsonStr) throws Exception {
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
			throw new Exception("parsing failed");
		} catch (NumberFormatException e) {
			System.err.println("ERROR");
			e.printStackTrace();// cast string to int
			throw new Exception("parsing failed");
		}
		return rankingInput;
	}

	// for ranking team - getToken(the string we pass out)
	public static String createRankingJSON(ArrayList<RankingOutput> rankingOutput) {
		JSONObject ret = new JSONObject();
		for (int i = 0; i < rankingOutput.size(); ++i) {// for every given ngram
			JSONObject tmp = new JSONObject();
			if (rankingOutput.get(i).ngramIndex == null) {
				continue;
			}
			for (String url : rankingOutput.get(i).ngramIndex.keySet()) {// for every url
				JSONArray indexesOfNgram_json = new JSONArray();
				ArrayList<Integer> indexesOfNgram = rankingOutput.get(i).ngramIndex.get(url).indicies;
				for (int k = 0; k < indexesOfNgram.size(); ++k) {// for every index of url
					indexesOfNgram_json.add(indexesOfNgram.get(k));
				}
				tmp.put(url, indexesOfNgram_json);
			}
			ret.put(rankingOutput.get(i).ngram, tmp);
		}
		String jsonText = ret.toJSONString();
		return jsonText;
	}

	public static String createTop50JSON(ArrayList<String> stopWords) {
		JSONArray ret = new JSONArray();
		for (int i = 0; i < stopWords.size(); ++i) {// for each stopword
			ret.add(stopWords.get(i));
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
