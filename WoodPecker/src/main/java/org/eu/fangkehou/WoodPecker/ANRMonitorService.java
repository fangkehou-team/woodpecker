package org.eu.fangkehou.WoodPecker;
import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import java.util.concurrent.*;
import java.util.*;

public class ANRMonitorService extends Service
{
	private static ConcurrentHashMap<Integer,CrashElement.ThreadTag> currentTag = new ConcurrentHashMap<Integer,CrashElement.ThreadTag>();
	private static List<CrashElement.ThreadTag> anrtags = new ArrayList<CrashElement.ThreadTag>();

	private class ClassRecordHandler extends Handler
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			handleThreadTag(msg.what , msg.getData());
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
		int pid = intent.getIntExtra("pid", 0);
		Log.i("fangkehouWoodPecker", "Starting ANR Monitor for PID" + pid + "(UI Thread Only)");

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
	}

	private void handleThreadTag(int tagPid, Bundle bundle)
	{
		String tag = bundle.getString("tag", "none");

		if (!tag.contains("android.os.Handler.dispatchMessage"))
		{
			if (currentTag.containsKey(tagPid))
			{
				currentTag.remove(tagPid);
			}
			return;
		}

		long currentTime = System.currentTimeMillis();
		if (!currentTag.containsKey(tagPid))
		{
			currentTag.put(tagPid, new CrashElement.ThreadTag(tag, currentTime));
		}

		CrashElement.ThreadTag currentTagElement = currentTag.get(tagPid);
		long deltaTime = currentTime - currentTagElement.startTime;

		if (currentTagElement.tag.equals(tag) && deltaTime >= 1000L)
		{
			
			//ANR occured
			if(deltaTime >= 6000L)
			{
				
			}
			
			Log.i("a cute woodpecker", "Wow! It seems that you have put too much work on this thread!");
			return;
		}
		else if (!currentTagElement.tag.equals(tag))
		{
			currentTag.replace(tagPid, new CrashElement.ThreadTag(tag, currentTime));
			return;
		}

		return;
	}


}
