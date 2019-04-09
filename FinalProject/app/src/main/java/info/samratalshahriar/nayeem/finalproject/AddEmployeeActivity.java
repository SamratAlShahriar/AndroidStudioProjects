package info.samratalshahriar.nayeem.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import info.samratalshahriar.nayeem.finalproject.domain.EmployeeInfo;
import info.samratalshahriar.nayeem.finalproject.service.CommonNavMenu;

public class AddEmployeeActivity extends AppCompatActivity {
    private Button btnSave, btnReset;
    private EditText etName, etDept, etDesig, etEmail, etPhone, etSalary;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        //initializing by id
        etName = findViewById(R.id.etAddEmpName);
        etDept = findViewById(R.id.etDepartment);
        etDesig = findViewById(R.id.etDesignation);
        etEmail = findViewById(R.id.etEmpEmail);
        etPhone = findViewById(R.id.etEmpPhone);
        etSalary = findViewById(R.id.etEmpSalary);


        btnSave = findViewById(R.id.btnSaveEmp);
        btnReset = findViewById(R.id.btnResetEmp);

        //to perform btn Save button operation
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeInfo empInfo = new EmployeeInfo();
                boolean error = false;

                String name = etName.getText().toString();
                String department = etDept.getText().toString();
                String designation = etDesig.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String salary = etSalary.getText().toString();

                //to valid name
                if (name.isEmpty()) {
                    etName.setError("Name can't be empty.");
                    error = true;
                } else {
                    if (name.length() <= 3) {
                        etName.setError("Name is too short.");
                        error = true;
                    } else {
                        empInfo.setName(name);
                    }
                }

                //to valid department
                if (department.isEmpty()) {
                    etDept.setError("Department name can't be empty");
                    error = true;
                } else {
                    empInfo.setDepartment(department);
                }

                //to valid designation
                if (designation.isEmpty()) {
                    etDesig.setError("Designation can't be empty.");
                    error = true;
                } else {
                    empInfo.setDesignation(designation);
                }

                //to valid email
                if (email.isEmpty()) {
                    etEmail.setError("email can't be empty");
                    error = true;
                } else {
                    if (!email.contains("@")) {
                        etEmail.setError("Enter a valid email");
                        error = true;
                    } else if (!email.contains(".")) {
                        etEmail.setError("Enter a valid email");
                        error = true;
                    } else if (email.contains(" ")) {
                        etEmail.setError("No space allowed.");
                        error = true;
                    } else {
                        empInfo.setEmail(email);
                    }
                }

                //to valid phone
                if (phone.isEmpty()) {
                    etPhone.setError("Phone no can't be empty.");
                    error = true;
                } else {
                    if (phone.length() == 11) {
                        empInfo.setPhone(phone);
                    } else {
                        etPhone.setError("Phone no. should be 11 digit.");
                        error = true;
                    }
                }

                //to set valid salary
                if (salary.isEmpty()) {
                    etSalary.setError("Salary can't be empty");
                    error = true;
                } else {
                    empInfo.setSalary(salary);
                }

                //input Data in firebase if no error found
                if (error){
                    Toast.makeText(AddEmployeeActivity.this, "Data can't save. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("Employee Information");
                    String key = databaseReference.push().getKey();
                    databaseReference.child(key).setValue(empInfo);
                    Toast.makeText(AddEmployeeActivity.this, "Data saved successfully.", Toast.LENGTH_SHORT).show();

                    //start login activity after 1s
                    new CountDownTimer(1500, 500) {
                        @Override
                        public void onTick(long l) {
                        }
                        @Override
                        public void onFinish() {
                            Intent intent = new Intent(AddEmployeeActivity.this, EmployeeManagementActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }.start();
                }

            }
        });

        //tos perform Reset button
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etName.setText("");
                etDept.setText("");
                etDesig.setText("");
                etEmail.setText("");
                etPhone.setText("");
                etSalary.setText("");
            }
        });

        //to set Navigation
        navigationView = findViewById(R.id.navViewMenu);
        drawerLayout = findViewById(R.id.activity_add_emp);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //to perform something onclick navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                CommonNavMenu.menuOperation(item.getItemId(), AddEmployeeActivity.this, AddEmployeeActivity.this);
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
