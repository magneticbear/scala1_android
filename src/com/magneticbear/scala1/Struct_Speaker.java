package com.magneticbear.scala1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.Time;

public class Struct_Speaker 
{
	public Boolean isSeparator;
	
	public String name;
	public int    speakerid;
	public String seperator_title;
	
	public Struct_Speaker(String Name, int SpeakerID)
	{
		isSeparator = false;
		name        = Name;
		speakerid   = SpeakerID;
	}
	public Struct_Speaker(String Title)
	{
		isSeparator     = true;
		seperator_title = Title;
	}
}
