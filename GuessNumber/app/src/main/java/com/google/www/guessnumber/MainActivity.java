package com.google.www.guessnumber;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int ranNo;
    private int totalguess = 0;

    public void guessButtonClick(View v) {
        try {
            TextView tv = (TextView) findViewById(R.id.showTotalGuess);

            Button guessButton = (Button) findViewById(R.id.buttonGuess);
            String butText = guessButton.getText().toString();

            EditText userGivenNumber = (EditText) findViewById(R.id.editTextInput);
            int userGuessNumber = Integer.parseInt(userGivenNumber.getText().toString());

            if (butText.equals("Guess")) {
                if (userGuessNumber > 0 && userGuessNumber <= 10) {
                    if (ranNo > userGuessNumber) {
                        Toast.makeText(MainActivity.this, "Sorry, Input Larger", Toast.LENGTH_SHORT).show();
                    } else if (ranNo < userGuessNumber) {
                        Toast.makeText(MainActivity.this, "Sorry, Input Smaller", Toast.LENGTH_SHORT).show();
                    } else if (ranNo == userGuessNumber) {
                        Toast.makeText(MainActivity.this, "WOW. Guess Right.", Toast.LENGTH_SHORT).show();
                        totalguess++;
                        guessButton.setText("Try Again");
                        genarateRandomNumber();
                    }
                    tv.setText(Integer.toString(totalguess));
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a number between 1 to 10.", Toast.LENGTH_SHORT).show();
                }
            } else {
                setContentView(R.layout.activity_main);
                TextView tv2 = (TextView) findViewById(R.id.showTotalGuess);
                tv2.setText(Integer.toString(totalguess));
            }
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Please enter a number between 1 to 10.", Toast.LENGTH_LONG).show();
        }
    }

    public int genarateRandomNumber() {
        Random random = new Random();
        ranNo = random.nextInt(10) + 1;
        return ranNo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random random = new Random();
        ranNo = random.nextInt(10) + 1;
    }
}
