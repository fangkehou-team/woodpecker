package org.eu.fangkehou.WoodPecker;
import android.content.*;

public interface ProcesserBase
{
	
	public abstract void onCrash(Context mContext,Throwable e,String report);
	
	public abstract void onANR(Context mcontext,String report);

}
