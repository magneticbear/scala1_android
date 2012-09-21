package com.magneticbear.scala1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class ServerData 
{
	public static ArrayList<Struct_Event>   events   	= null;
	public static ArrayList<Struct_Speaker> speakers 	= null;
	
	public static void pull()
	{
		// do load here gee
		events   = new ArrayList<Struct_Event>();
		speakers = new ArrayList<Struct_Speaker>();	
		pull_events();
		pull_speakers();
	}

	public static void pull_events()
    {
    	 // Read event feed
        String rawEventFeedJSON = read_events();
        
        // Parse event feed
		try 
		{
			// Create object from base
			JSONObject base = new JSONObject(rawEventFeedJSON);
			
			// Check status
			if(!base.getString("status").equals("OK")) throw new Exception("Status from JSON was not 'OK'");
			
			// Check message
			if(!base.getString("message").equals("Success")) throw new Exception("Message from JSON was not 'Success'");
			
			// Get result object
			JSONObject result = base.getJSONObject("result");
			
			// Get events array from result object
			JSONArray json_events = result.getJSONArray("events");
			
			// Build an array of struct_events
			events = new ArrayList<Struct_Event>();
			
			// Create those objects from JSON
			for(int iter = 0; iter < json_events.length(); iter++)
			{
				// Get event object form json array at iter
				JSONObject   event   = json_events.getJSONObject(iter);	
				// Create a new struct event to hold this json event
				Struct_Event builder = new Struct_Event(event.getString("title"), event.getString("start"), event.getString("location"), event.getInt("id"));
				// Add it to the array of struct_events
				events.add(builder);
			}
			
			// Events_objects now holds all events

			// Sort items from earliest to latest start date
	    	int iter = 0;
	    	while(iter < events.size() - 1)
	    	{
	    		// Get A, B dates
	    		Date a = events.get(iter).start_date;
	    		Date b = events.get(iter + 1).start_date;
	    		
	    		// If A is after than B, swap, restart, else continue
	    		if(a.after(b))
	    		{
	    			// A is after B, swap
	    			Struct_Event register = events.get(iter);		 // Move A into register
	    			events.set(iter, events.get(iter + 1));	         // Move B into A's position
	    			events.set(iter + 1, register);				     // Move A from register into B's position
	    			
	    			// Restart
	    			iter = 0;
	    			continue;
	    		}	
	    		iter++;
	    	}
			
			
			Log.d("JSON", "Got " + json_events.length() + " events.");
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
    }
    
    public static String read_events() 
    {
        StringBuilder builder = new StringBuilder();
        HttpClient    client  = OpenAllSSLSocketFactory.getNewOpenAllSSLHttpClient(); //new DefaultHttpClient();
        HttpGet       httpGet = new HttpGet("https://scala1.tindr.co/events");
        
        try 
        {
			HttpResponse response   = client.execute(httpGet);
			StatusLine   statusLine = response.getStatusLine();
			int 		 statusCode = statusLine.getStatusCode();
			if (statusCode == 200) 
			{
				HttpEntity     entity  = response.getEntity();
				InputStream    content = entity.getContent();
				BufferedReader reader  = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) 
				{
					builder.append(line);
				}
			} 
			else 
			{
				Log.e(Events.class.getName(), "Failed to download events feed json.");
			}
        } 
        catch (ClientProtocolException e) 
        {
          e.printStackTrace();
        } 
        catch (IOException e) 
        {
          e.printStackTrace();
        }
        return builder.toString();
    }
    
    public static void pull_speakers()
    {
        // Read event feed
        String rawEventFeedJSON = read_speakers();
        
        // Parse event feed
		try 
		{
			// Create object from base
			JSONObject base = new JSONObject(rawEventFeedJSON);
			
			// Check status
			if(!base.getString("status").equals("OK")) throw new Exception("Status from JSON was not 'OK'");
			
			// Check message
			if(!base.getString("message").equals("Success")) throw new Exception("Message from JSON was not 'Success'");
			
			// Get result object
			JSONObject result = base.getJSONObject("result");
			
			// Get speakers array from result object
			JSONArray json_speakers = result.getJSONArray("speakers");
			
			// Build an array of struct_speaker objects
			speakers = new ArrayList<Struct_Speaker>();
			
			// Create those objects from JSON
			for(int iter = 0; iter < json_speakers.length(); iter++)
			{
				// Get event object form json array at iter
				JSONObject   speaker   = json_speakers.getJSONObject(iter);	
				// Create a new struct speaker to hold this json event
				Struct_Speaker builder = new Struct_Speaker(speaker.getString("name"), speaker.getInt("id"));
				// Add it to the array of struct_events
				speakers.add(builder);
			}
			
			// Events_objects now holds all events

			// Sort items alphabetically
	    	int iter = 0;
	    	while(iter < speakers.size() - 1)
	    	{
	    		// Get A, B names
	    		String a = speakers.get(iter).name;
	    		String b = speakers.get(iter + 1).name;
	    		
	    		// If A is after than B, swap, restart, else continue
	    		
	    		// If a comes after b, swap
	    		if(a.compareToIgnoreCase(b) > 0)
	    		{
	    			// A is after B, swap
	    			Struct_Speaker register = speakers.get(iter);		 // Move A into register
	    			speakers.set(iter, speakers.get(iter + 1));	 // Move B into A's position
	    			speakers.set(iter + 1, register);				     // Move A from register into B's position
	    			
	    			// Restart
	    			iter = 0;
	    			continue;
	    		}
	    		else
	    		{
	    			// Proper order
	    			iter++;
	    			continue;
	    		}
	    	}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
    	
    }
    
    public static String read_speakers() 
    {
        StringBuilder builder = new StringBuilder();
        HttpClient    client  = OpenAllSSLSocketFactory.getNewOpenAllSSLHttpClient(); //new DefaultHttpClient();
        HttpGet       httpGet = new HttpGet("https://scala1.tindr.co/speakers");
        
        try 
        {
			HttpResponse response   = client.execute(httpGet);
			StatusLine   statusLine = response.getStatusLine();
			int 		 statusCode = statusLine.getStatusCode();
			if (statusCode == 200) 
			{
				//Toast.makeText(getBaseContext(), "HTTP 200 on speakers feed GET.", Toast.LENGTH_LONG).show();
				
				HttpEntity     entity  = response.getEntity();
				InputStream    content = entity.getContent();
				BufferedReader reader  = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) 
				{
					builder.append(line);
				}
			} 
			else 
			{
				Log.e(Events.class.getName(), "Failed to download speakers feed json.");
			}
        } 
        catch (ClientProtocolException e) 
        {
        	e.printStackTrace();
        } 
        catch (IOException e) 
        {
          e.printStackTrace();
        }
        return builder.toString();
    }

    public static Struct_Event get_event_by_id(int eventid)
    {
    	for(int iter = 0; iter < events.size(); iter++)
    	{
    		if(events.get(iter).eventid == eventid)
    		{
    			return events.get(iter);
    		}
    	}
    	return null;
    }
    public static Struct_Speaker get_speaker_by_id(int speakerid)
    {
    	for(int iter = 0; iter < speakers.size(); iter++)
    	{
    		if(speakers.get(iter).speakerid == speakerid)
    		{
    			return speakers.get(iter);
    		}
    	}
    	return null;
    }
    
}
