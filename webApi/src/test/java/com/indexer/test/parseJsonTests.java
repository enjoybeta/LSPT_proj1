package com.indexer.test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import com.indexer.index.TokenData;

public class parseJsonTests {
	@Test
    public void myFirstTest() {
		parseJson test = new parseJson();
		ArrayList<RankingOutput> tmpIn = new ArrayList<RankingOutput>();
		RankingOutput example1 = new RankingOutput();
		RankingOutput example2 = new RankingOutput();
		example1.ngram = "hello";
		TokenData tData = new TokenData( new ArrayList<Integer>(Arrays.asList(5, 10)));
		example1.ngramIndex.put("rpi.edu", tData);
		example2.ngram = "world";
		TokenData tData2 = new TokenData(new ArrayList<Integer>(Arrays.asList(6, 11)));
		example2.ngramIndex.put("rpi.edu", tData2);
		
		tmpIn.add(example1);
		tmpIn.add(example2);
		
		String tmpOut = test.createRankingJSON(tmpIn);
		System.out.println(tmpOut);
        //assertEquals(2, 2);
    }
}
