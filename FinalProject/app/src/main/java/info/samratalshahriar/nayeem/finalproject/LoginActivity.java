package info.samratalshahriar.nayeem.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import info.samratalshahriar.nayeem.finalproject.service.CheckInternetConnecton;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin, btnSignup, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);
        btnReset = findViewById(R.id.btnReset);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;

                if (etUsername.getText().toString().isEmpty()) {
                    etUsername.setError("Username can't be null");
                    error = true;
                }
                if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Password can't be empty.");
                    error = true;
                }
                if (!error) {
                    if (CheckInternetConnecton.isNetworkAvailable(LoginActivity.this)) {
                        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setTitle("Please wait. .. ...");
                        progressDialog.setMessage("Log in");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                        Query query = databaseReference.child("User Data").orderByChild("username").equalTo(etUsername.getText().toString());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                        if (dataSnap.child("username").getValue().toString().toLowerCase().equals(etUsername.getText().toString().toLowerCase())) {
                                            if (dataSnap.child("password").getValue().equals(etPassword.getText().toString())) {
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                progressDialog.dismiss();
                                                finish();
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "No user information found. Sign up for new account.", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "No Internet Connection.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUsername.setText("");
                etPassword.setText("");
            }
        });
    }

}
