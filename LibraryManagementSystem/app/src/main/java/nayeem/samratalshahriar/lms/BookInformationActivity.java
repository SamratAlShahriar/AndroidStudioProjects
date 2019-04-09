package nayeem.samratalshahriar.lms;

import android.app.DatePickerDialog;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nayeem.samratalshahriar.lms.adapter.ViewPagerAdapter;
import nayeem.samratalshahriar.lms.fragments.BookBasicInfoFragment;
import nayeem.samratalshahriar.lms.fragments.BookOtherInfoFragment;

public class BookInformationActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    EditText et;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_information);

        //for navigation bar in tabs to show fragments
        viewPager = findViewById(R.id.viewpagerBookInfo);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabsBookInfo);
        tabLayout.setupWithViewPager(viewPager);

        Button btn = findViewById(R.id.btnSaveBook);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = findViewById(R.id.etAccessionNo);
                editText.setText("ok");
            }
        });

        Button btn2 = findViewById(R.id.btnResetBook);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    //setupviewPager method
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter customAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        customAdapter.addFragment(new BookBasicInfoFragment(), "Basic Info");
        customAdapter.addFragment(new BookOtherInfoFragment(), "Other Info");
        viewPager.setAdapter(customAdapter);
    }


    //onClick method : to set Date in Purchase Date by choosing Date
    public void choosePurchaseDate(View view) {
        et = findViewById(R.id.etPurchaseDate);
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setPurchaseDateTextView();
            }

        };
        //to show Dialog for choose date
        new DatePickerDialog(BookInformationActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    //to set date the textview with formated text.
    private void setPurchaseDateTextView() {
        String myFormat = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        et.setText(sdf.format(myCalendar.getTime()));
    }



}
