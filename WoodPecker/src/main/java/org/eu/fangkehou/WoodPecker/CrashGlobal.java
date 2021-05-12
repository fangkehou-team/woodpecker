package org.eu.fangkehou.WoodPecker;

public class CrashGlobal
{
	public static String logCatHeader = "fangkehouWoodPecker";
	public static long sleepTime = 50L;
	public static long anrTime = 1000L;
	public static long killTime = 6000L;
	public static ProcesserBase mprocesser = new DemoProcesser();

	public static void setProcesser(ProcesserBase mprocesser)
	{
		CrashGlobal.mprocesser = mprocesser;
	}

	public static ProcesserBase getProcesser()
	{
		return mprocesser;
	}

	public static void setLogCatHeader(String logCatHeader)
	{
		CrashGlobal.logCatHeader = logCatHeader;
	}

	public static String getLogCatHeader()
	{
		return logCatHeader;
	}

	public static void setSleepTime(long sleepTime)
	{
		CrashGlobal.sleepTime = sleepTime;
	}

	public static long getSleepTime()
	{
		return sleepTime;
	}

	public static void setAnrTime(long anrTime)
	{
		CrashGlobal.anrTime = anrTime;
	}

	public static long getAnrTime()
	{
		return anrTime;
	}

	public static void setKillTime(long killTime)
	{
		CrashGlobal.killTime = killTime;
	}

	public static long getKillTime()
	{
		return killTime;
	}
}
