package com.webs.itmexicali.selfiegram;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


public class JSonParser {

	private static final String
					PAGINATION="pagination", NEXT_MAX_TAG = "next_max_tag_id",
					MIN_TAG = "min_tag_id",
					DATA="data", IMAGES="images", URL = "url",
					LOW_R="low_resolution",		TMB_R="thumbnail",
					STD_R="standard_resolution"; 
	
	
	public static String[] getURLs(Context context, String JSon){
		JSONObject res;
		String[] dataOutput = null;
		try {
			res = new JSONObject(JSon);
			JSONObject pagination = res.getJSONObject(PAGINATION);
			String max_tag_id = pagination.getString(NEXT_MAX_TAG);
			String min_tag_id = null;
			try{//Sometimes this string is not contained in the json
				min_tag_id = pagination.getString(MIN_TAG);
			}catch(Exception e){}
			
			MainAct.updateNextUrl("&max_tag_id="+max_tag_id+
					(min_tag_id==null?"":"&min_tag_id="+min_tag_id) );
			
			
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
