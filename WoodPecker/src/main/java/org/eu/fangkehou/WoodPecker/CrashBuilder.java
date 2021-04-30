package org.eu.fangkehou.WoodPecker;
import android.content.*;

public class CrashBuilder
{
	
	private Context mcontext;
	private ProcesserBase mprocesser = new DemoProcesser();
	
	//android.intent.action.ANR
	
	private CrashBuilder(){
	}
	private CrashBuilder(Context context){
		mcontext = context;
	}
	
	public static CrashBuilder from(Context context){
		return new CrashBuilder(context);
	}
	
	public CrashBuilder setProcesser(ProcesserBase processer){
		this.mprocesser = processer;
		return this;
	}
	
	public CrashHandler build(){
		CrashHandler c = CrashHandler.getinstance();
		c.setProcesser(mprocesser);
		c.setContext(mcontext);
		return c;
	}
	
}
