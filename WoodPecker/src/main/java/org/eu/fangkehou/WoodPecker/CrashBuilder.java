package org.eu.fangkehou.WoodPecker;
import android.content.*;

public class CrashBuilder
{
	
	private Context mcontext;
	private ProcesserBase mprocesser = new DemoProcesser();
	private boolean withANRHandler = true;
	
	private long sleeptime = 50L;
	private long anrtime = 1000L;

	private long killtime = 6000L;
	
	private CrashBuilder(){
	}
	private CrashBuilder(Context context){
		mcontext = context;
	}

	public void setWithANRHandler(boolean withANRHandler){
		this.withANRHandler = withANRHandler;
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

	public long getKilltime() {
		return killtime;
	}

	public void setKilltime(long killtime) {
		this.killtime = killtime;
	}
	
	public static CrashBuilder from(Context context){
		return new CrashBuilder(context);
	}
	
	public CrashBuilder setProcesser(ProcesserBase processer){
		this.mprocesser = processer;
		return this;
	}
	
	public CrashHandler build(){
		CrashGlobal.setSleepTime(sleeptime);
		CrashGlobal.setAnrTime(anrtime);
		CrashGlobal.setProcesser(mprocesser);
		CrashGlobal.setKillTime(killtime);
		CrashHandler c = CrashHandler.getInstance();
		c.setWithANRHandler(withANRHandler);
		c.setContext(mcontext);
		return c;
	}
	
}
