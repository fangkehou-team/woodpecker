package org.eu.fangkehou.demo.WoodPeckerDemo;
import android.app.*;
import java.io.*;
import org.eu.fangkehou.WoodPecker.*;

public class demoApplication extends Application
{

	private static demoApplication instance;

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		instance = this;

		if(getProcessName(android.os.Process.myPid()).equals(this.getPackageName())){
			CrashBuilder.from(getApplicationContext()).build().init();

		}

	}

	public static demoApplication getInstance()
	{
        return instance;
    }
	
	public static String getProcessName(int pid)
	{
        try
		{
            File file = new File("/proc/" + pid + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        }
		catch (Exception e)
		{
            e.printStackTrace();
            return null;
        }
    }
	
}
