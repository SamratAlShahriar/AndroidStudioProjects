package info.samratalshahriar.nayeem.finalproject;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import info.samratalshahriar.nayeem.finalproject.domain.SignUpData;

public class SignupActivity extends AppCompatActivity {
    Button btnSignup, btnReset, btnLogin;
    EditText etName, etEmail, etUsername, etPassword;
    String name, email, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);
        btnReset = findViewById(R.id.btnReset);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);



        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpData signUpData = new SignUpData();
                boolean error = false;
                name = etName.getText().toString();
                email = etEmail.getText().toString();
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                //to check valid Full Name
                if (name.isEmpty()) {
                    etName.setError("Your name can't be empty.");
                    error = true;
                } else {
                    if (name.length() < 3) {
                        etName.setError("Name is too short.");
                        error = true;
                    } else {
                        signUpData.setFullName(name);
                    }
                }

                //to get valid email
                if (email.isEmpty()) {
                    etEmail.setError("Your email can't be empty.");
                    error = true;
                } else {
                    if (!email.contains("@")){
                        etEmail.setError("Please enter inputboxback valid email.");
                        error = true;
                    } else {
                        if (!email.contains(".")) {
                            etEmail.setError("Please enter inputboxback valid email");
                            error = true;
                        } else if (email.contains(" ")) {
                            etEmail.setError("No space allowed.");
                            error = true;
                        } else {
                            signUpData.setEmail(email);
                        }
                    }
                }

                //to input valid username
                if (username.isEmpty()){
                    etUsername.setError("Username field can't be empty.");
                    error = true;
                } else if (username.contains(" ")) {
                    etUsername.setError("No space allowed.");
                    error = true;
                } else {
                    signUpData.setUsername(username);
                }

                //take valid password
                if (password.isEmpty()){
                    etPassword.setError("Password field can't be empty");
                    error = true;
                } else {
                    if (password.length() < 6){
                        etPassword.setError("Password should be at least 6 character");
                        error = true;
                    } else {
                        signUpData.setPassword(password);
                    }
                }

                //input Data in firebase if no error found
                if (error){
                    Toast.makeText(SignupActivity.this, "Data can't save. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("User Data");
                    String key = databaseReference.push().getKey();
                    databaseReference.child(key).setValue(signUpData);
                    Toast.makeText(SignupActivity.this, "Data saved successfully.", Toast.LENGTH_SHORT).show();

                    //start login activity after 1s
                    new CountDownTimer(1500, 500) {
                        @Override
                        public void onTick(long l) {
                        }
                        @Override
                        public void onFinish() {
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }.start();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etName.setText("");
                etEmail.setText("");
                etUsername.setText("");
                etPassword.setText("");
            }
        });
    }
}
