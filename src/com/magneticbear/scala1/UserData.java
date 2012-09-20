package com.magneticbear.scala1;

import java.util.ArrayList;

public class UserData 
{
	public static ArrayList<Struct_Event>   fav_events   = null;
	public static ArrayList<Struct_Speaker> fav_speakers = null;
	
	public static void load_or_create()
	{
		if(fav_events == null || fav_speakers == null)
		{
			// do load here gee
		
			fav_events   = new ArrayList<Struct_Event>();
			fav_speakers = new ArrayList<Struct_Speaker>();
		}
	}

}
