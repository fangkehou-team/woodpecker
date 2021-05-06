package org.eu.fangkehou.WoodPecker;
import android.util.*;

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
		while(this.isContinue){
			
			
			
			try
			{
				Thread.sleep(50L);
			}
			catch (InterruptedException e)
			{
				Log.e("fangkehouWoodPecker","AnrHandlerThread can not sleep");
			}
		}
	}
	
}
