package info.samratalshahriar.nayeem.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import info.samratalshahriar.nayeem.finalproject.service.CommonNavMenu;

public class EmployeeManagementActivity extends AppCompatActivity {
    private Button addNewEmp, manageExEmp;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_management);

        addNewEmp = findViewById(R.id.btnAddNewEmp);
        manageExEmp = findViewById(R.id.btnManageExistingEmp);

        addNewEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeManagementActivity.this, AddEmployeeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        manageExEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeManagementActivity.this, ManageEmployee.class);
                startActivity(intent);
                finish();
            }
        });

        //to set Navigation
        navigationView = findViewById(R.id.navViewMenu);
        drawerLayout = findViewById(R.id.activity_emp_manage);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //to perform something onclick navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                CommonNavMenu.menuOperation(item.getItemId(), EmployeeManagementActivity.this, EmployeeManagementActivity.this);
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
