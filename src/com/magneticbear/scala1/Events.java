package com.magneticbear.scala1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Events extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_events, menu);
        return true;
    }
}
