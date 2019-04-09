package com.google.www.weathernow;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    public void clickDhaka(View view) {
        commonButton("Dhaka,bd");
    }
    public void clickLondon(View view) {
        commonButton("London,uk");

    }
    public void clickNewyork(View view) {
        commonButton("new%20york,us");

    }
    public void clickTokyo(View view) {
        commonButton("Tokyo,jp");
    }

    public void commonButton(String url) {
        DownloadTask dTask = new DownloadTask();
        try {
            dTask.execute("http://api.openweathermap.org/data/2.5/weather?q="+url+"&appid=b362558490d87dc98f981488ae3ac9c0&units=metric").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    class DownloadTask extends AsyncTask<String, Void, String> {
        TextView showTemp= findViewById(R.id.showTemp),
                 showCondition = findViewById(R.id.showCondition),
                 showHumidity =findViewById(R.id.showHumidity),
                 showWind = findViewById(R.id.showWind),
                 showPressure = findViewById(R.id.showPressure),
                 showSunriseSunset = findViewById(R.id.showSunriseSunset);

        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading, please wait.");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            setPreferenceURL(urls[0]);
            URL url;
            HttpURLConnection urlConnection = null;
            String result = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(3*1000);
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200){
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
                    if(result.startsWith(crappyPrfgix)){
                        result = result.substring(crappyPrfgix.length(), result.length());
                    }
                    Log.i("XXX", "Connected");
                    return result;
                } else {
                    urlConnection.disconnect();
                    result = sharedPreferences.getString("result", "");
                    Log.i("XXX", "Not Connected");
                    return result;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sharedPreferences.getString("result", "");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            setPreferenceResult(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String main = jsonObject.getString("main");
                JSONObject mainInfo = new JSONObject(main);

                String s = mainInfo.getString("temp"); //get temperature
                Spanned ss; //for make spanned text
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    ss = Html.fromHtml(s+"<sup><small>°c</small></sup>", Html.FROM_HTML_MODE_LEGACY); //make superscript temp icon
                } else {
                    ss = Html.fromHtml(s+"<sup><small>°C</small></sup>");
                }
                String index = ss.toString(); //to get index
                int startIndex = index.indexOf("°c"); //get iocn index start
                int stopIndex = index.length(); //get icon index stop
                SpannableString sss = new SpannableString(ss);
                sss.setSpan(new RelativeSizeSpan(1.1f), 0, startIndex, 0); //change text size before celcius
                sss.setSpan(new RelativeSizeSpan(0.6f), startIndex, stopIndex, 0); // change text size of celcius icon
                sss.setSpan(new ForegroundColorSpan(Color.parseColor("#cee685")), startIndex, stopIndex, 0); //change celcius icon color
                showTemp.setText(sss); //show temp with custom text

                //set Humidity
                String h = mainInfo.getString("humidity");
                showHumidity.setText(h+"%");

                //set wind
                String wind = jsonObject.getString("wind");
                JSONObject windInfo = new JSONObject(wind);
                String w = windInfo.getString("speed");
                showWind.setText(w+" km/h");

                //set Pressure
                String p = mainInfo.getString("pressure");
                showPressure.setText(p+" hPa");

                //showing info for the location in toast
                String city = jsonObject.getString("name"); //get city name
                String sys = jsonObject.getString("sys");
                JSONObject sysInfo = new JSONObject(sys);
                String country = sysInfo.getString("country"); //get country code
                Toast.makeText(MainActivity.this, city+", "+country, Toast.LENGTH_LONG).show();

                //show sunrise and sunset
                String sunrise = sysInfo.getString("sunrise");
                String sunset = sysInfo.getString("sunset");
                Date dateOfSunrise = new Date(Long.parseLong(sunrise)*1000);
                Date dateOfSunset = new Date(Long.parseLong(sunset)*1000);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mma");
                String srt = simpleDateFormat.format(dateOfSunrise);
                String sst = simpleDateFormat.format(dateOfSunset);
                showSunriseSunset.setText(srt+" / "+sst);

                //set condition
                JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject jsonPart = jsonArray.getJSONObject(0);
                    showCondition.setText(jsonPart.getString("main"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.google.www.weathernow", Context.MODE_PRIVATE);
        String getSharedPreferencesUrl = sharedPreferences.getString("url", "");

        DownloadTask downloadTask = new DownloadTask();
        try {
            downloadTask.execute(getSharedPreferencesUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchBar));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setQueryHint("Format: city_name, country_code(a2)");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String userInput = searchView.getQuery().toString();
                DownloadTask downloadTask =new DownloadTask();
                try {
                    downloadTask.execute("http://api.openweathermap.org/data/2.5/weather?q="+userInput+"&appid=b362558490d87dc98f981488ae3ac9c0&units=metric").get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void setPreferenceURL(String url) {
        sharedPreferences.edit().putString("url", url).apply();
    }

    public void setPreferenceResult(String result) {
        sharedPreferences.edit().putString("result", result).apply();
    }

}
