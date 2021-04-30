package org.eu.fangkehou.WoodPecker;
import android.app.*;
import android.content.*;
import android.os.*;

public class AnrHandlerService extends Service
{

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
		
		CrashHandler.getinstance().getProcesser();
		return super.onStartCommand(intent, flags, startId);
	}

}
