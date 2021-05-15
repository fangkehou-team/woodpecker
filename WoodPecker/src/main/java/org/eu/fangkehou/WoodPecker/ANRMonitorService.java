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
	private static List<String> anrtags = new ArrayList<String>();

	private class ClassRecordHandler extends Handler
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			handleThreadTag(msg.what , msg.getData(),msg.replyTo);
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

	private void handleThreadTag(int tagPid, Bundle bundle,Messenger replyto)
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

		if (currentTagElement.tag.equals(tag) && deltaTime >= CrashGlobal.getAnrTime())
		{
			
			//ANR occured
			if(deltaTime >= CrashGlobal.getKillTime())
			{
				CrashElement.ANRException anrexception = new CrashElement.ANRException(tag);
				Message message = Message.obtain(null, tagPid);
				Bundle exceptionbundle = new Bundle();
				bundle.putSerializable("exception",anrexception);
				message.setData(exceptionbundle);
				try
				{
					replyto.send(message);
				}
				catch (RemoteException e)
				{}
				Log.i(CrashGlobal.getLogCatHeader(), "kill Process:" + tagPid);
				
			}else if(!anrtags.contains(tag))
			{
				anrtags.add(tag);
				//handle ANR
				Log.i("a cute woodpecker", "Wow! It seems that you have put too much work on this thread!");
				StringBuilder sb = new StringBuilder();
				sb.append(CrashElement.PhoneInfo.getInfo(this) + "\n");
				sb.append(tag);
				Log.e(CrashGlobal.getLogCatHeader(),sb.toString());
			}
			
			
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
