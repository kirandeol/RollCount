package com.example.kdeol_rollcount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class PlayGame extends AppCompatActivity {
    GridView numbersGrid;
    ArrayAdapter<String> numbersAdapter;
    ArrayList<String> numbersList;
    Button undo;
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        numbersGrid = findViewById(R.id.simpleGridView);
        undo = findViewById(R.id.undo_roll);
        confirm = findViewById(R.id.confirm_session);
        Intent intent = getIntent();
        String gameToPlay = intent.getStringExtra("gameToPlay");

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

        numbersList = new ArrayList<>();
        Log.d("game to play", gameToPlay);
        int rolls = Integer.parseInt(rollPrefs.getString(gameToPlay, ""));
        int dice = Integer.parseInt(dicePrefs.getString(gameToPlay, ""));
        int min = rolls;
        int max = rolls * dice;

        for(int i = min; i <= max; i++) {
            numbersList.add(String.valueOf(i));
        }

        numbersAdapter = new ArrayAdapter<>(this, R.layout.grid_game, numbersList);
        numbersGrid.setAdapter(numbersAdapter); // displays list

        numbersGrid.setClickable(true);
        numbersGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String newScore;
            String oldScore;
            Object o;
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                o = numbersGrid.getItemAtPosition(position);
                newScore = o.toString();
                oldScore = scorePrefs.getString(gameToPlay, "");
                oldScore = oldScore.concat(" | " + newScore);
                scoreEditor.putString(gameToPlay, oldScore);
                scoreEditor.apply();
                Log.d("Current number", scorePrefs.getString(gameToPlay, ""));
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldScore = scorePrefs.getString(gameToPlay, "");
                String[] newScore = oldScore.split(" \\| ");
                String[] slice = Arrays.copyOfRange(newScore, 1, newScore.length - 1);
                String temp = "";
                for (String num: slice) {
                    temp = temp.concat(" | ".concat(num));
                }
                scoreEditor.remove(gameToPlay).apply();
                scoreEditor.putString(gameToPlay, temp).apply();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(PlayGame.this, ViewGames.class);
                startActivity(backIntent);
            }
        });

    }
}