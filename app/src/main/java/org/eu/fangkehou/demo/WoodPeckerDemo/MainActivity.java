package org.eu.fangkehou.demo.WoodPeckerDemo;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class MainActivity extends Activity implements OnClickListener
{

	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.demoButtonANR:
				SystemClock.sleep(100000);
				break;
				
			case R.id.demoButtonCrash:
				System.out.println(1/0);
				break;
		}
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Button CrashButton = findViewById(R.id.demoButtonCrash);
		Button ANRButton = findViewById(R.id.demoButtonANR);
		
		CrashButton.setOnClickListener(this);
		ANRButton.setOnClickListener(this);
		
		
    }
}
