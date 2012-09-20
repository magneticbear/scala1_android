package com.magneticbear.scala1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Favourites extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        setupButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_favourites, menu);
        return true;
    }
    
    public void setupButtons()
    {
    	final Button rocker_events   = (Button)findViewById(R.id.rocker_events);
    	final Button rocker_speakers = (Button)findViewById(R.id.rocker_speakers);
    
    	rocker_events.setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				rocker_events.setBackgroundResource(R.drawable.fav_tab_left_down);
				rocker_speakers.setBackgroundResource(R.drawable.fav_tab_right_up);
			}
		});
    	rocker_speakers.setOnClickListener(new OnClickListener() 
    	{	
			@Override
			public void onClick(View v) 
			{
				rocker_events.setBackgroundResource(R.drawable.fav_tab_left_up);
				rocker_speakers.setBackgroundResource(R.drawable.fav_tab_right_down);
			}
		});
    }
}
