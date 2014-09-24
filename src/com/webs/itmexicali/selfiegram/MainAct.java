package com.webs.itmexicali.selfiegram;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainAct extends Activity {

	private ListView mListView;
	
	//Array adapter for the Result thread
    private CustomArrayAdapter mCustArrAdap;
	
    private static final String IMG_VIEW_KEY = "img_on_view";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		
		/* this works but it is Deprecated
		Object obj = getLastNonConfigurationInstance();

	    if(null != obj){//if there is saved adapter - restore it
	    	mCustArrAdap = (CustomArrayAdapter)obj;
	    } else { //if not - create new one
	    	mCustArrAdap = new CustomArrayAdapter(this, R.layout.image_view);
	    }
	    */
		mCustArrAdap = new CustomArrayAdapter(this, R.layout.image_view);
		
		mListView = (ListView) findViewById(R.id.in);
		mListView.setAdapter(mCustArrAdap);
		
		
		if(savedInstanceState == null)
			new StartFetching().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			mCustArrAdap.add("http://images.medicaldaily.com/sites/medicaldaily.com/files/styles/large/public/2014/04/11/woman-taking-selfie-herself.jpg");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/* This works but it is Deprecated
	@Override
	public Object onRetainNonConfigurationInstance() {
	    return mCustArrAdap;
	}
	*/

	
	/** Save data from ListView when rotating the device 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle) */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		Log.i(this.getClass().getSimpleName(),"onRestoreInstanceState restoring  values");
		// Initialize the array adapter for the conversation thread
        if (savedInstanceState != null) {
        	String[] values = savedInstanceState.getStringArray(IMG_VIEW_KEY);
            for (String result : values) {
            	mCustArrAdap.add(result);
            }
        }
        
	}
	
	
	/** Save data from ListView when rotating the device */
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        int items = mCustArrAdap.getCount();
        
        Log.i(this.getClass().getSimpleName(),"onSaveInstanceState saving "+items+" values");
        
        String[] values =  new String[items];
        for(int i =0 ; i < items;i++)
        	values[i] = mCustArrAdap.getItem(i);
        
        savedState.putStringArray(IMG_VIEW_KEY, values);
    }
    
    private class StartFetching extends AsyncTask<Void,Void,String>{
    	@Override
	    protected String doInBackground(Void... params) {
    		String inputJson = null;
	    	try {
	    		String client_id = getString(R.string.instagram_client_id);
				//inputJson = ServerConn.getResponse("http://yo-t.besaba.com/instagram_json.html");
	    		inputJson = ServerConn.getResponse("https://api.instagram.com/v1/tags/selfie/media/recent?client_id="+client_id);
				System.out.println("Response; chars: "+inputJson.length()+"\nText: "+inputJson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return inputJson;
	    }

		@Override
	    protected void onPostExecute(String result) {
	    	String[] urls = JSonParser.getURLs(result);
			mCustArrAdap.add(urls);
	    }
    }
	
}
