package info.samratalshahriar.nayeem.finalproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import info.samratalshahriar.nayeem.finalproject.domain.EmployeeInfo;
import info.samratalshahriar.nayeem.finalproject.service.CheckInternetConnecton;
import info.samratalshahriar.nayeem.finalproject.service.CommonNavMenu;
import info.samratalshahriar.nayeem.finalproject.service.CustomAdapterForEmployeeList;

public class ManageEmployee extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<EmployeeInfo> empArrayList;
    CustomAdapterForEmployeeList customAdapterForEmployeeList;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employee);

        progressDialog = new ProgressDialog(ManageEmployee.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        //progressDialog.setCancelable(false);
        progressDialog.show();


        listView = findViewById(R.id.showEmployeeList);
        empArrayList =new ArrayList<>();
        customAdapterForEmployeeList = new CustomAdapterForEmployeeList(empArrayList, ManageEmployee.this);

        //empArrayAdapter = new ArrayAdapter<EmployeeInfo>(ManageEmployee.this, android.R.layout.simple_list_item_1, empArrayList);
        listView.setAdapter(customAdapterForEmployeeList);

        getDataFromFirebase();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

        //to set Navigation
        navigationView = findViewById(R.id.navViewMenu);
        drawerLayout = findViewById(R.id.activity_manage_emp);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //to perform something onclick navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                CommonNavMenu.menuOperation(item.getItemId(), ManageEmployee.this, ManageEmployee.this);
                return false;
            }
        });

        //to perform on click on listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManageEmployee.this);
                alertDialog.setTitle("Employee Information").setMessage(empArrayList.get(position).getEmpData());
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.setCancelable(true);
                    }
                });
                alertDialog.show();

            }
        });

        /*
        //to perform in long press on listview
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final String phone = empArrayList.get(position).getPhone();

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManageEmployee.this);
                alertDialog.setTitle("Employee Information").setMessage(empArrayList.get(position).getEmpData());
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.setCancelable(true);
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+phone));
                        startActivity(intent);
                    }
                });
                alertDialog.setIcon(R.drawable.aupic);
                alertDialog.show();
                return false;
            }
        });
*/
        //for context menu
        registerForContextMenu(listView);
    }

    //for Context menu of create app
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.showEmployeeList) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("Select An Option");
            String[] array = {"Call", "Email", "Delete Employee"};
            for (int i = 0; i<array.length; i++) {
                menu.add(Menu.NONE, i, i, array[i]);
            }
        }
    }

    //on item selected on context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemId = item.getItemId();
        String selectedUserPhoneNo = empArrayList.get(info.position).getPhone();
        String selectedUserEmail = empArrayList.get(info.position).getEmail();

        switch (menuItemId) {
            case 0:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+selectedUserPhoneNo));
                startActivity(intent);
                return true;

            case 1:
                Intent intent2 = new Intent(Intent.ACTION_SENDTO);
                intent2.setData(Uri.parse("mailto:"+selectedUserEmail));
                startActivity(intent2);
                return true;

            case 2:
                deleteEmployeeByPhoneNo(selectedUserPhoneNo);
                return true;

            default: return super.onContextItemSelected(item);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar_for_mng_emp, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchBar));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setQueryHint("Search Employee By Phone No.");
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() < 11 || query.length() > 11) {
                    Toast.makeText(ManageEmployee.this, "Please input valid Phone no.", Toast.LENGTH_SHORT).show();
                    getDataFromFirebase();
                } else {
                    getDataFromFirebaseByPhoneNo(query);
                    Log.i("name", query+query.length());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }


    public void getDataFromFirebase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Employee Information");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                empArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue().toString();
                    String department = snapshot.child("department").getValue().toString();
                    String designation = snapshot.child("designation").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String phone = snapshot.child("phone").getValue().toString();
                    String salary = snapshot.child("salary").getValue().toString();

                    EmployeeInfo employeeInfo = new EmployeeInfo(name, department, designation, email, phone, salary);
                    empArrayList.add(employeeInfo);
                    customAdapterForEmployeeList.notifyDataSetChanged();
                }

                if (CheckInternetConnecton.isNetworkAvailable(ManageEmployee.this)){
                    new CountDownTimer(500, 100) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            progressDialog.dismiss();
                        }
                    }.start();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ManageEmployee.this, "No data loaded.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //to get user by Phone no by search
    public void getDataFromFirebaseByPhoneNo(String phoneNo) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = firebaseDatabase.getReference("Employee Information").orderByChild("phone").equalTo(phoneNo);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                empArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue().toString();
                    String department = snapshot.child("department").getValue().toString();
                    String designation = snapshot.child("designation").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String phone = snapshot.child("phone").getValue().toString();
                    String salary = snapshot.child("salary").getValue().toString();

                    EmployeeInfo employeeInfo = new EmployeeInfo(name, department, designation, email, phone, salary);
                    empArrayList.add(employeeInfo);
                    customAdapterForEmployeeList.notifyDataSetChanged();
                }

                if (CheckInternetConnecton.isNetworkAvailable(ManageEmployee.this)){
                    new CountDownTimer(500, 100) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            progressDialog.dismiss();
                        }
                    }.start();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ManageEmployee.this, "No data loaded.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //delete user by Phone no
    public void deleteEmployeeByPhoneNo(String phoneNo) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = firebaseDatabase.getReference("Employee Information").orderByChild("phone").equalTo(phoneNo);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                empArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                    customAdapterForEmployeeList.notifyDataSetChanged();
                }

                if (CheckInternetConnecton.isNetworkAvailable(ManageEmployee.this)){
                    new CountDownTimer(500, 100) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            progressDialog.dismiss();
                        }
                    }.start();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ManageEmployee.this, "No data loaded.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
