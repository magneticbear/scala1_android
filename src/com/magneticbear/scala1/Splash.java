package com.magneticbear.scala1;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class Splash extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        
        Timer splashtimer = new Timer();
        splashtimer.schedule(new TimerTask() 
        {
			@Override
			public void run()
			{
				Intent intent = new Intent(getBaseContext(), Home.class);
				startActivity(intent);
		        finish();
			}
		}, 2000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
}
