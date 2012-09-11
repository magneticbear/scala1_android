package com.magneticbear.scala1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Favourites extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_favourites, menu);
        return true;
    }
}
