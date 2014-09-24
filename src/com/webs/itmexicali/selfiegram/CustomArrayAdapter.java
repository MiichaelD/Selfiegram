package com.webs.itmexicali.selfiegram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/** Custom ArrayAdapter to display views more complicated than just
 * a TextView; In this case, 1 ImageView.
 * 
 * More info:
 * https://github.com/thecodepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView */
public class CustomArrayAdapter extends ArrayAdapter<String>{

	int res_id;
	Context ctx;
	String[] data;
	
	public CustomArrayAdapter(Context context, int resource) {
		super(context, resource);
		ctx = context;
		res_id = resource;
	}
	
	public CustomArrayAdapter(Context context, int resource, String[] arr) {
		super(context, resource, arr);
		ctx = context;
		res_id = resource;
		data = arr;
	}
	
	public void add(String[] args){
		if(args == null) return;
		for(String val:args){
			if(val != null && !val.isEmpty())
				add(val);
		}
	}
	
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
		final String result = getItem(position);   
		
		final boolean big = position % 3 == 0;
		
		final ViewHolder viewHolder;
		
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(res_id, parent, false);
          
          viewHolder = new ViewHolder();
          viewHolder.img = (ImageView) convertView;
          viewHolder.img.setImageBitmap(BitmapLoader.getImage(ctx,
        		  big?R.drawable.loading_big:R.drawable.loading_small,
        		  true));
          convertView.setTag(viewHolder);
       } else {
    	   //if it was already reused 
           viewHolder = (ViewHolder) convertView.getTag();
       }
       // Populate the data into the template view using the data object       
       ImageDownloaderTask idt = new ImageDownloaderTask();
       idt.setContext(ctx);
       idt.setViewHolder(viewHolder);
       idt.execute(result,big?"BIG":null);
       
       // Return the completed view to render on screen
       return convertView;
   }
	
	/** This class is used to improve performance, we should modify the custom adapter
	 * by applying the ViewHolder pattern which speeds up the population of the
	 * ListView considerably by caching view lookups for smoother, faster loading:*/
	static class ViewHolder{
		ImageView img;
	}

}
