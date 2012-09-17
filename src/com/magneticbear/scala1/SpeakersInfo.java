package com.magneticbear.scala1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.SslErrorHandler;
import android.widget.TextView;
import android.widget.Toast;
import android.net.http.SslError;

public class SpeakersInfo extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
}
