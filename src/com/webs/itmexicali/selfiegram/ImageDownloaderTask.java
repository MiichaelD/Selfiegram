package com.webs.itmexicali.selfiegram;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;



public class ImageDownloaderTask extends AsyncTask<String, Integer, Bitmap> {

	private static final int BIG_SIZE = 306, SMALL_SIZE = 150;
	
	Context ctx = null;
	boolean pBig;
	CustomArrayAdapter.ViewHolder viewHolder = null;
	
	public void setContext(Context context){
		ctx= context;
	}
	
	public void setViewHolder(CustomArrayAdapter.ViewHolder vh){
		viewHolder=vh;
	}
	
    protected Bitmap doInBackground(String... urls) {

    	Bitmap bp;
    	if(urls[1] != null)
  	   		bp = BitmapLoader.resizeImage(ctx, urls[0], true, BIG_SIZE, BIG_SIZE);
    	else
    		bp = BitmapLoader.resizeImage(ctx, urls[0], true, SMALL_SIZE, SMALL_SIZE);
        return bp;
    }

    protected void onPostExecute(Bitmap result) {
    	viewHolder.img.setImageBitmap(result);
    }
}