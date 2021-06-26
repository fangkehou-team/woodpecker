package org.eu.fangkehou.WoodPecker;
import android.os.Process;
import android.util.*;
import android.os.*;

public class ANRHandlerThread implements Runnable
{
	private Boolean isContinue = true;
	private Messenger mService = null;
	private final int pid;


	public ANRHandlerThread(Messenger mService)
	{
		this.mService = mService;
		this.pid = android.os.Process.myPid();
	}


	public ANRHandlerThread()
	{
		this.pid = android.os.Process.myPid();
	}

	public void setMessager(Messenger mService)
	{
		this.mService = mService;
	}


	public void stopWorking()
	{
		this.isContinue = false;
	}

	@Override
	public void run()
	{
		// TODO: Implement this method
		if (mService == null)
		{
			Log.e("fangkehouWoodPecker", "Can not start ANR monitor: Service not found");
			return;
		}

		while (this.isContinue)
		{
			StringBuilder threadtagbuilder = new StringBuilder();
			StackTraceElement[] st = Looper.getMainLooper().getThread().getStackTrace();
			for (StackTraceElement stackTraceElement : st) {
				threadtagbuilder.append(stackTraceElement).append("\n");
			}
			String threadtag =  threadtagbuilder.toString();
			Message message = Message.obtain(null, pid);
			Bundle bundle = new Bundle();
			bundle.putString("tag",threadtag);
			message.setData(bundle);
			try
			{
				mService.send(message);
				Thread.sleep(CrashGlobal.getSleepTime());
			}
			catch (InterruptedException e)
			{
				Log.e("fangkehouWoodPecker", "AnrHandlerThread can not sleep", e);
			}
			catch (RemoteException e)
			{
				Log.e("fangkehouWoodPecker", "AnrHandlerThread send message error", e);
			}
		}
	}

}
