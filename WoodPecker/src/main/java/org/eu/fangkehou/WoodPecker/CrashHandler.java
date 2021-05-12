package org.eu.fangkehou.WoodPecker;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.util.*;
import java.io.*;
import java.lang.reflect.*;
import java.text.*;
import java.util.*;
import android.widget.*;

public class CrashHandler implements Thread.UncaughtExceptionHandler
{

	private static CrashHandler instance = new CrashHandler();
	private Context mcontext;
	private Thread.UncaughtExceptionHandler defaultHandler;
	
	ANRHandlerThread mAnrHandlerThread = new ANRHandlerThread();
	
	private CrashHandler()
	{

	}

	public void setContext(Context mcontext)
	{
		this.mcontext = mcontext;
	}

	public static CrashHandler getInstance()
	{
		return instance;
	}

	public void init()
	{
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		

		if (this.mcontext != null)
		{
			Thread.setDefaultUncaughtExceptionHandler(this);
			
			Intent i = new Intent(mcontext, ANRMonitorService.class);
			i.putExtra("pid",android.os.Process.myPid());
			
			ServiceConnection mConn = new ServiceConnection(){

				@Override
				public void onServiceConnected(ComponentName p1, IBinder p2)
				{
					
					mAnrHandlerThread.setMessager(new Messenger(p2));
					new Thread(mAnrHandlerThread).start();
				}

				@Override
				public void onServiceDisconnected(ComponentName p1)
				{
					
					mAnrHandlerThread.stopWorking();
				}
				
				
			};
			
			mcontext.bindService(i,mConn,mcontext.BIND_AUTO_CREATE);
			
		}

		
		
	}


	@Override
	public void uncaughtException(Thread p1, Throwable p2)
	{
		Log.i("A Cute WoodPecker", "Oh!There is a bug!");

		if (p2 == null && defaultHandler != null)
		{
			defaultHandler.uncaughtException(p1, p2);
			return;
		}

		StringBuffer sb = new StringBuffer();
		sb.append(CrashElement.PhoneInfo.getInfo(mcontext) + "\n");
        
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        p2.printStackTrace(printWriter);
        Throwable cause = p2.getCause();
        while (cause != null)
		{
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
		String es = sb.toString();
		Log.e(CrashGlobal.getLogCatHeader(), es, p2);
		CrashGlobal.getProcesser().onCrash(mcontext, p2, es);
	}

    

}
