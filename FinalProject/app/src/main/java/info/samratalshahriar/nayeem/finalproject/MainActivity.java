package info.samratalshahriar.nayeem.finalproject;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import info.samratalshahriar.nayeem.finalproject.service.CheckInternetConnecton;
import info.samratalshahriar.nayeem.finalproject.service.CommonNavMenu;
import info.samratalshahriar.nayeem.finalproject.service.CustomSwipeAdapterForWebImage;
import info.samratalshahriar.nayeem.finalproject.service.WeatherForDashboard;

public class MainActivity extends AppCompatActivity {
    private final String API_KEY = "ddc939cb25654341245c101819d612dc";
    private NavigationView navigationView;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat1, simpleDateFormat2;
    private String nowTime;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private TextView tvShowTime;

    private ViewPager viewPager;
    private CustomSwipeAdapterForWebImage customSwipeAdapterForWebImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dl = findViewById(R.id.activity_main);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.open, R.string.close);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navViewMenu);

        tvShowTime = findViewById(R.id.tvShowTime);
        //to show time and update in every 1s
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                calendar = Calendar.getInstance();
                simpleDateFormat1 = new SimpleDateFormat("hh:mm:ss a");
                simpleDateFormat2 = new SimpleDateFormat("dd-MMM-yyyy");
                nowTime = simpleDateFormat1.format(calendar.getTime());
                nowTime += "\n";
                nowTime += simpleDateFormat2.format(calendar.getTime());
                tvShowTime.setText(nowTime);
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);


            if (CheckInternetConnecton.isNetworkAvailable(this)) {
                //to show weather in marquee
                new CountDownTimer(500, 100) { //to delay some time
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        WeatherForDashboard weatherForDashboard = new WeatherForDashboard(MainActivity.this, MainActivity.this);
                        try {
                            weatherForDashboard.execute("http://api.openweathermap.org/data/2.5/weather?q=" + "dhaka,bd" + "&appid=" + API_KEY + "&units=metric").get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.start();

            } else {
                TextView scrollingText = findViewById(R.id.scrollingWeather);
                scrollingText.setSelected(true);
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                CommonNavMenu.menuOperation(item.getItemId(), MainActivity.this, MainActivity.this);
                return false;
            }
        });


        if (CheckInternetConnecton.isNetworkAvailable(this)) {
            //to show slide image from web
            viewPager = findViewById(R.id.viewImageFromWeb);
            customSwipeAdapterForWebImage = new CustomSwipeAdapterForWebImage(this, this);
            viewPager.setAdapter(customSwipeAdapterForWebImage);
        }
    }

    //to enable navigation option menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        navigationView.bringToFront();
        if (abdt.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
