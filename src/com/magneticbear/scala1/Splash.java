package com.magneticbear.scala1;

import java.util.Timer;
import java.util.TimerTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class Splash extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        new AsyncTask<Void, Void, Void>() 
        {
			@Override
			protected Void doInBackground(Void... params) {
				
				ServerData.pull();
		        UserData.load_or_create(getBaseContext());
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) 
			{
				Intent intent = new Intent(getBaseContext(), Home.class);
				startActivity(intent);
		        finish();
				super.onPostExecute(result);
			}
		}.execute((Void)null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
}
