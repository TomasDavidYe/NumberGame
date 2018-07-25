package com.example.i354640.numbergameproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class HowToActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        Toast.makeText(getApplicationContext(), "Here are going to be rules of the game", Toast.LENGTH_LONG).show();
    }
}
