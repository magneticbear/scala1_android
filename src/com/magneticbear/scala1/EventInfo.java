package com.magneticbear.scala1;

import android.net.http.SslError;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class EventInfo extends Activity 
{	
	Bundle save_event;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	// Create a bundle to save the web state
    	if(save_event == null)
    	{
    		save_event = new Bundle();
    	}
    		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        
        // Setup debug data
        String event_title_to_display = "Event Info";
        int event_id_to_load = 1;
        
        // Setup real data
        if(getIntent().getExtras() != null) 
    	{
    		if(getIntent().getExtras().containsKey("index")) event_id_to_load = getIntent().getExtras().getInt("index");
    	}
        
        // Navigate to event url
        WebView event_info_webview = (WebView)findViewById(R.id.webview_event_info);
        
        // This is a bugfix for HTTPS pages, that will just not display because the user is not prompted
        // to accept the cert. This is an android bug, and this is the accepted workaround
        event_info_webview.setWebViewClient(new WebViewClient() 
        {
        	// Ignore SSL certs
        	 public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) 
        	 {
        		 handler.proceed();
        	 }
        	 
        	 @Override
        	 public boolean shouldOverrideUrlLoading(WebView view, String url) 
        	 {
				String[] urlbits = url.split("/");
				String   type 	 = urlbits[urlbits.length - 2];
				String   index 	 = urlbits[urlbits.length - 1];
        		 
        		if(type.equals("speakers"))
        		{
        			// Go to speaker of index
        			Intent intent = new Intent(view.getContext(), SpeakersInfo.class);
        			intent.putExtra("index", Integer.parseInt(index));
                    startActivity(intent);
        			//startActivityForResult(intent, 0);
        		}
        		else if(type.equals("events"))
        		{
        			// Go to event of index
        			Intent intent = new Intent(view.getContext(), EventInfo.class);
        			intent.putExtra("index", Integer.parseInt(index));
                    startActivity(intent);
        			//startActivityForResult(intent, 0);
        		}
				
				return false;
        	 }
        });
        
        event_info_webview.getSettings().setJavaScriptEnabled(true);
        event_info_webview.loadUrl(getString(R.string.url_event_info_base) + event_id_to_load + "?hideActions=1");
        
        // Set title to event title
        TextView title = (TextView)findViewById(R.id.event_info_title);
        title.setText(event_title_to_display);
        
        if(savedInstanceState != null)
        {
        	event_info_webview.restoreState(savedInstanceState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_event_info, menu);
        return true;
    }

    @Override
    protected void onPause() 
    {
    	((WebView)findViewById(R.id.webview_event_info)).saveState(save_event);
    	super.onPause();
    }
    
    @Override
    protected void onResume() 
    {
    	((WebView)findViewById(R.id.webview_event_info)).restoreState(save_event);
    	onCreate(save_event);
    	super.onResume();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) 
    {
    	((WebView)findViewById(R.id.webview_event_info)).saveState(outState);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) 
    {
    	((WebView)findViewById(R.id.webview_event_info)).restoreState(savedInstanceState);
    }
}
