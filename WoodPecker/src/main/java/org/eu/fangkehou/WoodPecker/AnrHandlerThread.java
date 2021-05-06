package org.eu.fangkehou.WoodPecker;

public class AnrHandlerThread implements Runnable
{
	Boolean isContinue = true;
	
	private static AnrHandlerThread instance = new AnrHandlerThread();
	
	private AnrHandlerThread(){
		
	}
	
	public static AnrHandlerThread getInstance(){
		return instance;
	}
	
	public void stopWorking(){
		this.isContinue = false;
	}

	@Override
	public void run()
	{
		// TODO: Implement this method
	}
	
}
