package info.samratalshahriar.nayeem.finalproject.service;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import info.samratalshahriar.nayeem.finalproject.R;
import info.samratalshahriar.nayeem.finalproject.ReadNewsActivity;

public class AsynTaskForNews extends AsyncTask<String, Void, String> {
    Activity activity;
    Context context;
    ListView listNews;
    ProgressBar loader;
    public static ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();



    public AsynTaskForNews(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listNews = activity.findViewById(R.id.listNews);
        loader = activity.findViewById(R.id.loader);
        listNews.setEmptyView(loader);
    }

    @Override
    protected String doInBackground(String... args) {
        String xml = "";

        xml = CheckInternetConnecton.excuteGet("https://newsapi.org/v1/articles?source="+ReadNewsActivity.NEWS_SOURCE+"&sortBy=top&apiKey="+ReadNewsActivity.API_KEY);
        return xml;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result.length() > 10) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(ReadNewsActivity.KEY_AUTHOR, jsonObject.optString(ReadNewsActivity.KEY_AUTHOR).toString());
                    map.put(ReadNewsActivity.KEY_TITLE, jsonObject.optString(ReadNewsActivity.KEY_TITLE).toString());
                    map.put(ReadNewsActivity.KEY_DESCRIPTION, jsonObject.optString(ReadNewsActivity.KEY_DESCRIPTION).toString());
                    map.put(ReadNewsActivity.KEY_URL, jsonObject.optString(ReadNewsActivity.KEY_URL).toString());
                    map.put(ReadNewsActivity.KEY_URLTOIMAGE, jsonObject.optString(ReadNewsActivity.KEY_URLTOIMAGE).toString());
                    map.put(ReadNewsActivity.KEY_PUBLISHEDAT, jsonObject.optString(ReadNewsActivity.KEY_PUBLISHEDAT).toString());
                    dataList.add(map);

                }
            } catch (JSONException e) {
                Toast.makeText(context, "Unexpected erro!", Toast.LENGTH_SHORT).show();
            }

            CustomAdapterForNews adapter = new CustomAdapterForNews(activity, dataList);
            listNews.setAdapter(adapter);
        } else {
            Toast.makeText(context, "No news found.", Toast.LENGTH_SHORT).show();
        }
    }
}
