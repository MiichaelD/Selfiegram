package com.webs.itmexicali.selfiegram;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
	
	private Bitmap pBM;
	private String pURL;
		
	public Item(){
	}
	
	public Item(String oper, double x, double y, double res){	
	}
	
	private Item(Parcel in) {
		/*
		byte[] arr = in.readByteArray();
		x=in.readDouble();
		y=in.readDouble();
		res=in.readDouble();*/
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		/*dest.writeString(operator);
		dest.writeDouble(x);
		dest.writeDouble(y);
		dest.writeDouble(res);*/
	}
	
	
	public static final Parcelable.Creator<Item> CREATOR   = new Parcelable.Creator<Item>() {
		public Item createFromParcel(Parcel in) {
			return new Item(in);
		}
	
		public Item[] newArray(int size) {
		    return new Item[size];
		}
	};
	
	public void setBitmap(){
		
	}
	
	public String getURL(){
		return pURL;
	}
	
	public void loadBitmap(){
		
	}
	
}
