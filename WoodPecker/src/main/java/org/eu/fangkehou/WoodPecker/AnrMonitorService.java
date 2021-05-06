package org.eu.fangkehou.WoodPecker;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.util.*;
import java.lang.reflect.*;
import android.widget.*;

public class AnrMonitorService extends Service
{
	
	private static int pid;
	
	private static class ClassRecordHandler extends Handler
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
		}
		
	}
	 
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO: Implement this method
		pid = intent.getIntExtra("pid",0);
		Log.i("fangkehouWoodPecker","Starting ANR Monitor for " + pid);
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
	}

	
	
	
	
}
