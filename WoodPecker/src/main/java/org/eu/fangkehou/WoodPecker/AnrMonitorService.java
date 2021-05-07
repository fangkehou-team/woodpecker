package org.eu.fangkehou.WoodPecker;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.util.*;
import java.lang.reflect.*;
import android.widget.*;
import java.util.*;

public class AnrMonitorService extends Service
{
	
	private static int pid;
	
	private class ClassRecordHandler extends Handler
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			handleStackTrace(msg.what , (StackTraceElement[]) msg.obj);
			super.handleMessage(msg);
		}
		
	}
	 
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return new Messenger(new ClassRecordHandler()).getBinder();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO: Implement this method
		pid = intent.getIntExtra("pid",0);
		Log.i("fangkehouWoodPecker","Starting ANR Monitor for " + pid + "(UI Thread Only)");
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
	}
	
	private void handleStackTrace(int pid, StackTraceElement[] stelements){
		Log.d("fangkehouWoodPecker","got StackTrace");
	}

	
}
