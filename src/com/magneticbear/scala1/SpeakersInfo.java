package com.magneticbear.scala1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.SslErrorHandler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.http.SslError;

public class SpeakersInfo extends Activity {

    Bundle save_speaker;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        // Create a bundle to save the web state
    	if(save_speaker == null)
    	{
    		save_speaker = new Bundle();
    	}
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speakers_info);
        
        // Setup debug data
        String speaker_title_to_display = "Speaker Info";
        int speaker_id_to_load = 1;
        
        // Setup real data
        if(getIntent().getExtras() != null) 
    	{
        	if(getIntent().getExtras().containsKey("index")) speaker_id_to_load = getIntent().getExtras().getInt("index");
    	}
        
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
        			intent.putExtra("index", Integer.parseInt(index));
                    startActivityForResult(intent, 0);
        		}
        		else if(type.equals("events"))
        		{
        			// Go to event of index
        			Intent intent = new Intent(view.getContext(), EventInfo.class);
        			intent.putExtra("index", Integer.parseInt(index));
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
        
        if(savedInstanceState != null)
        {
        	speakers_info_webview.restoreState(savedInstanceState);
        }
        
        // Check fav star start
        fav_proc(speaker_id_to_load);
        
        // Setup fav button
        final int closure_saved_id = speaker_id_to_load;
        findViewById(R.id.speaker_info_bar_star).setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				UserData.load_or_create();
				Struct_Speaker box = new Struct_Speaker("BOX", closure_saved_id);
		        if(UserData.is_fav(box))
		        {
		        	// already a fav make not fav
		        	UserData.remove_fav(box);
		        	fav_proc(closure_saved_id);
		        }
		        else
		        {
		        	// not a fav make a fav
		        	UserData.add_fav(box);
		        	fav_proc(closure_saved_id);
		        }
			}
		});
    }
    
    public void fav_proc(int id)
    {
    	 // Check if this is a fav already
        UserData.load_or_create();
        Struct_Speaker box = new Struct_Speaker("BOX", id);
        if(UserData.is_fav(box))
        {
        	((ImageView)findViewById(R.id.speaker_info_bar_star)).setImageResource(R.drawable.eventinfo_topbar_star_on);
        }
        else
        {
        	((ImageView)findViewById(R.id.speaker_info_bar_star)).setImageResource(R.drawable.eventinfo_topbar_star_off);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_speakers_info, menu);
        return true;
    }
    
    @Override
    protected void onPause() 
    {
    	((WebView)findViewById(R.id.webview_speaker_info)).saveState(save_speaker);
    	super.onPause();
    }
    
    @Override
    protected void onResume() 
    {
    	((WebView)findViewById(R.id.webview_speaker_info)).restoreState(save_speaker);
    	onCreate(save_speaker);
    	super.onResume();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) 
    {
    	((WebView)findViewById(R.id.webview_speaker_info)).saveState(outState);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) 
    {
    	((WebView)findViewById(R.id.webview_speaker_info)).restoreState(savedInstanceState);
    }
}
