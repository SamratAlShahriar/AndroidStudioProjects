package info.samratalshahriar.nayeem.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import info.samratalshahriar.nayeem.finalproject.service.AsynTaskForNews;
import info.samratalshahriar.nayeem.finalproject.service.CheckInternetConnecton;
import info.samratalshahriar.nayeem.finalproject.service.CommonNavMenu;

public class ReadNewsActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    ListView listNews;


    public static final String API_KEY = "e61fe9ac8d3b4bc7a85ddaa9506c08f4"; // news api
    public static String NEWS_SOURCE = "bbc-news";

    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_URLTOIMAGE = "urlToImage";
    public static final String KEY_PUBLISHEDAT = "publishedAt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);

        navigationView = findViewById(R.id.navViewNews);
        listNews = findViewById(R.id.listNews);

        //do as network status
        if (CheckInternetConnecton.isNetworkAvailable(getApplicationContext())) {
            AsynTaskForNews  newsTask = new AsynTaskForNews(ReadNewsActivity.this, ReadNewsActivity.this);
            newsTask.execute();
        } else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

        //to set Navigastion bar
        drawerLayout = findViewById(R.id.activity_read_news);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(ReadNewsActivity.this, ReadNewsWebviewActivity.class);
                intent.putExtra("url", AsynTaskForNews.dataList.get(+position).get(KEY_URL));
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                CommonNavMenu.menuOperation(item.getItemId(), ReadNewsActivity.this, ReadNewsActivity.this);
                return false;
            }
        });
    }

    //to enable navigation option menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        navigationView.bringToFront();
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
