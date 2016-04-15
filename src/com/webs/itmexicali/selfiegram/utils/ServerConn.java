package com.webs.itmexicali.selfiegram.utils;

import com.webs.itmexicali.selfiegram.MainAct;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ServerConn extends server.ServerCom{

	private static ServerConn instance;
	
	private ServerConn(){}
	
	public static ServerConn shared(){
		if(instance == null){
			instance = new ServerConn();
		}
		return instance;
	}

	@Override
	public boolean isNetworkAvailable() {
		boolean connected = false;
		ConnectivityManager cm = (ConnectivityManager) MainAct.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm != null){
			//this method iterates thru all the networks available to check which one is connected
			NetworkInfo[] netInfo = cm.getAllNetworkInfo();
			for (NetworkInfo ni : netInfo) 
				if ( ni.isConnected() )//&& ni.getTypeName().equalsIgnoreCase("WIFI") )
					connected = true;
		
		//	this method gets the active network and checks if it is connected
//			NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
//			connected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
		}
		return connected;
	}
}