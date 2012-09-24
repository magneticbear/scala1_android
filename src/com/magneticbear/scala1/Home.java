package com.magneticbear.scala1;

import android.net.Uri;
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
        
        
        // typesafe link
        findViewById(R.id.typesafe_logo).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserData.mixpanel.track("HomeToTypeSafeLogo", null);
				
				Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( "http://www.typesafe.com" ) );
			    startActivity( browse );
			}
		});
        
        setupButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }
    
    @Override
    protected void onDestroy() {
    	// Flush mixpanel on the way down
    	UserData.mixpanel.flush();
    	super.onDestroy();
    }
    
    @Override
    public void onBackPressed() {
    	UserData.mixpanel.track("HomeBack", null);
    	
    	super.onBackPressed();
    }
    
    public void setupButtons()
    {
    	findViewById(R.id.btn_events).setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				UserData.mixpanel.track("HomeToEventsList", null);
				
				Intent intent = new Intent(getBaseContext(), Events.class);
				startActivity(intent);
			}
		});
    	findViewById(R.id.btn_speakers).setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				UserData.mixpanel.track("HomeToSpeakersList", null);
				
				Intent intent = new Intent(getBaseContext(), Speakers.class);
				startActivity(intent);
			}
		});
    	findViewById(R.id.btn_favourites).setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				UserData.mixpanel.track("HomeToFavouritesList", null);
				
				Intent intent = new Intent(getBaseContext(), Favourites.class);
				startActivity(intent);
			}
		});
    			
    	findViewById(R.id.btn_playground).setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				UserData.mixpanel.track("HomeToPlayground", null);
				
				Intent intent = new Intent(getBaseContext(), Playground.class);
				startActivity(intent);
			}
		});
    	findViewById(R.id.btn_about).setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				UserData.mixpanel.track("HomeToAbout", null);
				
				Intent intent = new Intent(getBaseContext(), About.class);
				startActivity(intent);
			}
		});
    }
}
