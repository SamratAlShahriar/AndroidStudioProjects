package com.google.www.watchtimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void timerClick(View view) {
        Intent intent = new Intent(this, InputForTimer.class);
        startActivity(intent);
    }

    public void stopwatchClick(View view) {
        Intent intent = new Intent(this, Stopwatch.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
