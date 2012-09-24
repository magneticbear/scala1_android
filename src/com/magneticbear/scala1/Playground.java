package com.magneticbear.scala1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class Playground extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);
        
        // Navigate to playground url
        WebView playground_webview = (WebView)findViewById(R.id.webview_playground);
        playground_webview.getSettings().setJavaScriptEnabled(true);
        playground_webview.loadUrl(getString(R.string.url_playground));
    }

    // Dont show the options menu
   @Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return false;
	}
    
    @Override
    public void onBackPressed() {
    	
    	UserData.mixpanel.track("PlaygroundBack", null);
    	
    	super.onBackPressed();
    }
}
