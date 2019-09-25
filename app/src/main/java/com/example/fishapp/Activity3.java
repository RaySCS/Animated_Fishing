package com.example.fishapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity3 extends AppCompatActivity {

    TextView transactionUpdate, textViewPtsAvailable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        transactionUpdate = (TextView)findViewById(R.id.transactionUpdate);
        textViewPtsAvailable = (TextView)findViewById(R.id.textViewPtsAvailable);
        textViewPtsAvailable.setText("Points: " + Activity2.pointsAcrossActivities);
    }

    int ballOneTap = 0;
    int ballTwoTap = 0;
    int ballThreeTap = 0;
    public void purchaseBallOne(View view){
        //Purpose of all of these power up functions is for user to purchase them and use them
        ballOneTap++;
        if(ballOneTap == 1){
            transactionUpdate.setText("Are you sure? If yes, tap again");
        }
        if(ballOneTap == 2){
            if( Activity2.pointsAcrossActivities >= 50){
                transactionUpdate.setText("Transaction Successful!");
                Activity2.ballOnePurchased = true;
                Activity2.ballTwoPurchased = false;
                Activity2.ballThreePurchased = false;
                Activity2.pointsAcrossActivities-=50;
                textViewPtsAvailable.setText("Points: " +  Activity2.pointsAcrossActivities);
            }
            else{
                transactionUpdate.setText("Transaction Unsuccessful!");
            }
        }
    }

    public void purchaseBallTwo(View view){
        //Purpose of all of these power up functions is for user to purchase them and use them
        ballTwoTap++;
        if(ballTwoTap == 1){
            transactionUpdate.setText("Are you sure? If yes, tap again");
        }
        if(ballTwoTap == 2){
            if( Activity2.pointsAcrossActivities >= 100){
                transactionUpdate.setText("Transaction Successful!");
                Activity2.ballOnePurchased = false;
                Activity2.ballTwoPurchased = true;
                Activity2.ballThreePurchased = false;
                Activity2.pointsAcrossActivities-=100;
                textViewPtsAvailable.setText("Points: " +  Activity2.pointsAcrossActivities);
            }
            else{
                transactionUpdate.setText("Transaction Unsuccessful!");
            }
        }
    }

    public void purchaseBallThree(View view){
        //Purpose of all of these power up functions is for user to purchase them and use them
        ballThreeTap++;
        if(ballThreeTap == 1){
            transactionUpdate.setText("Are you sure? If yes, tap again");
        }
        if(ballThreeTap == 2){
            if( Activity2.pointsAcrossActivities >= 200){
                transactionUpdate.setText("Transaction Successful!");
                Activity2.ballOnePurchased = false;
                Activity2.ballTwoPurchased = false;
                Activity2.ballThreePurchased = true;
                Activity2.pointsAcrossActivities-=200;
                textViewPtsAvailable.setText("Points: " +  Activity2.pointsAcrossActivities);
            }
            else{
                transactionUpdate.setText("Transaction Unsuccessful!");
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//in order to make transition between activities look nice
    }
}
