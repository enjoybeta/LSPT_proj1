package com.indexer.webApi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class parseJsonTests {
	@Test
    public void myFirstTest() {
		parseJson test = new parseJson();
		ArrayList<RankingOutput> tmpIn = new ArrayList<RankingOutput>();
		RankingOutput example1 = new RankingOutput();
		RankingOutput example2 = new RankingOutput();
		example1.ngram = "hello";
		example1.ngramIndex.put("rpi.edu", new ArrayList<Integer>(Arrays.asList(5, 10)));
		example2.ngram = "world";
		example2.ngramIndex.put("rpi.edu", new ArrayList<Integer>(Arrays.asList(6, 11)));
		
		tmpIn.add(example1);
		tmpIn.add(example2);
		
		String tmpOut = test.createRankingJSON(tmpIn);
		System.out.println(tmpOut);
        //assertEquals(2, 2);
    }
}
