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
        
        UserData.load_or_create();
        
        long splash_time;
        if(UserData.has_seen_splash)
        {
        	splash_time = 1000;
        }
        else
        {
        	splash_time = 2000;
        }
        
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
		}, splash_time);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
}
