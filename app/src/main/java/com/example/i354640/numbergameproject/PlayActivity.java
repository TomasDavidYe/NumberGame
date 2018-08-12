package com.example.i354640.numbergameproject;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PlayActivity extends AppCompatActivity {

    //mapping numbers to their images
    private final Map<Integer,Integer> numberToImage = new HashMap<Integer, Integer>(){
        {
            put(1,R.drawable.number1);
            put(2,R.drawable.number2);
            put(3,R.drawable.number3);
            put(4,R.drawable.number4);
            put(5,R.drawable.number5);
            put(6,R.drawable.number6);
            put(7,R.drawable.number7);
            put(8,R.drawable.number8);
            put(9,R.drawable.number9);
        }
    };

    private final Map<Character,Integer> operatorToImage = new HashMap<Character, Integer>(){
        {
            put('+', R.drawable.plus);
            put('-', R.drawable.minus);
            put('x', R.drawable.times);
            put('?', R.drawable.question);
        }
    };
    private int wantedResult;
    int score;
    private int[] numbers;
    private char[] generatedOperators;
    private char[] userOperators;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //Creating references to number and operator image views
        ImageView[] operatorImageViews = new ImageView[]{
                findViewById(R.id.operator1ImageView),
                findViewById(R.id.operator2ImageView),
                findViewById(R.id.operator3ImageView)

        };

        ImageView[] numberImageViews = new ImageView[]{
                findViewById(R.id.number1ImageView),
                findViewById(R.id.number2ImageView),
                findViewById(R.id.number3ImageView),
                findViewById(R.id.number4ImageView)
        };

        TextView scoreScreen = findViewById(R.id.scoreTextView);

        if(savedInstanceState == null){
            //first iteration, generating new sequence from scratch
            numbers = generateNumbers();
            generatedOperators = generateOperators();
            userOperators = new char[] {'?', '?', '?'};
            score = 0;

        }
        else{
            numbers = savedInstanceState.getIntArray("numbers");
            userOperators = savedInstanceState.getCharArray("userOperators");
            generatedOperators = savedInstanceState.getCharArray("generatedOperators");
            score = savedInstanceState.getInt("score");
        }
        wantedResult = evaluate(numbers,generatedOperators);
        for(int i = 0; i<3;i++) operatorImageViews[i].setTag(i);
        updateSequence(operatorImageViews,numberImageViews);
        updateScoreScreen(scoreScreen);




        ((Button) findViewById(R.id.evaluateBtn)).setOnClickListener(view -> {

            boolean operatorCheck = checkIfAllOperatorsAssigned();

            if(operatorCheck) {
                int playerResult = evaluate(numbers,userOperators);
                if (playerResult == wantedResult) {
                    Toast.makeText(this, "Well done!!!", Toast.LENGTH_SHORT).show();
                    score++;
                    updateScoreScreen(scoreScreen);
                } else {
                    Toast.makeText(this, "Incorrect, you result is " + playerResult, Toast.LENGTH_SHORT)
                            .show();
                }
            } else{
                Toast.makeText(this, "Not all operators are assigned", Toast.LENGTH_SHORT).show();
            }

        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntArray("numbers",numbers);
        outState.putCharArray("userOperators", userOperators);
        outState.putCharArray("generatedOperators", generatedOperators);
        outState.putInt("score", score);
        super.onSaveInstanceState(outState);
    }

    protected void updateSequence(ImageView[] operatorImageViews, ImageView[] numberImageViews){


        ((TextView) findViewById(R.id.goalNumTextView)).setText("Goal = " + wantedResult);
        //initializing operator image views

        //initializing number image views
        for(int i = 0; i < 4; i++){
            numberImageViews[i].setImageResource(numberToImage.get(numbers[i]));
        }

        //Here is the onClickListener common for all operator windows
        View.OnClickListener operatorOnClickListener = ( view ) -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            View mView = getLayoutInflater().inflate(R.layout.layout_select_operator, null);
            mBuilder.setView(mView);
            AlertDialog chooseOperatorDialog = mBuilder.create();
            chooseOperatorDialog.show();
            ImageView operatorImageView = findViewById(view.getId());

            //here we set up onclick listeners for the operator signs in the dialog that opens up
            chooseOperatorDialog.findViewById(R.id.selectMinusImageView).setOnClickListener(view1 -> {
                operatorImageView.setImageResource(R.drawable.minus);
                operatorImageView.setTag('-');
                updateUserOperators(operatorImageViews);
                chooseOperatorDialog.cancel();
            });

            chooseOperatorDialog.findViewById(R.id.selectPlusImageView).setOnClickListener(view1 -> {
                operatorImageView.setImageResource(R.drawable.plus);
                operatorImageView.setTag('+');
                updateUserOperators(operatorImageViews);
                chooseOperatorDialog.cancel();

            });

            chooseOperatorDialog.findViewById(R.id.selectTimesImageView).setOnClickListener(view1 -> {
                operatorImageView.setImageResource(R.drawable.times);
                operatorImageView.setTag('x');
                updateUserOperators(operatorImageViews);
                chooseOperatorDialog.cancel();
            });



        };


        for (int i = 0; i < 3; i++) {
            ImageView operatorImageView = operatorImageViews[i];
            operatorImageView.setClickable(true);
            operatorImageView.setOnClickListener(operatorOnClickListener);
            char operatorSign = userOperators[i];
            int pictureId = operatorToImage.get(operatorSign);
            operatorImageView.setImageResource(pictureId);
            operatorImageView.setTag(operatorSign);

        }
    }

    protected int[] generateNumbers(){
        return new int[]{3,4,5,8};
    }

    protected char[] generateOperators(){
        return new char[] {'x', '-', 'x'};
    }

    private int evaluate(int[] numbers, char[] operators){
        Map<Character, Operator> operatorInterpretation = new HashMap<Character, Operator>(){
            {
                put('+', (a,b) -> a + b);
                put('-', ((a, b) -> a - b));
                put('x', ((a, b) -> a*b));
            }
        };


        int temp = numbers[0];
        int length = operators.length;
        for(int i = 0; i < length; i++){
            char operatorSign = operators[i];
            Operator operator = operatorInterpretation.get(operatorSign);
            int nextNum = numbers[i + 1];
            temp = operator.Operation(temp, nextNum);
        }

        return temp;
    }

    private void updateUserOperators(ImageView[] operatorImageViews){
        for(int i = 0; i<3;i++){
            userOperators[i] = (char) operatorImageViews[i].getTag();
        }
    }

    private boolean checkIfAllOperatorsAssigned(){
        return !(new String(userOperators)).contains("?");
    }

    private void updateScoreScreen(TextView scoreScreen){
        scoreScreen.setText("Score =  " + score);
    }

    interface Operator {
        int Operation(int a, int b);
    }











}
