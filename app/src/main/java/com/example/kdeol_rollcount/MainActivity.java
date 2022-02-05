package com.example.kdeol_rollcount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button viewGames;
    Button addGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewGames = findViewById(R.id.view_games);
        addGame = findViewById(R.id.add_game);
    }

    /** Called when the user taps the Add Game button */
    public void addGame(View view) {
        // Simply change activities
        Intent addGameIntent = new Intent(this, AddGame.class);
        startActivity(addGameIntent);
    }

    public void viewGames(View view) {
        // Simply change activities
        Intent viewGamesIntent = new Intent(this, ViewGames.class);
        startActivity(viewGamesIntent);
    }
}