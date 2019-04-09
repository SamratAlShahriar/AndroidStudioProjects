package nayeem.samratalshahriar.lms;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import nayeem.samratalshahriar.lms.adapter.ViewPagerAdapter;
import nayeem.samratalshahriar.lms.fragments.StudentAddressInfoFragment;
import nayeem.samratalshahriar.lms.fragments.StudentBasicInfoFragment;
import nayeem.samratalshahriar.lms.fragments.StudentOtherInfoFragment;

public class StudentInformationActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);

        //for navigation bar in tabs to show fragments
        viewPager = findViewById(R.id.viewpagerStudentInfo);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabsStudentInfo);
        tabLayout.setupWithViewPager(viewPager);
    }

    //setupviewPager method
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter customAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        customAdapter.addFragment(new StudentBasicInfoFragment(), "Basic Info");
        customAdapter.addFragment(new StudentAddressInfoFragment(), "Address Info");
        customAdapter.addFragment(new StudentOtherInfoFragment(), "Other Info");
        viewPager.setAdapter(customAdapter);
    }
}
