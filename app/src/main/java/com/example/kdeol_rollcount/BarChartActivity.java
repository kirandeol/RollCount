package com.example.kdeol_rollcount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {
    BarChart barChart;
    TextView minimum;
    TextView maximum;
    TextView average;
    ArrayList<String> singleScores;
    ArrayList<Integer> scoreCounts;
    ArrayList<String> xValues;
    ArrayList<BarEntry> yValues;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        minimum = findViewById(R.id.min);
        maximum = findViewById(R.id.max);
        average = findViewById(R.id.avg);
        barChart = findViewById(R.id.bar_chart);
        singleScores = new ArrayList<>();
        Intent intent = getIntent();
        String gameToPlay = intent.getStringExtra("gameToPlay");

        SharedPreferences rollPrefs = this.getSharedPreferences(
                "com.rollcount.app.rolls", Context.MODE_PRIVATE);
        SharedPreferences.Editor rollEditor = rollPrefs.edit();

        SharedPreferences dicePrefs = this.getSharedPreferences(
                "com.rollcount.app.dice", Context.MODE_PRIVATE);
        SharedPreferences.Editor diceEditor = dicePrefs.edit();

        SharedPreferences scorePrefs = this.getSharedPreferences(
                "com.rollcount.app.scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor scoreEditor = scorePrefs.edit();

        xValues = new ArrayList<>();
        Log.d("game to play", gameToPlay);
        int rolls = Integer.parseInt(rollPrefs.getString(gameToPlay, ""));
        int dice = Integer.parseInt(dicePrefs.getString(gameToPlay, ""));
        int min = rolls;
        int max = rolls * dice;

        for(int i = min; i <= max; i++) {
            xValues.add(String.valueOf(i));
        }
        for (String ter : xValues){
            Log.d("X-Value", ter);
        }

        singleScores = new ArrayList<>();
        String scores = scorePrefs.getString(gameToPlay, "");
        String[] scoresList = scores.split(" \\| ");
        int sum = 0;
        ArrayList<Integer> scoresArray = new ArrayList<>();
        for (int j = 1; j < scoresList.length; j++){
            scoresArray.add(Integer.parseInt(scoresList[j]));
            sum += Integer.parseInt(scoresList[j]);
        }
        Log.d("SUM: ", String.valueOf(sum));
        Log.d("Divisor: ", String.valueOf(scoresArray.size()));
        double temp4 = ((double) sum) / scoresArray.size();
        Log.d("Average: ", String.valueOf(temp4));

        minimum.setText("Min: " + String.valueOf(Collections.min(scoresArray)));
        maximum.setText("Max: " + String.valueOf(Collections.max(scoresArray)));
        average.setText("Average: " + String.valueOf(temp4));
        //max1.setText("Max: ");
        //avg.setText("Avg: ".concat(String.valueOf(average)));

        singleScores.addAll(Arrays.asList(scoresList));
        List<String> values = Arrays.asList(scoresList);

        scoreCounts = new ArrayList<>();
        for (String member : xValues){
            count = Collections.frequency(values, member);
            scoreCounts.add(count);
        }
        for (Integer tempe : scoreCounts){
            Log.d("Score Counts", String.valueOf(tempe));
        }

        yValues = new ArrayList<>();
        int temp = max - min;
        for(int g = 0; g <= temp; g++) {
            Log.d("--- TESTING g ", String.valueOf(g));
            yValues.add(new BarEntry(g + min, scoreCounts.get(g)));
        }
        for (BarEntry gr : yValues){
            Log.d("Score Counts", String.valueOf(gr));
        }
        BarDataSet barDataSet = new BarDataSet(yValues, "# of Rolls");
        BarData myData = new BarData(barDataSet);
        barChart.setDrawValueAboveBar(true);
        Description description = barChart.getDescription();
        description.setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        YAxis rightAxis = barChart.getAxisLeft();
        barChart.getAxisRight().setEnabled(false); // no right axis
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(13f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        rightAxis.setTextSize(13f);
        rightAxis.setDrawZeroLine(true);
        rightAxis.setZeroLineWidth(0.7f);
        rightAxis.setAxisMinimum(0f); // start at zero
        barChart.setAutoScaleMinMaxEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setData(myData);

    }
}