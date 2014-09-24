package com.webs.itmexicali.selfiegram;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class DetailAct extends Activity{
	
	String Url = null;
	
	ImageView imgv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.image_view);
		
		Url = getIntent().getExtras().getString(MainAct.IMG_VIEW_KEY);
		if(Url != null){
			imgv = (ImageView)findViewById(R.id.iv_1);
			imgv.setImageBitmap(BitmapLoader.fetchImage(this, Url, true));
		}
		
	}

}
