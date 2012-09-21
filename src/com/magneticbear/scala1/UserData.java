package com.magneticbear.scala1;

import java.util.ArrayList;

public class UserData 
{
	public static ArrayList<Struct_Event>   fav_events   	= null;
	public static ArrayList<Struct_Speaker> fav_speakers 	= null;
	
	public static void load_or_create()
	{
		if(fav_events == null || fav_speakers == null)
		{
			// do load here gee
			fav_events   = new ArrayList<Struct_Event>();
			fav_speakers = new ArrayList<Struct_Speaker>();
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
	
}
