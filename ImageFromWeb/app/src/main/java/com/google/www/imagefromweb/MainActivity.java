package com.google.www.imagefromweb;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ImageView viewImage;
    Button getImageButton;
    TextView tv;

    public class ImageDownload extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected void onPreExecute() {
            tv = (TextView) findViewById(R.id.tv);
            tv.setTextSize(25f);
            tv.setText("Got Image From Internet");
       }

        @Override
        protected Bitmap doInBackground(String... urls) {
            URL url;
            HttpURLConnection urlConnection = null;
            String result = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewImage = (ImageView) findViewById(R.id.viewImage);
        getImageButton = (Button) findViewById(R.id.buttonGetImage);

        getImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog ringProgressDialog = ProgressDialog.show(MainActivity.this, "Please wait ...", "Downloading Image ...", true);
                ringProgressDialog.setCancelable(true);

                Handler hand = new Handler();
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ImageDownload downImage = new ImageDownload();
                            Bitmap image;
                            image = downImage.execute("http://lorempixel.com/400/200/").get();
                            //image = downImage.execute("https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/YuanEmperorAlbumGenghisPortrait.jpg/220px-YuanEmperorAlbumGenghisPortrait.jpg").get();
                            //image = downImage.execute("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Flag-map_of_Bangladesh2.svg/1200px-Flag-map_of_Bangladesh2.svg.png").get();
                            viewImage.setImageBitmap(image);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                hand.postDelayed(run, 200);

                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        ringProgressDialog.dismiss();
                        viewImage.animate().alpha(1f);
                        getImageButton.setText("Get Another Image");
                    }
                };
                handler.postDelayed(runnable, 800);
            }
        });
    }
}
