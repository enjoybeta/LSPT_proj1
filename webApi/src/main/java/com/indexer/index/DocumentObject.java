package com.indexer.index;

import java.lang.String;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DocumentObject {
	//these fields are primarily for internal use, or that will be needed
	//if we wish to extend this to use external products
	String url; //This is the unique identifier -> needed?
	Boolean deleteDoc = false;
	Boolean Updating = false;
	int currentVersionNumber = 1;
	
	//These are fields that will likely be used for ranking
	String title;
	int titleIndex;
	int wordCount; //total wordcount of the document
	Map<String, String> metaDataFields = new HashMap<String, String>(); //Field to Value
	long uniqueTokenNumber;
	Calendar lastUpdate;
	
	public DocumentObject(String titleIn, int titleIndex, HashMap<String, String> metaData, String url, long tokenNum) {
		// TODO Auto-generated constructor stub
		if(titleIn != null) {
			this.title = new String(titleIn);
			this.titleIndex = titleIndex;
		} else {
			this.title = null;
			this.titleIndex = -1;
		}
		if(metaData != null) {
			this.metaDataFields = (HashMap<String, String>)metaData.clone();
		} else {
			this.metaDataFields = null;
		}
		if(url != null) {
			this.url = url;
		} else {
			this.url = null;
		}
		
		this.uniqueTokenNumber = tokenNum;
		Locale locale1 = Locale.US;
		TimeZone tz1 = TimeZone.getTimeZone("EST");
		this.lastUpdate = Calendar.getInstance(tz1, locale1);
	}
}