package info.samratalshahriar.nayeem.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    TextView textView;
    ImageView imageView;
    boolean blink = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //initialize item
        textView = findViewById(R.id.splashText);
        imageView = findViewById(R.id.splashImage);


        imageView.animate().alpha(1).setDuration(3000);

        new CountDownTimer(3000, 100) {
            @Override
            public void onTick(long l) {
                if (blink) {
                    textView.setTextColor(Color.BLACK);
                    blink = false;
                } else {
                    textView.setTextColor(Color.WHITE);
                    blink = true;
                }
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
