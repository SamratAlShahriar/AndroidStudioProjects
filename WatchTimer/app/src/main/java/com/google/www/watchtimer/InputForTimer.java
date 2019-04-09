package com.google.www.watchtimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InputForTimer extends AppCompatActivity {

    public void clickStart(View v) {
       try {
           EditText userInput = (EditText) findViewById(R.id.userInput);
           long getUserInput = Long.parseLong(String.valueOf(userInput.getText()));


           Intent intent = new Intent(this, Timer.class);
           intent.putExtra("userInput", getUserInput);
           startActivity(intent);
       } catch (Exception e){
           Toast.makeText(this, "Please input valid number", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_for_timer);
    }
}
