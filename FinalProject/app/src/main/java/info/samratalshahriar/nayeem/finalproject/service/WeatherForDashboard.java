package info.samratalshahriar.nayeem.finalproject.service;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import info.samratalshahriar.nayeem.finalproject.R;

public class WeatherForDashboard extends AsyncTask<String, Void, String> {
    TextView tvScrollingWeather;
    ProgressBar progressBar;
    Context context;
    Activity activity;

    public WeatherForDashboard(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = activity.findViewById(R.id.loader);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected String doInBackground(String... urls) {
        URL url;
        HttpURLConnection urlConnection = null;
        String result = null;
        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3 * 1000);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();
            while (data != -1) {
                char current = (char) data;
                result += current;
                data = inputStreamReader.read();
            }
            //for cut "null" from the result as prefix
            String crappyPrfgix = "null";
            if (result.startsWith(crappyPrfgix)) {
                result = result.substring(crappyPrfgix.length(), result.length());
            }
            Log.i("XXX", "Connected");
            return result;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        tvScrollingWeather = activity.findViewById(R.id.scrollingWeather);
        try {
            JSONObject jsonObject = new JSONObject(result);
            String main = jsonObject.getString("main");
            JSONObject mainInfo = new JSONObject(main);

            String temp = mainInfo.getString("temp"); //get temperature

            //set Humidity
            String humadity = mainInfo.getString("humidity");

            //set wind
            String w = jsonObject.getString("wind");
            JSONObject windInfo = new JSONObject(w);
            String wind = windInfo.getString("speed");

            //set Pressure
            String pressure = mainInfo.getString("pressure");

            //showing info for the location in toast
            String city = jsonObject.getString("name"); //get city name
            String sys = jsonObject.getString("sys");
            JSONObject sysInfo = new JSONObject(sys);
            String country = sysInfo.getString("country"); //get country code

            //show sunrise and sunset
            String sunrise = sysInfo.getString("sunrise");
            String sunset = sysInfo.getString("sunset");
            Date dateOfSunrise = new Date(Long.parseLong(sunrise) * 1000);
            Date dateOfSunset = new Date(Long.parseLong(sunset) * 1000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mma");
            String srt = simpleDateFormat.format(dateOfSunrise);
            String sst = simpleDateFormat.format(dateOfSunset);

            //set condition
            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonPart = jsonArray.getJSONObject(0);
            String condition = jsonPart.getString("main");

            String fullStatement = "Temp : " + temp + "c"
                    + " || " +
                    "Condition : " + condition
                    + " || " +
                    "Humadity : " + humadity + "%"
                    + " || " +
                    "Wind : " + wind + " km/h"
                    + " || " +
                    "Pressure : " + pressure + " hPa"
                    + " || " +
                    "Sunrise : " + srt
                    + " || " +
                    "Sunset : " + sst
                    + " || " +
                    "Location : " + city + ", " + country;

            tvScrollingWeather.setSelected(true);
            tvScrollingWeather.setText(fullStatement);

            progressBar.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
