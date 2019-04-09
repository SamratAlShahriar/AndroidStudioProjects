package info.samratalshahriar.nayeem.finalproject.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import info.samratalshahriar.nayeem.finalproject.CheckWeatherActivity;
import info.samratalshahriar.nayeem.finalproject.EmployeeManagementActivity;
import info.samratalshahriar.nayeem.finalproject.LoginActivity;
import info.samratalshahriar.nayeem.finalproject.MainActivity;
import info.samratalshahriar.nayeem.finalproject.R;
import info.samratalshahriar.nayeem.finalproject.ReadNewsActivity;

public class CommonNavMenu {


    public static void menuOperation(int id, Context context, Activity activity) {
        switch (id) {
            case R.id.db: context.startActivity(new Intent(context, MainActivity.class));
            activity.finish();
            break;

            case R.id.employee_management: context.startActivity(new Intent(context, EmployeeManagementActivity.class));
            activity.finish();
            break;

            case R.id.news_paper: context.startActivity(new Intent(context, ReadNewsActivity.class));
            activity.finish();
            break;

            case R.id.check_weather: context.startActivity(new Intent(context, CheckWeatherActivity.class));
            break;

            case R.id.logout: context.startActivity(new Intent(context, LoginActivity.class));
            activity.finish();
            break;

            default:

        }
    }


}
