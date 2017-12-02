package com.indexer.webApi;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
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
    @RequestMapping(value="/getToken", method=RequestMethod.POST, consumes="text/plain")
    public String getToken(@RequestBody String input) {
    	RankingInput ret = parseJson.readRankingJSON(input);
        return "result to ranking";
        // test with:
        //curl -H "Accept: application/json" -H "Content-type: text/plain" -X POST -d 'some tokens' http://localhost:8080/getToken
    }
    
    //for text transform team
    @RequestMapping(value="/setToken", method=RequestMethod.POST, consumes="text/plain")
    public void setToken(@RequestBody String input) {
    	  System.out.println(input);
          // test with:
          //curl -H "Accept: application/json" -H "Content-type: text/plain" -X POST -d '{"name":"value"}' http://localhost:8080/setToken
    }

    public static void main(String[] args) {
    	database.init();
    	app = new SpringApplication(WebApiApplication.class);
        app.run(args);
    }

}