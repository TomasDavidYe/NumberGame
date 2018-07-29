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

import java.util.HashMap;
import java.util.Map;

public class PlayActivity extends AppCompatActivity {

    //mapping numbers to their images
    private static final Map<Integer,Integer> numberToImage = new HashMap<Integer, Integer>(){
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

        //generating a sequence and a result;
        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        Sequence sequence = sequenceGenerator.generateSequence();
        //update the displayed sequence for the first time
        int sequenceLength = numberImageViews.length;
        updateSequence(operatorImageViews,numberImageViews, sequence, sequenceLength);




        ((Button) findViewById(R.id.evaluateBtn)).setOnClickListener(view -> {
            char[] submittedOperators = new char[sequenceLength -1];
            for(int i = 0; i < sequenceLength - 1; i++){
                submittedOperators[i] = (char) operatorImageViews[i].getTag();
            }
            //check if all operators are assigned
            boolean operatorCheck = true;
            for (int i = 0; i <sequenceLength-1; i++){
                if(submittedOperators[i] == '?') operatorCheck = false;
            }
            //error if missing operators
            if(!operatorCheck){
                Toast.makeText(this,"Some operators are not assigned!", Toast.LENGTH_SHORT).show();
                return;
            }

            sequence.submitNewOperators(submittedOperators);
            int playerResult = sequence.evaluate();

            if(playerResult == sequence.getResult()){
                Toast.makeText(this, "Well done!!!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Incorrect, you result is " + playerResult, Toast.LENGTH_SHORT)
                .show();
            }
            //after evaluation, update view with a newly generated sequence
            updateSequence(operatorImageViews, numberImageViews,sequenceGenerator.generateSequence() ,sequenceLength);

        });


    }

    protected void updateSequence(ImageView[] operatorImageViews, ImageView[] numberImageViews, Sequence sequence, int sequenceLength ){


        int wantedResult = sequence.getResult();
        ((TextView) findViewById(R.id.goalNumTextView)).setText("Goal = " + wantedResult);
        //initializing operator image views
        for (ImageView operatorImageView: operatorImageViews) {
            operatorImageView.setImageResource(R.drawable.question);
        }
        //initializing number image views
        int[] numbers = sequence.getNumbers();
        for(int i = 0; i < sequenceLength; i++){
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
                chooseOperatorDialog.cancel();
            });

            chooseOperatorDialog.findViewById(R.id.selectPlusImageView).setOnClickListener(view1 -> {
                operatorImageView.setImageResource(R.drawable.plus);
                operatorImageView.setTag('+');
                chooseOperatorDialog.cancel();
            });

            chooseOperatorDialog.findViewById(R.id.selectTimesImageView).setOnClickListener(view1 -> {
                operatorImageView.setImageResource(R.drawable.times);
                operatorImageView.setTag('x');
                chooseOperatorDialog.cancel();
            });



        };

        for (ImageView operatorImageView: operatorImageViews) {
            operatorImageView.setClickable(true);
            operatorImageView.setOnClickListener(operatorOnClickListener);
            operatorImageView.setTag('?');
        }
    }











}
