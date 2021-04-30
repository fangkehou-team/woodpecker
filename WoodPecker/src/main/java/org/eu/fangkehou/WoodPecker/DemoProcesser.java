package org.eu.fangkehou.WoodPecker;
import android.content.*;
import android.app.*;

public class DemoProcesser extends ProcesserBase
{

	@Override
	public void onANR(Context mcontext, String report)
	{
		// TODO: Implement this method
	}


	@Override
	public void onCrash(Context mContext, Throwable e, String report)
	{
		// TODO: Implement this method
		Intent i = new Intent(mContext,DemoDialogActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(i);
		System.exit(0);
	}
	
	
}
