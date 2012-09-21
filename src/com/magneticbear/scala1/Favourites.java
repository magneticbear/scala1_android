package com.magneticbear.scala1;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class Favourites extends Activity {

	Boolean showingEvents = true;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        setupButtons();
    }

    @Override
    protected void onResume() 
    {
    	refreshFeed();
    	super.onResume();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_favourites, menu);
        return true;
    }
    
    public void refreshFeed()
    {
    	if(showingEvents)
    	{
    		show_events();
    	}
    	else
    	{
    		show_speakers();
    	}
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
				show_events();
			}
		});
    	rocker_speakers.setOnClickListener(new OnClickListener() 
    	{	
			@Override
			public void onClick(View v) 
			{
				rocker_events.setBackgroundResource(R.drawable.fav_tab_left_up);
				rocker_speakers.setBackgroundResource(R.drawable.fav_tab_right_down);
				show_speakers();
			}
		});
    }
    
    public void show_events()
    {
    	showingEvents = true;
    	ArrayList<Struct_Event> local_event = (ArrayList)UserData.fav_events.clone();
    	Struct_Event_Adapter adapter = new Struct_Event_Adapter(getBaseContext(), R.id.struct_event_adapter_row_title, local_event, false, false);
    	((ListView)findViewById(R.id.favourites_list)).setAdapter(adapter);
    	
    }
    public void show_speakers()
    {
    	showingEvents = false;
    	ArrayList<Struct_Speaker> local_speaker = (ArrayList)UserData.fav_speakers.clone();
     	Struct_Speaker_Adapter adapter = new Struct_Speaker_Adapter(getBaseContext(), R.id.struct_speaker_adapter_row_title, local_speaker, false, false);
     	((ListView)findViewById(R.id.favourites_list)).setAdapter(adapter);
    }
}
















