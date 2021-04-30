package org.eu.fangkehou.WoodPecker;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class DemoDialogActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.DemoCrashDialog);
		
		LinearLayout dialogroot = findViewById(R.id.DemoCrashDialogRootLinearLayout);
		
		dialogroot.setOutlineProvider(new ViewOutlineProvider(){

				@Override
				public void getOutline(View p1, Outline p2)
				{
					// TODO: Implement this method
					p2.setRoundRect(0,0,p1.getWidth(),p1.getHeight(),18);
				}


			});
		dialogroot.setClipToOutline(true);
	}
	
}
