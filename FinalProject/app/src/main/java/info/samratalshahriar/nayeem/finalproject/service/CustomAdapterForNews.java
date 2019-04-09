package info.samratalshahriar.nayeem.finalproject.service;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import info.samratalshahriar.nayeem.finalproject.R;
import info.samratalshahriar.nayeem.finalproject.ReadNewsActivity;
import info.samratalshahriar.nayeem.finalproject.domain.ListNewsViewHolder;

public class CustomAdapterForNews extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;


    public CustomAdapterForNews(Activity activity, ArrayList<HashMap<String, String>> data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ListNewsViewHolder holder  = null;
        if (view == null) {
            holder = new ListNewsViewHolder();
            view = LayoutInflater.from(activity).inflate(R.layout.list_news_row, viewGroup, false);
            holder.galleryImage = view.findViewById(R.id.galleryImage);
            holder.author = view.findViewById(R.id.author);
            holder.title = view.findViewById(R.id.title);
            holder.sdetails = view.findViewById(R.id.sdetails);
            holder.time = view.findViewById(R.id.time);
            view.setTag(holder);
        } else {
            holder = (ListNewsViewHolder) view.getTag();
        }
        holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);
        holder.time.setId(position);

        HashMap<String, String> news = new HashMap<String, String>();
        news = data.get(position);

        try {
            holder.author.setText(news.get(ReadNewsActivity.KEY_AUTHOR));
            holder.title.setText(news.get(ReadNewsActivity.KEY_TITLE));
            holder.time.setText(news.get(ReadNewsActivity.KEY_PUBLISHEDAT));
            holder.sdetails.setText(news.get(ReadNewsActivity.KEY_DESCRIPTION));

            if (news.get(ReadNewsActivity.KEY_URLTOIMAGE).toString().length() < 5) {
                holder.galleryImage.setVisibility(View.GONE);
            } else {
                Picasso.with(activity).load(news.get(ReadNewsActivity.KEY_URLTOIMAGE).toString()).resize(300, 200).into(holder.galleryImage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        view.setBackgroundColor(position % 2 == 0 ? Color.parseColor("#b4b4b4") : Color.parseColor("#9e9f9e"));
        return view;
    }
}
