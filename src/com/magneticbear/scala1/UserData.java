package com.magneticbear.scala1;

import java.util.ArrayList;
import java.util.Map;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData 
{
	public static final String PREFS_NAME = "scala1_local_favs";
	public static ArrayList<Struct_Event>   fav_events   	= null;
	public static ArrayList<Struct_Speaker> fav_speakers 	= null;
	
	public static MixpanelAPI mixpanel;
	
	public static void load_or_create(Context context)
	{
		// Get mixpanel api instance
		mixpanel = MixpanelAPI.getInstance(context,"JRR TOKEN GOES HERE");
		
		if(fav_events == null || fav_speakers == null)
		{
			// Load
			fav_events   = new ArrayList<Struct_Event>();
			fav_speakers = new ArrayList<Struct_Speaker>();
			
			SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
			if(settings == null)
				return;
			
			// Get all saved
			Map<String, ?> saved = settings.getAll();
			
			// Get all events
			int iter = 0;
			while(saved.containsKey("e" + iter))
			{
				int fav_event_id = (Integer)saved.get("e" + iter);
				add_fav(ServerData.get_event_by_id(fav_event_id));
				iter++;
			}
			// Get all speakers
			iter = 0;
			while(saved.containsKey("s" + iter))
			{
				int fav_speaker_id = (Integer)saved.get("s" + iter);
				add_fav(ServerData.get_speaker_by_id(fav_speaker_id));
				iter++;
			}
			
		}
	}
	public static void add_fav(Struct_Event event)
	{
		fav_events.add(event);
	}
	public static void add_fav(Struct_Speaker speaker)
	{
		fav_speakers.add(speaker);
	}
	public static void remove_fav(Struct_Event event)
	{
		for(int iter = 0; iter < fav_events.size(); iter++)
		{
			if(fav_events.get(iter).eventid == event.eventid)
			{
				fav_events.remove(iter);
				return;
			}
		}
	}
	public static void remove_fav(Struct_Speaker speaker)
	{
		for(int iter = 0; iter < fav_speakers.size(); iter++)
		{
			if(fav_speakers.get(iter).speakerid == speaker.speakerid)
			{
				fav_speakers.remove(iter);
				return;
			}
		}
	}
	public static Boolean is_fav(Struct_Event event)
	{
		for(int iter = 0; iter < fav_events.size(); iter++)
		{
			if(fav_events.get(iter).eventid == event.eventid)
			{
				return true;
			}
		}
		return false;
	}
	public static Boolean is_fav(Struct_Speaker speaker)
	{
		for(int iter = 0; iter < fav_speakers.size(); iter++)
		{
			if(fav_speakers.get(iter).speakerid == speaker.speakerid)
			{
				return true;
			}
		}
		return false;
	}
	
	public static void write_changes(Context context)
	{
		  // We need an Editor object to make preference changes.
	      // All objects are from android.context.Context
	      SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      
	      // Save favs
	      for(int iter = 0; iter < fav_events.size(); iter++)
	      {
	    	  editor.putInt("e" + iter, fav_events.get(iter).eventid);
	      }
	      // Save favs
	      for(int iter = 0; iter < fav_speakers.size(); iter++)
	      {
	    	  editor.putInt("s" + iter, fav_speakers.get(iter).speakerid);
	      }

	      // Commit the edits!
	      editor.commit();	
	}
	
}
