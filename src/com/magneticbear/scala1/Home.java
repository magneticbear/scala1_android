package com.magneticbear.scala1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        setupButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }
    
    public void setupButtons()
    {
    	findViewById(R.id.btn_events).setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(getBaseContext(), Events.class);
				startActivity(intent);
			}
		});
    	findViewById(R.id.btn_speakers).setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(getBaseContext(), Speakers.class);
				startActivity(intent);
			}
		});
    	findViewById(R.id.btn_favourites).setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(getBaseContext(), Favourites.class);
				startActivity(intent);
			}
		});
    			
    	findViewById(R.id.btn_playground).setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(getBaseContext(), Playground.class);
				startActivity(intent);
			}
		});
    	findViewById(R.id.btn_about).setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(getBaseContext(), About.class);
				startActivity(intent);
			}
		});
    }
}
