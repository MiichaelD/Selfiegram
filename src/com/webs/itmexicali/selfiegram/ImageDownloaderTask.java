package com.webs.itmexicali.selfiegram;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.AbsListView.LayoutParams;


/** 
 * Download the image and if we have a given ViewHolder, set its Imageview to 
 * contain the image, if not, just keep it for faster acces.
 * 
 * Instead of resizing the bitmap, setting new layout parameters, I prefered
 * Scaling the imageview, I think it looks better and it is not as memory consumming
 * as resizing the bitmap (creating a new one). */
public class ImageDownloaderTask extends AsyncTask<String, Integer, Bitmap> {

	private static final int BIG_SIZE = 350, SMALL_SIZE = 200;
	
	static final LayoutParams
			paramsBig = new LayoutParams(BIG_SIZE, BIG_SIZE),
			paramsSmall = new LayoutParams(SMALL_SIZE, SMALL_SIZE);
	
	
	Context ctx = null;
	int position;
	CustomArrayAdapter.ViewHolder viewHolder = null;
	boolean big = false;
	
	
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
    	big = urls[1] != null;
    	bp = BitmapLoader.fetchImage(ctx, urls[0], true);
    	/*
  	   	if(big)
    		bp = BitmapLoader.resizeImage(ctx, urls[0], true, BIG_SIZE, BIG_SIZE);
    	else
    		bp = BitmapLoader.resizeImage(ctx, urls[0], true, SMALL_SIZE, SMALL_SIZE);
    	*/
        return bp;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void onPostExecute(Bitmap result) {
    	if(viewHolder != null){
	    	if(position == viewHolder.position){
	    		viewHolder.img.setImageBitmap(result);
    			
    			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
		    		viewHolder.img.setScaleX(big?1:0.5f);
		    		viewHolder.img.setScaleY(big?1:0.5f);
    			}
	    		//viewHolder.img.setLayoutParams(big?paramsBig:paramsSmall);
	    		//viewHolder.img.setLayoutParams(paramsBig);
	    	}
    	}
    }
}