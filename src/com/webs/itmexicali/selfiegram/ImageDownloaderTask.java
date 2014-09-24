package com.webs.itmexicali.selfiegram;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;



public class ImageDownloaderTask extends AsyncTask<String, Integer, Bitmap> {

	private static final int BIG_SIZE = 306, SMALL_SIZE = 150;
	
	Context ctx = null;
	int position;
	CustomArrayAdapter.ViewHolder viewHolder = null;
	
	ImageDownloaderTask(Context context, CustomArrayAdapter.ViewHolder vh, int position){
		ctx = context;
		viewHolder = vh;
		this.position = position;
	}
	
	ImageDownloaderTask(Context context){
		ctx = context;
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
    	if(viewHolder != null){
	    	if(position == viewHolder.position)
	    		viewHolder.img.setImageBitmap(result);
    	}
    }
}