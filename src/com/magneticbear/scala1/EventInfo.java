package com.magneticbear.scala1;

import android.net.http.SslError;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class EventInfo extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        
        // Setup debug data
        String event_title_to_display = "Event Info";
        int event_id_to_load = 1;
        
        // Navigate to event url
        WebView event_info_webview = (WebView)findViewById(R.id.webview_event_info);
        
        // This is a bugfix for HTTPS pages, that will just not display because the user is not prompted
        // to accept the cert. This is an android bug, and this is the accepted workaround
        event_info_webview.setWebViewClient(new WebViewClient() 
        {
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
                    startActivityForResult(intent, 0);
        		}
        		else if(type.equals("events"))
        		{
        			// Go to event of index
        			Intent intent = new Intent(view.getContext(), EventInfo.class);
                    startActivityForResult(intent, 0);
        		}
				
				return false;
        	 }
        });
        
        event_info_webview.getSettings().setJavaScriptEnabled(true);
        event_info_webview.loadUrl(getString(R.string.url_event_info_base) + event_id_to_load + "?hideActions=1");
        
        // Set title to event title
        TextView title = (TextView)findViewById(R.id.event_info_title);
        title.setText(event_title_to_display);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_event_info, menu);
        return true;
    }
    
    @Override
    public void onBackPressed() {
    	// Go to events
		Intent intent = new Intent(getBaseContext(), Events.class);
        startActivityForResult(intent, 0);
    }
}
