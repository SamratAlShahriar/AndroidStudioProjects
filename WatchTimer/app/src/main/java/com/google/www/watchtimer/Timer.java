package com.google.www.watchtimer;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Timer extends AppCompatActivity {


    public void clickReset(View v){
        Intent intent = new Intent(this, InputForTimer.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //
        final TextView showRemTime = (TextView) findViewById(R.id.showTime);

        //to get user input from intent
        Bundle bundle = getIntent().getExtras();
        long userInput = bundle.getLong("userInput");
        final long calculteUIinSec = userInput * 60 * 1000;


        new CountDownTimer(calculteUIinSec, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                long leftMilliSecond = millisUntilFinished;
                long leftSecond = leftMilliSecond / 1000;
                long leftMinute = leftSecond /60 ;

                showRemTime.setText(String.format("%02d", leftMinute) +":"+ String.format("%02d", leftSecond%60)+":"+String.format("%03d", leftMilliSecond%1000));
            }

            @Override
            public void onFinish() {
                showRemTime.setText("TIME0 UP");
            }
        }.start();

        /*
         final int[] num = {0};
         final Handler handler = new Handler();
         Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1);
                Log.i("as increment", Integer.toString(num[0]));  //I want to show 'num' as increment
                num[0]++;
            }
        };
        handler.post(runnable);*/
    }
}
