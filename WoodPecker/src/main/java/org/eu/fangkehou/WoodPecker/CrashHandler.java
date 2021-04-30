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
	private ProcesserBase mprocesser;
	private Thread.UncaughtExceptionHandler defaultHandler;
	private String logcatheader = "fangkehouWoodPecker";

	private Map<String, String> infos = new HashMap<String, String>();

    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    // 日志地址
    private String logPath;

	private CrashHandler()
	{

	}

	public void setContext(Context mcontext)
	{
		this.mcontext = mcontext;
	}

	public void setProcesser(ProcesserBase mprocesser)
	{
		this.mprocesser = mprocesser;
	}

	public ProcesserBase getProcesser()
	{
		return this.mprocesser;
	}

	public static CrashHandler getinstance()
	{
		return instance;
	}

	public void init()
	{
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		

		if (this.mcontext != null && this.mprocesser != null)
		{
			Thread.setDefaultUncaughtExceptionHandler(this);
			//Log.i("fangkehouWoodPecker",String.valueOf(android.os.Process.myPid()));
			Intent i = new Intent(mcontext, AnrMonitorService.class);
			i.putExtra("pid",android.os.Process.myPid());
			mcontext.startService(i);
			
			return;
		}

		// TODO: Implement this method
		
	}


	@Override
	public void uncaughtException(Thread p1, Throwable p2)
	{
		Log.i("A Cute WoodPecker", "Oh!There is a bug!");

		// TODO: Implement this method
		if (p2 == null && defaultHandler != null)
		{
			defaultHandler.uncaughtException(p1, p2);
			return;
		}

		collectDeviceInfo(mcontext);

		StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet())
		{
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

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
		Log.e(logcatheader, es, p2);
		if (mprocesser != null)
		{
			mprocesser.onCrash(mcontext, p2, es);
		}
		else
		{
			defaultHandler.uncaughtException(p1, p2);
		}

	}

	/**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx)
	{
        // 避免重复取基础数据
        if (infos.size() != 0)
		{
            return;
        }
        try
		{
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null)
			{
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        }
		catch (PackageManager.NameNotFoundException e)
		{
            Log.e(logcatheader, "an error occured when collect package info", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields)
		{
            try
			{
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(logcatheader, field.getName() + " : " + field.get(null));
            }
			catch (Exception e)
			{
                Log.e(logcatheader, "an error occured when collect crash info", e);
            }
        }
        // 增加android版本日志, 不需要 上面已经几乎全部包括了
//        Field[] versionFields = Build.VERSION.class.getDeclaredFields();
//        for (Field field : versionFields) {
//            try {
//                field.setAccessible(true);
//                infos.put(field.getName(), field.get(null).toString());
//                Log.d(TAG, field.getName() + " : " + field.get(null));
//            } catch (Exception e) {
//                Log.e(TAG, "an error occured when collect crash info", e);
//            }
//        }
    }

}
