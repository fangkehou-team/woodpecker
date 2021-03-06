package org.eu.fangkehou.WoodPecker;

import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.util.*;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;

public class CrashElement
{
	
	public static class ThreadTag implements Serializable {
		public String tag;
		public long startTime;

		public ThreadTag(String tag, long startTime)
		{
			this.tag = tag;
			this.startTime = startTime;
		}
	}
	
	public static class PhoneInfo
	{
		private static final Map<String, String> infos = new HashMap<>();
		
		public static String getInfo(Context context){
			
			collectDeviceInfo(context);
			
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : infos.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				sb.append(key).append("=").append(value).append("\n");
			}
			
			return sb.toString();
		}
		
		private static void collectDeviceInfo(Context ctx)
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
				Log.e(CrashGlobal.logCatHeader, "an error occured when collect package info", e);
			}

			Field[] fields = Build.class.getDeclaredFields();
			for (Field field : fields)
			{
				try
				{
					field.setAccessible(true);
					infos.put(field.getName(), Objects.requireNonNull(field.get(null)).toString());
					Log.d(CrashGlobal.logCatHeader, field.getName() + " : " + field.get(null));
				}
				catch (Exception e)
				{
					Log.e(CrashGlobal.logCatHeader, "an error occured when collect crash info", e);
				}
			}
		}
	}
	
}
