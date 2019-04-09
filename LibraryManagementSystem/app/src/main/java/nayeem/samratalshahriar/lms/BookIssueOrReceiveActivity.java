package nayeem.samratalshahriar.lms;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import nayeem.samratalshahriar.lms.adapter.ViewPagerAdapter;
import nayeem.samratalshahriar.lms.fragments.BookIssueFragment;
import nayeem.samratalshahriar.lms.fragments.BookReceiveFragment;
import nayeem.samratalshahriar.lms.fragments.StudentAddressInfoFragment;
import nayeem.samratalshahriar.lms.fragments.StudentBasicInfoFragment;
import nayeem.samratalshahriar.lms.fragments.StudentOtherInfoFragment;

public class BookIssueOrReceiveActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button btnIssue, btnReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_issue_or_receive);

        //initialize Button
        btnIssue = findViewById(R.id.btnIssueBook);
        btnReceive = findViewById(R.id.btnReceiveBook);

        //for navigation bar in tabs to show fragments
        viewPager = findViewById(R.id.viewpagerBookIssueReceive);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabsBookIssueOrReceive);
        tabLayout.setupWithViewPager(viewPager);

        //to set one button according to tab selection
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        btnIssue.setVisibility(View.VISIBLE);
                        btnReceive.setVisibility(View.GONE);
                        break;
                    case 1:
                        btnIssue.setVisibility(View.GONE);
                        btnReceive.setVisibility(View.VISIBLE);
                        break;
                    default:
                        btnIssue.setVisibility(View.GONE);
                        btnReceive.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //end of tab selection

        //to set button view accoding which fragment is showing
        if (viewPager.getCurrentItem() == 0) {
            btnIssue.setVisibility(View.VISIBLE);
            btnReceive.setVisibility(View.GONE);
        } else if (viewPager.getCurrentItem() == 1) {
            btnReceive.setVisibility(View.VISIBLE);
            btnIssue.setVisibility(View.GONE);
        }

    }

    //setupviewPager method
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter customAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        customAdapter.addFragment(new BookIssueFragment(), "Issue Book");
        customAdapter.addFragment(new BookReceiveFragment(), "Receive Book");
        viewPager.setAdapter(customAdapter);
    }
}
