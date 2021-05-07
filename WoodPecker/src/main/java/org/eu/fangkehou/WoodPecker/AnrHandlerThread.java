package org.eu.fangkehou.WoodPecker;
import android.util.*;
import android.os.*;

public class AnrHandlerThread implements Runnable
{
	private Boolean isContinue = true;
	private Messenger mService = null;
	private int pid = 0;

	public AnrHandlerThread(Messenger mService)
	{
		this.mService = mService;
		this.pid = android.os.Process.myPid();
	}


	public AnrHandlerThread()
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

			StackTraceElement[] st = Looper.getMainLooper().getThread().getStackTrace();
			Message message = Message.obtain(null, pid, st);
			
			try
			{
				mService.send(message);
				Thread.sleep(50L);
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