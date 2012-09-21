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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.database.CursorJoiner.Result;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class Speakers extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speakers);
    }
    
    @Override
    protected void onResume() 
    {
    	refreshFeed();
    	super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_speakers, menu);
        return true;
    }
    
    public void refreshFeed()
    {
        // Read event feed
        String rawEventFeedJSON = readSpeakersFeed();
        
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
			JSONArray speakers = result.getJSONArray("speakers");
			
			// Build an array of struct_speaker objects
			ArrayList<Struct_Speaker> speakers_objects = new ArrayList<Struct_Speaker>();
			
			// Create those objects from JSON
			for(int iter = 0; iter < speakers.length(); iter++)
			{
				// Get event object form json array at iter
				JSONObject   speaker   = speakers.getJSONObject(iter);	
				// Create a new struct speaker to hold this json event
				Struct_Speaker builder = new Struct_Speaker(speaker.getString("name"), speaker.getInt("id"));
				// Add it to the array of struct_events
				speakers_objects.add(builder);
			}
			
			// Events_objects now holds all events

			// Sort items alphabetically
	    	int iter = 0;
	    	while(iter < speakers_objects.size() - 1)
	    	{
	    		// Get A, B names
	    		String a = speakers_objects.get(iter).name;
	    		String b = speakers_objects.get(iter + 1).name;
	    		
	    		// If A is after than B, swap, restart, else continue
	    		
	    		// If a comes after b, swap
	    		if(a.compareToIgnoreCase(b) > 0)
	    		{
	    			// A is after B, swap
	    			Struct_Speaker register = speakers_objects.get(iter);		 // Move A into register
	    			speakers_objects.set(iter, speakers_objects.get(iter + 1));	 // Move B into A's position
	    			speakers_objects.set(iter + 1, register);				     // Move A from register into B's position
	    			
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
			
			// Create an adapter
			Struct_Speaker_Adapter adapter = new Struct_Speaker_Adapter(getBaseContext(), R.id.struct_speaker_adapter_row_title, speakers_objects);
			
			// Connect list to adapter
			ListView lv = (ListView)findViewById(R.id.speakers_list);
			lv.setAdapter(adapter);
			
			Log.d("JSON", "Got " + speakers.length() + " speakers.");
			
		} 
		catch (Exception e) 
		{
			//Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
    	
    }
    
    public String readSpeakersFeed() 
    {
        StringBuilder builder = new StringBuilder();
        HttpClient    client  = OpenAllSSLSocketFactory.getNewOpenAllSSLHttpClient(); //new DefaultHttpClient();
        HttpGet       httpGet = new HttpGet(getString(R.string.url_speakers_feed));
        
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
				Toast.makeText(getBaseContext(), "Failed to download speakers feed json.", Toast.LENGTH_LONG).show();
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
}
