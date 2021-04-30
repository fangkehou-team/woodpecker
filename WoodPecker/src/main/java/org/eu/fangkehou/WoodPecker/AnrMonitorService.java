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
	private static AnrReceiver manrreceiver;
	
	public class AnrReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context p1, Intent p2)
		{
			// TODO: Implement this method
			Log.i("a cute woodpecker","Wow! It seems that you have put too much work to this tree!");
			
			Intent i = new Intent(p1,AnrHandlerService.class);
			i.putExtra("pid",pid);
			p1.startService(i);
			
			stopSelf();
		}

	}
	
	private static class ClassRecordHandler extends Handler{
		
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
		
		manrreceiver = new AnrReceiver();

		IntentFilter inf = new IntentFilter();
		inf.addAction("android.intent.action.ANR");
		this.registerReceiver(manrreceiver,inf);
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy()
	{
		// TODO: Implement this method
		this.unregisterReceiver(manrreceiver);
		super.onDestroy();
	}

	
	
	
	
}
