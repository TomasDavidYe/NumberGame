package com.example.i354640.numbergameproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        int[] nums = {1, 2, 3, 4,5};
        char[] chars = {'+','-','*', '+'};
        int result = 24;

        Sequence sequence = new Sequence(result,nums,chars);
        int evaluationResult = sequence.evaluate();
        Toast.makeText(getApplicationContext(),"The sequence has been evaluated into: " + evaluationResult,Toast.LENGTH_LONG).show();
    }


}
