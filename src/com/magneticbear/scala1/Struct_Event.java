package com.magneticbear.scala1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.Time;

public class Struct_Event 
{
	public Boolean isSeparator;
	
	public String event_or_seperator_title;
	public Date   start_date;
	public String location;
	public int    eventid;
	
	public Struct_Event(String Title, String Start, String Location, int EventID)
	{
		isSeparator = false;
		event_or_seperator_title = Title;
		location    = Location;
		eventid     = EventID;
		start_date  = readDate(Start);
	}
	public Struct_Event(String Title)
	{
		isSeparator = true;
		event_or_seperator_title       = Title;
	}
	
	private Date readDate(String DateString)
	{
		// pull date and time from start string
		//                      9   10   11      16    19     23
		// SAMPLE::    2012-10-02    T    10:00   :00   .000   Z
		//
		// FIRST 10 CHARS ARE SIMPLE DATE
		String datestring = DateString.substring(0, 10);
		
		// CHARS 11->16 ARE TIME HH:MM
		String timestring =  DateString.substring(11, 16); 
		 
		// Format date
		Date date = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:kk:mm"); 
		try 
		{
			date = simpleDateFormat.parse(datestring + ":" + timestring);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		} 
		
		return date;
	}
}
