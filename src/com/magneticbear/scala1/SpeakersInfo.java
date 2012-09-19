package com.magneticbear.scala1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.SslErrorHandler;
import android.widget.TextView;
import android.widget.Toast;
import android.net.http.SslError;

public class SpeakersInfo extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speakers_info);
        
        // Setup debug data
        String speaker_title_to_display = "Speaker Info";
        int speaker_id_to_load = 1;
        
        // Navigate to speaker url
        WebView speakers_info_webview = (WebView)findViewById(R.id.webview_speaker_info);
        
        // This is a bugfix for HTTPS pages, that will just not display because the user is not prompted
        // to accept the cert. This is an android bug, and this is the accepted workaround
        speakers_info_webview.setWebViewClient(new WebViewClient() 
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
        
        speakers_info_webview.getSettings().setJavaScriptEnabled(true);
        speakers_info_webview.loadUrl(getString(R.string.url_speakers_info_base) + speaker_id_to_load);
        
        // Set title to speaker name title
        TextView title = (TextView)findViewById(R.id.speaker_info_title);
        title.setText(speaker_title_to_display);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_speakers_info, menu);
        return true;
    }
    
    @Override
    public void onBackPressed() {
    	// Go to speakers
		Intent intent = new Intent(getBaseContext(), Speakers.class);
        startActivityForResult(intent, 0);
    }
}
