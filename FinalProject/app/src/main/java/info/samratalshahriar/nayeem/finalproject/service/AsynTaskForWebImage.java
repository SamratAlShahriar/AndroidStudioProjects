package info.samratalshahriar.nayeem.finalproject.service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import info.samratalshahriar.nayeem.finalproject.R;

public class AsynTaskForWebImage extends AsyncTask<String, Void, Bitmap> {
    Activity activity;
    ProgressBar progressBar;

    public AsynTaskForWebImage(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        progressBar = activity.findViewById(R.id.viewImageProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
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

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        progressBar.setVisibility(View.GONE);
    }
}
