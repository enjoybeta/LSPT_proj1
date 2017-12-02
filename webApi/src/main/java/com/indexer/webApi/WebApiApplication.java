package com.indexer.webApi;

import java.util.ArrayList;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import com.indexer.index.OverallIndex;
import com.indexer.store.cassandraClient;

@RestController
@EnableAutoConfiguration
public class WebApiApplication {
	private static SpringApplication app;
	private static cassandraClient database = new cassandraClient();
	
    @RequestMapping("/")
    String home() {
        return "/stopWords	/getToken	/setToken";
    }
    
    @RequestMapping("/stopWords")
    String stopWords() {
        return "stop word list";
        // test with:
        //http://localhost:8080/stopWords
    }
    
    //for ranking team
    // test with:
    //curl -H "Accept: application/json" -H "Content-type: text/plain" -X POST -d 'some tokens' http://localhost:8080/getToken
    @RequestMapping(value="/getToken", method=RequestMethod.POST, consumes="text/plain")
    public String getToken(@RequestBody String input) {
    	RankingInput rankingInput = parseJson.readRankingJSON(input);
    	ArrayList<RankingOutput> tmp = OverallIndex.getNgramData(rankingInput);
    	String ret = parseJson.createRankingJSON(tmp);
        return "result to ranking";//TODO return ret
    }
    
    //for text transform team
    // test with:
    //curl -H "Accept: application/json" -H "Content-type: text/plain" -X POST -d '{"name":"value"}' http://localhost:8080/setToken
    @RequestMapping(value="/setToken", method=RequestMethod.POST, consumes="text/plain")
    public void setToken(@RequestBody String input) {
    	  TextTransInput textTransInput = parseJson.readTextTransformJSON(input);
    	  database.addWebinfo(textTransInput);
    	  //TODO add into index
    }

    public static void main(String[] args) {
    	try {
			database.init();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
    	app = new SpringApplication(WebApiApplication.class);
        app.run(args);
    }

}