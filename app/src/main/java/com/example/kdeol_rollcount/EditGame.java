package com.example.kdeol_rollcount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditGame extends AppCompatActivity {
    Button confirm;
    EditText newGameName;
    EditText newGameDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);
        confirm = findViewById(R.id.new_game_confirm);
        newGameName = findViewById(R.id.new_game_name);
        newGameDate = findViewById(R.id.new_game_date);
        Intent intent = getIntent();
        String gameToEdit = intent.getStringExtra("gameToPlay");

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
        SharedPreferences scorePrefs = this.getSharedPreferences(
                "com.rollcount.app.scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor scoreEditor = scorePrefs.edit();

        // Auto-fill of date, copied from user Pratik Butani at Stack Overflow: https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android/15698784
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        newGameDate.setText(date);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNameString = newGameName.getText().toString();
                String newDateString = newGameDate.getText().toString();

                String oldRollsString = rollPrefs.getString(gameToEdit, "");
                String oldDiceString = dicePrefs.getString(gameToEdit, "");
                String oldScoresString = scorePrefs.getString(gameToEdit, "");

                gameNameEditor.remove(gameToEdit);
                gameNameEditor.putString(newNameString, newNameString);
                gameNameEditor.apply();

                gameDateEditor.remove(gameToEdit);
                gameDateEditor.putString(newNameString, newDateString);
                gameDateEditor.apply();

                rollEditor.remove(gameToEdit);
                rollEditor.putString(newNameString, oldRollsString);
                rollEditor.apply();

                diceEditor.remove(gameToEdit);
                diceEditor.putString(newNameString, oldDiceString);
                diceEditor.apply();

                scoreEditor.remove(gameToEdit);
                scoreEditor.putString(newNameString, oldScoresString);
                scoreEditor.apply();

                Intent homeIntent = new Intent(EditGame.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });
    }
}