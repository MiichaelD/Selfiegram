package com.webs.itmexicali.selfiegram;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSonParser {

	private static final String
					DATA="data", IMAGES="images", URL = "url",
					LOW_R="low_resolution",		TMB_R="thumbnail",
					STD_R="standard_resolution"; 
	
	
	public static String[] getURLs(String JSon){
		JSONObject res;
		String[] dataOutput = null;
		try {
			res = new JSONObject(JSon);
			JSONArray data = res.getJSONArray(DATA);
			
			int data_length = data.length();
			dataOutput = new String[data_length];
			
			for(int i=0 ;i < data_length; i++){
				JSONObject jsonData = data.getJSONObject(i);
				JSONObject images = jsonData.getJSONObject(IMAGES);
				dataOutput[i] = images.getJSONObject(LOW_R).getString(URL);
				dataOutput[i] = dataOutput[i].replace("\\/", "/");
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return dataOutput;
	}
	
	
}
