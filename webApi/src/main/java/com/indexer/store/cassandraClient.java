package com.indexer.store;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.indexer.webApi.TextTransInput;

public class cassandraClient {
	static String[] CONTACT_POINTS = { "127.0.0.1" };
	static int PORT = 9042;
	private Cluster cluster;
	private Session session;

	public void init() throws Exception {
		try {
			cluster = Cluster.builder().addContactPoints(CONTACT_POINTS).withPort(PORT).build();
			session = cluster.connect();
		}catch(Exception e) {
			throw new Exception("Connection to Cassandra failed, possibly Cassandra not started or still starting");
		}
		System.out.println("Connected to cluster:" + cluster.getMetadata().getClusterName());
	}

	public void end() {
		cluster.close();
	}

	public void addWebinfo(TextTransInput input) {
		session.execute(
				"INSERT INTO webinfo.data (url,time,json) VALUES ('rpi.edu','now','" + input.originalJson + "');");//TODO
	}

	public void getWebinfo(String url) {
		ResultSet results = session.execute("SELECT * FROM webinfo.data WHERE url = '" + url + "';");
		System.out.println(results.all());
	}
}
