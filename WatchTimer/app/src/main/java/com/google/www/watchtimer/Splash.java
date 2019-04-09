package com.google.www.watchtimer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //for splash screen
        new CountDownTimer(1000, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                ImageView backGr = (ImageView) findViewById(R.id.backgr);
                TextView tvTitle = (TextView) findViewById(R.id.textViewTitle);
                TextView tvAuthor = (TextView) findViewById(R.id.textViewAuthor);
                String[] colorBank = {"c75219", "fda852", "123221", "258520", "700120", "0f8552", "a54875", "254d87", "015501", "acf8ab", "7f7f7f"};
                Random random = new Random();
                int i = random.nextInt(colorBank.length - 2);
                backGr.setBackgroundColor(Color.parseColor("#" + colorBank[i]));
                tvTitle.setTextColor(Color.parseColor("#" + colorBank[i + 1]));
                tvAuthor.setTextColor(Color.parseColor("#"+colorBank[i+2]));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 100);
                //code for run every 1/10 sec
            }
        };
        handler.post(runnable);
    }
}
