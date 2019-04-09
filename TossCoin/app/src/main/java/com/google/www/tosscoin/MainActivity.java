package com.google.www.tosscoin;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public void clickHead(View v){
        TextView textHead=(TextView) findViewById(R.id.textHeader);
        TextView showResult = (TextView) findViewById(R.id.viewResult);
        Button headButt = (Button) findViewById(R.id.buttonHead);
        Button tailButt = (Button) findViewById(R.id.buttonTail);
        final Button tossAg= (Button) findViewById(R.id.tossAgainButton);
        ImageView backImg= (ImageView) findViewById(R.id.imageView);
        final ImageView backImgChange= (ImageView) findViewById(R.id.backImageChange);
        TextView authView = (TextView) findViewById(R.id.autorView);

        headButt.animate().translationX(200f).rotation(3600f).setDuration(5000);
        headButt.animate().alpha(0f);
        headButt.setEnabled(false);
        tailButt.animate().alpha(0f);
        tailButt.setEnabled(false);
        textHead.animate().alpha(0f);
        backImg.animate().alpha(0).setDuration(4000);
        showResult.animate().alpha(1f).rotation(10800f).scaleX(2.8f).scaleY(4f).translationY(-200f).setDuration(5000);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                tossAg.animate().alpha(1f).setDuration(1000);
                tossAg.setEnabled(true);
            }
        }, 5000);


        authView.animate().alpha(0).setDuration(0);
        authView.setClickable(false);

    try{
        Random ranNo = new Random();
        int getRanNo = ranNo.nextInt(3) + 1;

        int head = 1;
        int tail = 2;

         if(getRanNo == head){
             headButt.animate().rotation(3600).setDuration(5000);
             showResult.setText("HURRAY!\nIt's Head. You won the toss.");

             backImgChange.setImageResource(R.drawable.win);
         } else if (getRanNo == tail) {
             headButt.animate().rotation(3600).setDuration(5000);
             showResult.setText("SORRY!\nIt's Tail. You lost the toss.");

             backImgChange.setImageResource(R.drawable.lost);
         } else {
             headButt.animate().rotation(3600).setDuration(5000);
             showResult.setText("Oops!\n Both of you lost.\nTry again.");

             backImgChange.setImageResource(R.drawable.nores);
         }
    }
    catch(Exception e){
            showResult.setText("Something went wrong.");
    }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                backImgChange.animate().alpha(0.7f).translationY(50f).setDuration(1000);
            }
        }, 4000);
    }


    public void clickTail(View v) {
        TextView textHead=(TextView) findViewById(R.id.textHeader);
        TextView showResult = (TextView) findViewById(R.id.viewResult);
        Button headButt = (Button) findViewById(R.id.buttonHead);
        Button tailButt = (Button) findViewById(R.id.buttonTail);
        final Button tossAg= (Button) findViewById(R.id.tossAgainButton);
        ImageView backImg= (ImageView) findViewById(R.id.imageView);
        final ImageView backImgChange= (ImageView) findViewById(R.id.backImageChange);
        TextView authView = (TextView) findViewById(R.id.autorView);

        tailButt.animate().alpha(0f);
        tailButt.setEnabled(false);
        headButt.animate().alpha(0f);
        headButt.setEnabled(false);
        textHead.animate().alpha(0f);
        backImg.animate().alpha(0).setDuration(4000);
        showResult.animate().alpha(1f).rotation(10800f).scaleX(2.8f).scaleY(4f).translationY(-200f).setDuration(5000);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                tossAg.animate().alpha(1f).setDuration(1000);
                tossAg.setEnabled(true);
            }
        }, 5000);


        authView.animate().alpha(0).setDuration(0);
        authView.setClickable(false);


        try {
            Random ranNo = new Random();
            int getRanNo = ranNo.nextInt(3) + 1;

            int tail = 1;
            int head = 2;

            if (getRanNo == tail) {
                tailButt.animate().rotation(-3600).translationX(-200f).setDuration(5000);
                showResult.setText("HURRAY!\nIt's Tail. You won the toss.");

                backImgChange.setImageResource(R.drawable.win);
            } else if (getRanNo == head) {
                tailButt.animate().rotation(-3600).translationX(-200f).setDuration(5000);
                showResult.setText("SORRY!\nIt's Head. You lost the toss.");

                backImgChange.setImageResource(R.drawable.lost);
            } else {
                tailButt.animate().rotation(-3600).translationX(-200f).setDuration(5000);
                showResult.setText("Oops!\n Both of you lost.\nTry again.");

                backImgChange.setImageResource(R.drawable.nores);
            }
        } catch (Exception e) {
            showResult.setText("Something went wrong.");
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                backImgChange.animate().alpha(0.7f).translationY(50f).setDuration(1000);
            }
        }, 4000);

    }


    public void authorClick(View v){
        TextView showResult = (TextView) findViewById(R.id.viewResult);
        try {
            TextView authView = (TextView) findViewById(R.id.autorView);
            ImageView backImg = (ImageView) findViewById(R.id.imageView);
            TextView textHead = (TextView) findViewById(R.id.textHeader);
            Button headButt = (Button) findViewById(R.id.buttonHead);
            Button tailButt = (Button) findViewById(R.id.buttonTail);
            Button tossAg= (Button) findViewById(R.id.tossAgainButton);

            final ImageView backImgChange = (ImageView) findViewById(R.id.backImageForAuth);

            backImg.animate().alpha(0).setDuration(1000);



            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    backImgChange.setImageResource(R.drawable.authpic);
                    backImgChange.animate().alpha(1f).setDuration(1000);
                }
            }, 850);


            textHead.animate().alpha(0f);
            authView.animate().alpha(0f);
            headButt.animate().alpha(0f);
            headButt.setEnabled(false);
            tailButt.animate().alpha(0f);
            tailButt.setEnabled(false);
            tossAg.setText("Back 2 Toss");
            tossAg.animate().alpha(1f).translationY(500f).setDuration(1);
            tossAg.setEnabled(true);

        }catch (Exception e){
            showResult.setText("Something went wrong.");
        }

    }


    public void tossAgain(View v){

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
