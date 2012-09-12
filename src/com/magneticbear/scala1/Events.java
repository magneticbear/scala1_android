package com.magneticbear.scala1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
			JSONObject o = new JSONObject(rawEventFeedJSON);
			//Toast.makeText(getBaseContext(), "Length of JSON: " + o.length(), Toast.LENGTH_SHORT).show();
			//Toast.makeText(getBaseContext(), o.names().toString(), Toast.LENGTH_SHORT).show();
			
			// Loop through each key in the JSON
			for(int iter = 0; iter < o.names().length(); iter++)
			{
				String name = o.names().getString(iter);
				
				if(name.equals("message"))
				{	
					String message = o.getString(name);
					//Toast.makeText(getBaseContext(), "Message: " + message, Toast.LENGTH_SHORT).show();
				}
				else if(name.equals("status"))
				{
					String status = o.getString(name);
					//Toast.makeText(getBaseContext(), "Status: " + status, Toast.LENGTH_SHORT).show();
				}
				else if(name.equals("result"))
				{
					JSONObject result = o.getJSONObject(name);
					Toast.makeText(getBaseContext(), "Digging in:  " + result.names().getString(0) , Toast.LENGTH_LONG).show();
					
				}
				
				
			}
		} 
		catch (Exception e) 
		{
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
