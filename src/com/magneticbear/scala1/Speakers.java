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
		// Create an adapter
		Struct_Speaker_Adapter adapter = new Struct_Speaker_Adapter(getBaseContext(), R.id.struct_speaker_adapter_row_title, (ArrayList<Struct_Speaker>)ServerData.speakers.clone(), true, true);
		
		// Connect list to adapter
		ListView lv = (ListView)findViewById(R.id.speakers_list);
		lv.setAdapter(adapter);
    }
}
