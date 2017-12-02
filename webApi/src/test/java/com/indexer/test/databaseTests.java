package com.indexer.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.indexer.store.cassandraClient;
import com.indexer.webApi.TextTransInput;

public class databaseTests {
	private cassandraClient database = new cassandraClient();

	@Before
	public void init() {
		try {
			database.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void end() {
		database.end();
	}

	@Test
	public void myFirstTest() {
		TextTransInput tmp = new TextTransInput();
		tmp.title = "nothing";
		tmp.titleIndex = 0;
		HashMap<String, ArrayList<Integer>> tmp2 = new HashMap<String, ArrayList<Integer>>();
		tmp2.put("hello", new ArrayList<Integer>(Arrays.asList(5, 10)));
		tmp.ngrams.add(tmp2);
		tmp.originalJson = "[json]";
		database.addWebinfo(tmp);
		ArrayList<String> tmp3 = database.getWebinfo("rpi.edu");
		System.out.println("url:" + "rpi.edu");
		for (String value : tmp3) {
			System.out.println("json:" + value);
		}
	}
}
