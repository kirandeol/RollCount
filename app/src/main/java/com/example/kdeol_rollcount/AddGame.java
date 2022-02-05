package com.example.kdeol_rollcount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddGame extends AppCompatActivity {
    Button confirm;
    EditText newGameName;
    EditText newGameDate;
    Spinner rollNumber;
    Spinner diceNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        confirm = findViewById(R.id.new_game_confirm);
        newGameName = findViewById(R.id.new_game_name);
        newGameDate = findViewById(R.id.new_game_date);
        rollNumber = findViewById(R.id.roll_number);
        diceNumber = findViewById(R.id.dice_number);

        SharedPreferences gameNamePrefs = this.getSharedPreferences(
                "com.rollcount.app.gamenames", Context.MODE_PRIVATE);
        SharedPreferences.Editor gameNameEditor = gameNamePrefs.edit();

        SharedPreferences gameDatePrefs = this.getSharedPreferences(
                "com.rollcount.app.gamedates", Context.MODE_PRIVATE);
        SharedPreferences.Editor gameDateEditor = gameDatePrefs.edit();

        SharedPreferences rollPrefs = this.getSharedPreferences(
                "com.rollcount.app.rolls", Context.MODE_PRIVATE);
        SharedPreferences.Editor rollEditor = rollPrefs.edit();

        SharedPreferences dicePrefs = this.getSharedPreferences(
                "com.rollcount.app.dice", Context.MODE_PRIVATE);
        SharedPreferences.Editor diceEditor = dicePrefs.edit();

        // Auto-fill of date, copied from user Pratik Butani at Stack Overflow: https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android/15698784
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        newGameDate.setText(date);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNameString = newGameName.getText().toString();
                String newDateString = newGameDate.getText().toString();
                String rollsString = rollNumber.getSelectedItem().toString();
                String diceString = diceNumber.getSelectedItem().toString();

                gameNameEditor.putString(newNameString, newNameString);
                gameNameEditor.apply();

                gameDateEditor.putString(newNameString, newDateString);
                gameDateEditor.apply();

                rollEditor.putString(newNameString, rollsString);
                rollEditor.apply();

                diceEditor.putString(newNameString, diceString);
                diceEditor.apply();

                Intent homeIntent = new Intent(AddGame.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });
    }
}