package org.eu.fangkehou.WoodPecker;
import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;

import java.io.Serializable;
import java.util.concurrent.*;
import java.util.*;

public class ANRMonitorService extends Service
{
	private static final ConcurrentHashMap<Integer,CrashElement.ThreadTag> currentTag = new ConcurrentHashMap<Integer,CrashElement.ThreadTag>();
	private static final List<String> anrtags = new ArrayList<String>();

	@SuppressLint("HandlerLeak")
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
		Log.i(CrashGlobal.getLogCatHeader(), "Starting ANR Monitor for PID" + pid + "(UI Thread Only)");

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
			currentTag.remove(tagPid);
			return;
		}

		long currentTime = System.currentTimeMillis();
		if (!currentTag.containsKey(tagPid))
		{
			currentTag.put(tagPid, new CrashElement.ThreadTag(tag, currentTime));
		}

		CrashElement.ThreadTag currentTagElement = currentTag.get(tagPid);
		assert currentTagElement != null;
		long deltaTime = currentTime - currentTagElement.startTime;

		if (currentTagElement.tag.equals(tag) && deltaTime >= CrashGlobal.getAnrTime())
		{
			
			//ANR occured
			if(deltaTime >= CrashGlobal.getKillTime())
			{
				android.os.Process.killProcess(tagPid);
				Log.i(CrashGlobal.getLogCatHeader(), "kill Process:" + tagPid);
				
			}else if(!anrtags.contains(tag))
			{
				anrtags.add(tag);
				//handle ANR
				Log.i("a cute woodpecker", "Wow! It seems that you have put too much work on this thread!");
				String sb = CrashElement.PhoneInfo.getInfo(this) + "\n" +
						tag;
				Log.e(CrashGlobal.getLogCatHeader(), sb);
			}


		}
		else if (!currentTagElement.tag.equals(tag))
		{
			currentTag.replace(tagPid, new CrashElement.ThreadTag(tag, currentTime));
		}

	}


}
