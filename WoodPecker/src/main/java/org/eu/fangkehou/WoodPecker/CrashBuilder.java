package org.eu.fangkehou.WoodPecker;
import android.content.*;

public class CrashBuilder
{
	
	private Context mcontext;
	private ProcesserBase mprocesser = new DemoProcesser();
	
	private long sleeptime = 50L;
	private long anrtime = 1000L;
	
	private CrashBuilder(){
	}
	private CrashBuilder(Context context){
		mcontext = context;
	}

	public void setSleeptime(long sleeptime)
	{
		this.sleeptime = sleeptime;
	}

	public long getSleeptime()
	{
		return sleeptime;
	}

	public void setAnrtime(long anrtime)
	{
		this.anrtime = anrtime;
	}

	public long getAnrtime()
	{
		return anrtime;
	}
	
	public static CrashBuilder from(Context context){
		return new CrashBuilder(context);
	}
	
	public CrashBuilder setProcesser(ProcesserBase processer){
		this.mprocesser = processer;
		return this;
	}
	
	public CrashHandler build(){
		CrashGlobal.sleepTime = sleeptime;
		CrashGlobal.anrTime = anrtime;
		CrashHandler c = CrashHandler.getInstance();
		c.setProcesser(mprocesser);
		c.setContext(mcontext);
		return c;
	}
	
}
