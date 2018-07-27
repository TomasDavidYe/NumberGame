package com.example.i354640.numbergameproject;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);





        //Here is the onClickListener common for all operator windows

        View.OnClickListener operatorOnClickListener = ( view ) -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            View mView = getLayoutInflater().inflate(R.layout.layout_select_operator, null);
            mBuilder.setView(mView);
            AlertDialog chooseOperatorDialog = mBuilder.create();
            chooseOperatorDialog.show();
            ImageView operatorImageView = findViewById(view.getId());

            //in the layout the items have attribute clickable turned on!
            chooseOperatorDialog.findViewById(R.id.selectMinusImageView).setOnClickListener(view1 -> {
                operatorImageView.setImageResource(R.drawable.minus);
                chooseOperatorDialog.cancel();
            });

            chooseOperatorDialog.findViewById(R.id.selectPlusImageView).setOnClickListener(view1 -> {
                operatorImageView.setImageResource(R.drawable.plus);
                chooseOperatorDialog.cancel();
            });

            chooseOperatorDialog.findViewById(R.id.selectTimesImageView).setOnClickListener(view1 -> {
                operatorImageView.setImageResource(R.drawable.times);
                chooseOperatorDialog.cancel();
            });


            
        };

        ImageView[] operatorImageViews = new ImageView[]{
          findViewById(R.id.operator1ImageView),
          findViewById(R.id.operator2ImageView),
          findViewById(R.id.operator3ImageView)
        };

        for (ImageView operatorImageView: operatorImageViews) {
            operatorImageView.setClickable(true);
            operatorImageView.setOnClickListener(operatorOnClickListener);
        }




    }




}
