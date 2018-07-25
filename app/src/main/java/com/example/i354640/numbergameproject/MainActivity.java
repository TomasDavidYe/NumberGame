package com.example.i354640.numbergameproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playBtn = findViewById(R.id.playBtn);
        Button howToBtn = findViewById(R.id.howToBtn);
        Button exitBtn = findViewById(R.id.exitBtn);

        playBtn.setOnClickListener(view -> {
            Intent startGame = new Intent(getApplicationContext(), PlayActivity.class);
            startActivity(startGame);
        });

        howToBtn.setOnClickListener(view -> {
            Intent startHowTo = new Intent(getApplicationContext(), HowToActivity.class);
            startActivity(startHowTo);
        });

        exitBtn.setOnClickListener(view -> {
            finish();
        });

    }
}
