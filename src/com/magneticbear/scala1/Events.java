package com.magneticbear.scala1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class Events extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        
        // Read event feed
        String rawEventFeedJSON = readEventFeed();
        
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
			JSONArray events = result.getJSONArray("events");
			
			// Build an array of struct_event objects
			ArrayList<Struct_Event> events_objects = new ArrayList<Struct_Event>();
			
			// Create those objects from JSON
			for(int iter = 0; iter < events.length(); iter++)
			{
				// Get event object form json array at iter
				JSONObject   event   = events.getJSONObject(iter);	
				// Create a new struct event to hold this json event
				Struct_Event builder = new Struct_Event(event.getString("title"), event.getString("start"), event.getString("location"), event.getString("id"));
				// Add it to the array of struct_events
				events_objects.add(builder);
			}
			
			// Create an adapter
			Struct_Event_Adapter adapter = new Struct_Event_Adapter(getBaseContext(), R.id.struct_event_adapter_row_title, events_objects);
			
			// Connect list to adapter
			ListView lv = (ListView)findViewById(R.id.events_list);
			lv.setAdapter(adapter);
			
			Log.d("JSON", "Got " + events.length() + " events.");
			
		} 
		catch (Exception e) 
		{
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_events, menu);
        return true;
    }
    
    
    public String readEventFeed() 
    {
        StringBuilder builder = new StringBuilder();
        HttpClient    client  = new DefaultHttpClient();
        HttpGet       httpGet = new HttpGet(getString(R.string.url_events_feed));
        
        try 
        {
			HttpResponse response   = client.execute(httpGet);
			StatusLine   statusLine = response.getStatusLine();
			int 		 statusCode = statusLine.getStatusCode();
			if (statusCode == 200) 
			{
				Toast.makeText(getBaseContext(), "HTTP 200 on events feed GET.", Toast.LENGTH_LONG).show();
				
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
				Toast.makeText(getBaseContext(), "Failed to download events feed json.", Toast.LENGTH_LONG).show();
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
