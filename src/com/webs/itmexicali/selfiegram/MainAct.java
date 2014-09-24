package com.webs.itmexicali.selfiegram;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainAct extends Activity {

	private ListView mListView;
	
	//Array adapter for the Result thread
    private CustomArrayAdapter mCustArrAdap;
	
    public static final String IMG_VIEW_KEY = "img_on_view";
    
    
    public static final String
    	INSTAGRAM_CLIENT_ID="959d4a88f41a4eeaa52dc67238d3de4b",
		Default_Input_Url ="https://api.instagram.com/v1/tags/selfie/media/recent?client_id="+INSTAGRAM_CLIENT_ID;
    
    public static String
    		Next_Input_Url = null;
    
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
		mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(MainAct.this, DetailAct.class)
                .putExtra(IMG_VIEW_KEY, mCustArrAdap.getItem(position));
				startActivity(intent);
			}		
		});
		
		mListView.setOnScrollListener(new EndlessScrollListener(){
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Log.d(MainAct.class.getSimpleName(),"Loading new data!!!");
				//new StartFetching().execute();			
			}
			
		});
		
		
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
		if (id == R.id.action_load) {
			//mCustArrAdap.add("http://images.medicaldaily.com/sites/medicaldaily.com/files/styles/large/public/2014/04/11/woman-taking-selfie-herself.jpg");
			Log.d(MainAct.class.getSimpleName(),"Loading new data!!!");
			new StartFetching().execute();
			
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
				//inputJson = ServerConn.getResponse("http://yo-t.besaba.com/instagram_json.html");
	    		inputJson = ServerConn.getResponse(
	    				Default_Input_Url + (Next_Input_Url==null? "" : Next_Input_Url) );
				System.out.println("Response; chars: "+inputJson.length()+"\nText: "+inputJson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return inputJson;
	    }

		@Override
	    protected void onPostExecute(String result) {
	    	String[] urls = JSonParser.getURLs(MainAct.this,result);
			mCustArrAdap.add(urls);
	    }
    }

    
    public static void updateNextUrl(String query){
    	Next_Input_Url = query;
    	Log.i(MainAct.class.getSimpleName(),"new Url Limiters:"+Next_Input_Url);
    }
	
}
