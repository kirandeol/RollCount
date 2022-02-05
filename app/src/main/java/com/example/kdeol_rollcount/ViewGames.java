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
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ViewGames extends AppCompatActivity {
    Button delete;
    Button edit;
    Button stats;
    Button play;
    ListView gamesList;
    ListView gamesInfoList;
    ArrayAdapter<String> gamesInfoAdapter;
    ArrayList<String> gamesDataList;
    ArrayAdapter<String> gamesAdapter;
    ArrayList<String> gameNamesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_games);
        Boolean[] deleteBool = {false};
        String[] currentGameArr = {""};
        Button delete = findViewById(R.id.delete);
        Button edit = findViewById(R.id.edit);
        Button stats = findViewById(R.id.view_stats);
        Button play = findViewById(R.id.play);
        gamesList = findViewById(R.id.games_list);

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

        /* WORKING CODE
        gameNamesList = new ArrayList<>();
        Map<String, ?> allEntries = gameNamePrefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String totalRolls = String.valueOf(scorePrefs.getString(entry.getKey(), "").split(" \\| ").length);
            String test = entry.getKey() + "Total Rolls: " + totalRolls;
            gameNamesList.add(entry.getKey());
        }

        gamesAdapter = new ArrayAdapter<>(this, R.layout.games_content, gameNamesList); // adapt for us to display the list
        gamesList.setAdapter(gamesAdapter); // displays list
        */

        ListView gamesList = (ListView) findViewById(R.id.games_list);

        HashMap<String, String> nameSubtitle = new HashMap<>();
        Map<String, ?> allEntries = gameNamePrefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String totalRolls = String.valueOf(scorePrefs.getString(entry.getKey(), "").split(" \\| ").length - 1);
            String test = gameDatePrefs.getString(entry.getKey(), "") + "  " + rollPrefs.getString(entry.getKey(), "") + "d" + dicePrefs.getString(entry.getKey(), "") + "  Total Rolls: " + totalRolls;
            nameSubtitle.put(entry.getKey(), test);
        }

        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.games_content,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.games_content_view, R.id.games_info_view});


        Iterator it = nameSubtitle.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }
        gamesList.setAdapter(adapter);

        gamesList.setClickable(true);
        gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = gamesList.getItemAtPosition(position);
                String currentGame = o.toString();
                currentGameArr[0] = currentGame.split("First Line=")[1].replace("}", "");
                Log.d("Current Game", currentGameArr[0]);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBool[0] = true;
                nameSubtitle.remove(currentGameArr[0]);
                gamesList.setAdapter(adapter); // displays list
                gameNameEditor.remove(currentGameArr[0]);
                gameNameEditor.apply();
                gameDateEditor.remove(currentGameArr[0]);
                gameDateEditor.apply();
                rollEditor.remove(currentGameArr[0]);
                rollEditor.apply();
                diceEditor.remove(currentGameArr[0]);
                diceEditor.apply();
                scoreEditor.remove(currentGameArr[0]);
                scoreEditor.apply();
                Log.d("Current Game to Delete", currentGameArr[0]);
                deleteBool[0] = false;
                Intent backHomeIntent = new Intent(ViewGames.this, MainActivity.class);
                startActivity(backHomeIntent);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = gameNamePrefs.getString(currentGameArr[0], "");
                Intent playGameIntent = new Intent(ViewGames.this, PlayGame.class);
                String gameToPlay = "gameToPlay";
                playGameIntent.putExtra(gameToPlay, name);
                startActivity(playGameIntent);
            }
        });

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = gameNamePrefs.getString(currentGameArr[0], "");
                Intent statsIntent = new Intent(ViewGames.this, BarChartActivity.class);
                String gameToPlay = "gameToPlay";
                statsIntent.putExtra(gameToPlay, name);
                startActivity(statsIntent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = gameNamePrefs.getString(currentGameArr[0], "");
                Intent statsIntent = new Intent(ViewGames.this, EditGame.class);
                String gameToPlay = "gameToPlay";
                statsIntent.putExtra(gameToPlay, name);
                startActivity(statsIntent);
            }
        });

    }

}