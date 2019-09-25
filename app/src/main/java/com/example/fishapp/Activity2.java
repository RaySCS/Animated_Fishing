package com.example.fishapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.constraintlayout.solver.widgets.Rectangle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Activity2 extends AppCompatActivity {

    public static int pointsAcrossActivities = 0;

    int totalPoints = 0;//total taps does not change


    public static boolean ballOnePurchased = false;
    public static boolean ballTwoPurchased = false;
    public static boolean ballThreePurchased = false;

    int levelOfPokeBall = 0;

    private static final String TAG = Activity2.class.getSimpleName();
//
    private ImageView pokeFloatBall;
    private RelativeLayout mMainLayout;
    private int maxTranslationX;
    private int maxTranslationY;

    ImageView[] fishImagesArrayOne = new ImageView[3];

    ImageView[] fishImagesArrayThree= new ImageView[3];

    ImageView garbage1, garbage2;

    TextView timerTextViewUse, pointsTextView;

    ImageView restartImageViewUse;

    Pokeball userPokeBall = new Pokeball();

    TranslateAnimation fishAnimationFirstRow;
    TranslateAnimation fishAnimationFirstRow2;
    TranslateAnimation fishAnimationFirstRow3;

    TranslateAnimation fishAnimationThirdRow;
    TranslateAnimation fishAnimationThirdRow2;
    TranslateAnimation fishAnimationThirdRow3;

    Rect rc1, rc2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        //w:1200//h:1824

        pokeFloatBall = (ImageView) findViewById(R.id.pokeFloatBall);
        garbage1 = (ImageView)findViewById(R.id.imageView4);
        garbage2 = (ImageView)findViewById(R.id.imageView7);
        if(ballOnePurchased == true){
            levelOfPokeBall = 1;
            pokeFloatBall.setImageResource(R.drawable.upgone);
        }
        if(ballTwoPurchased == true){
            levelOfPokeBall = 2;
            pokeFloatBall.setImageResource(R.drawable.upgtwo);
        }
        if(ballThreePurchased == true){
            levelOfPokeBall = 3;
            pokeFloatBall.setImageResource(R.drawable.upgthee);
        }


        restartImageViewUse = (ImageView)findViewById(R.id.restartImageView);

        restartImageViewUse.setVisibility(View.GONE);

        timerTextViewUse = (TextView)findViewById(R.id.timerTextView);

        pointsTextView = (TextView)findViewById(R.id.pointsTextView);

        runFish();

        //timer stuff below
        new CountDownTimer(38000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(pokeFloatBall.getX() >= 1900 || pokeFloatBall.getX() <= 0){
                    pokeFloatBall.setTranslationX(10);
                    pokeFloatBall.setTranslationY(100);
                }
                if(pokeFloatBall.getY() >= 1500 || pokeFloatBall.getY() <= 0){
                    pokeFloatBall.setTranslationX(10);
                    pokeFloatBall.setTranslationY(100);
                }

                checkForCollision();

                timerTextViewUse.setText("Time: " + millisUntilFinished / 1000 + "s");

            }

            public void onFinish() {
                timerTextViewUse.setText("Time: 0s");
                restartImageViewUse.setVisibility(View.VISIBLE);
                fishImagesArrayOne[0].setVisibility(View.VISIBLE);
                fishImagesArrayOne[1].setVisibility(View.VISIBLE);
                fishImagesArrayOne[2].setVisibility(View.VISIBLE);
                fishImagesArrayThree[0].setVisibility(View.VISIBLE);
                fishImagesArrayThree[1].setVisibility(View.VISIBLE);
                fishImagesArrayThree[2].setVisibility(View.VISIBLE);
            }
        }.start();


        mMainLayout = (RelativeLayout) findViewById(R.id.main_layout);

        final GestureDetector gestureDetector = new GestureDetector(this, mGestureListener);

        pokeFloatBall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //this method is constantly running
                return gestureDetector.onTouchEvent(event);
            }
        });


        mMainLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                maxTranslationX = mMainLayout.getWidth() - pokeFloatBall.getWidth();
                maxTranslationY = mMainLayout.getHeight() - pokeFloatBall.getHeight();
                //As only wanted the first call back, so now remove the listener
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    mMainLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    mMainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {

        //Constants
        private static final int MIN_DISTANCE_MOVED = 50;
        private static final float MIN_TRANSLATION = 0;
        private static final float FRICTION = 1.1f;

        @Override
        public boolean onDown(MotionEvent arg0) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
            //downEvent : when user puts his finger down on the view
            //moveEvent : when user lifts his finger at the end of the movement
            float distanceInX = Math.abs(moveEvent.getRawX() - downEvent.getRawX());
            float distanceInY = Math.abs(moveEvent.getRawY() - downEvent.getRawY());

            if (distanceInX > MIN_DISTANCE_MOVED) {
                //Fling Right/Left
                flingX = new FlingAnimation(pokeFloatBall, DynamicAnimation.TRANSLATION_X);
                flingX.setStartVelocity(velocityX)
                        .setMinValue(MIN_TRANSLATION) // minimum translationX property
                        .setMaxValue(maxTranslationX)  // maximum translationX property
                        .setFriction(FRICTION)
                        .start();
            } else if (distanceInY > MIN_DISTANCE_MOVED) {
                //Fling Down/Up
                flingY = new FlingAnimation(pokeFloatBall, DynamicAnimation.TRANSLATION_Y);
                flingY.setStartVelocity(velocityY)
                        .setMinValue(MIN_TRANSLATION)  // minimum translationY property
                        .setMaxValue(maxTranslationY) // maximum translationY property
                        .setFriction(FRICTION)
                        .start();
            }


            return true;
        }

    };
    FlingAnimation flingX, flingY;
//    https://github.com/richakhanna/physicsbasedanimation/blob/master/app/src/main/java/com/richdroid/physicsbasedanimation/ui/activity/TranslateFlingAnimationActivity.java
//    https://stackoverflow.com/questions/39184318/imageview-objectanimator-transition-collision-detection-during-transition

    public void restartGame(View view){
        restartImageViewUse.setVisibility(View.GONE);
        System.out.println(restartImageViewUse.getVisibility());
        resetFishImages();
        fishImagesArrayOne[0].setVisibility(View.VISIBLE);
        fishImagesArrayOne[1].setVisibility(View.VISIBLE);
        fishImagesArrayOne[2].setVisibility(View.VISIBLE);
        fishImagesArrayThree[0].setVisibility(View.VISIBLE);
        fishImagesArrayThree[1].setVisibility(View.VISIBLE);
        fishImagesArrayThree[2].setVisibility(View.VISIBLE);

        pokeFloatBall.setTranslationX(10);
        pokeFloatBall.setTranslationY(100);

        totalPoints = 0;
        pointsTextView.setText("Points: " + totalPoints);

        new CountDownTimer(38000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(pokeFloatBall.getX() >= 1900 || pokeFloatBall.getX() <= 0){
                    pokeFloatBall.setTranslationX(10);
                    pokeFloatBall.setTranslationY(100);
                }
                if(pokeFloatBall.getY() >= 1500 || pokeFloatBall.getY() <= 0){
                    pokeFloatBall.setTranslationX(10);
                    pokeFloatBall.setTranslationY(100);
                }

                checkForCollision();
                timerTextViewUse.setText("Time: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                timerTextViewUse.setText("Time: 0s");
                restartImageViewUse.setVisibility(View.VISIBLE);

            }
        }.start();

    }


    public void runFish(){
        fishImagesArrayOne[0] = (ImageView)findViewById(R.id.fishImageOne);
        fishImagesArrayOne[1] = (ImageView)findViewById(R.id.fishImageTwo);
        fishImagesArrayOne[2] = (ImageView)findViewById(R.id.fishImageThree);

        fishImagesArrayThree[0] = (ImageView)findViewById(R.id.fishImageFour);
        fishImagesArrayThree[1] = (ImageView)findViewById(R.id.fishImageFive);
        fishImagesArrayThree[2] = (ImageView)findViewById(R.id.fishImageSix);


    }

    public void checkForCollision(){
        //Below the the rectangle objects are being declared and they are being drawn around the imageviews
        Rect rc1 = new Rect();
        pokeFloatBall.getHitRect(rc1);
        Rect rc2 = new Rect();
        fishImagesArrayOne[0].getHitRect(rc2);
        Rect rc3 = new Rect();
        fishImagesArrayOne[1].getHitRect(rc3);
        Rect rc4 = new Rect();
        fishImagesArrayOne[2].getHitRect(rc4);
        Rect rc5 = new Rect();
        fishImagesArrayThree[0].getHitRect(rc5);
        Rect rc6 = new Rect();
        fishImagesArrayThree[1].getHitRect(rc6);
        Rect rc7 = new Rect();
        fishImagesArrayThree[2].getHitRect(rc7);
        Rect rc8 = new Rect();
        garbage1.getHitRect(rc8);
        Rect rc9 = new Rect();
        garbage2.getHitRect(rc9);
        if(Rect.intersects(rc1,rc2)){
            makeCollisionOccur(1, 1);
        }
        if(Rect.intersects(rc1,rc3)){
            makeCollisionOccur(1, 2);
        }
        if(Rect.intersects(rc1,rc4)){
            makeCollisionOccur(1, 3);
        }

        if(Rect.intersects(rc1,rc5)){
            makeCollisionOccur(2, 1);
        }
        if(Rect.intersects(rc1,rc6)){
            makeCollisionOccur(2, 2);
        }
        if(Rect.intersects(rc1,rc7)){
            makeCollisionOccur(2, 3);
        }
        if(Rect.intersects(rc1,rc8)){
            makeCollisionGarbage();
        }
        if(Rect.intersects(rc1,rc9)){
            makeCollisionGarbage();
        }
    }

    public void makeCollisionGarbage(){
        //purpose to deduct and reset if garbage hit
        pokeFloatBall.setTranslationX(10);
        pokeFloatBall.setTranslationY(100);
        totalPoints-=(int)(Math.random() * ((3 - 1) + 1)) + 1;
        if (totalPoints < 0) {
            totalPoints = 0;
        }
        pointsTextView.setText("Points: " + totalPoints);
        Toast.makeText(this, "Garbage Hit!", Toast.LENGTH_SHORT).show();
    }

    boolean fishOneDone = false;
    boolean fishTwoDone = false;
    boolean fishThreeDone = false;
    boolean fishFourDone = false;
    boolean fishFiveDone = false;
    boolean fishSixDone = false;
    public void makeCollisionOccur(int whichF, int whichInRow){
        if(whichF == 1){
            int probabilityToCatch = tellProbability(1);
            if(probabilityToCatch >= 5){
                System.out.println("reached here!");
                if(!fishOneDone){
                    if(whichInRow == 1){
                        diplayFishInfo(0);
                        fishImagesArrayOne[0].animate().setDuration(3000).rotationBy(360f).start();
                        fishImagesArrayOne[0].setImageResource(R.drawable.fishdone);
                        fishOneDone = true;
                    }
                }
                else{
                    diplayFishInfo(1);
                }

                if(!fishTwoDone){
                    if(whichInRow == 2){
                        diplayFishInfo(0);
                        fishImagesArrayOne[1].animate().setDuration(3000).rotationBy(360f).start();
                        fishImagesArrayOne[1].setImageResource(R.drawable.fishdone);
                        fishTwoDone = true;
                    }
                }
                else{
                    diplayFishInfo(1);
                }

                if(!fishThreeDone){
                    if(whichInRow == 3){
                        diplayFishInfo(0);
                        fishImagesArrayOne[2].animate().setDuration(3000).rotationBy(360f).start();
                        fishImagesArrayOne[2].setImageResource(R.drawable.fishdone);
                        fishThreeDone = true;
                    }
                }
                else{
                    diplayFishInfo(1);
                }

                if(levelOfPokeBall == 0){
                    userPokeBall.addPoints(0, 1);
                    totalPoints+=userPokeBall.getPoints();
                    pointsAcrossActivities+=userPokeBall.getPoints();
                }
                else if(levelOfPokeBall == 1){
                    userPokeBall.addPoints(1, 1);
                    totalPoints+=userPokeBall.getPoints();
                    pointsAcrossActivities+=userPokeBall.getPoints();
                }
                else if(levelOfPokeBall == 2){
                    userPokeBall.addPoints(2, 1);
                    totalPoints+=userPokeBall.getPoints();
                    pointsAcrossActivities+=userPokeBall.getPoints();
                }
                else if(levelOfPokeBall == 3){
                    userPokeBall.addPoints(3, 1);
                    totalPoints+=userPokeBall.getPoints();
                    pointsAcrossActivities+=userPokeBall.getPoints();
                }
                System.out.println("PUNTSO" + pointsAcrossActivities);
                pointsTextView.setText("Points: " + totalPoints);
            }
            else{
                Toast.makeText(this, "Not Caught!", Toast.LENGTH_SHORT).show();
                pokeFloatBall.setTranslationX(10);
                pokeFloatBall.setTranslationY(100);

            }
        }



        //other row
        if(whichF == 2){
            int probabilityToCatch = tellProbability(2);
            if(probabilityToCatch >= 5){
                if(!fishFourDone){
                    if(whichInRow == 1){
                        diplayFishInfo(0);
                        fishImagesArrayThree[0].animate().setDuration(3000).rotationBy(360f).start();
                        fishImagesArrayThree[0].setImageResource(R.drawable.fishdone);
                        fishFourDone = true;
                    }
                }
                else{
                    diplayFishInfo(1);
                }

                if(!fishFiveDone){
                    if(whichInRow == 2){
                        diplayFishInfo(0);
                        fishImagesArrayThree[1].animate().setDuration(3000).rotationBy(360f).start();
                        fishImagesArrayThree[1].setImageResource(R.drawable.fishdone);
                        fishFiveDone = true;
                    }
                }
                else{
                    diplayFishInfo(1);
                }

                if(!fishSixDone){
                    if(whichInRow == 3){
                        diplayFishInfo(0);
                        fishImagesArrayThree[2].animate().setDuration(3000).rotationBy(360f).start();
                        fishImagesArrayThree[2].setImageResource(R.drawable.fishdone);
                        fishSixDone = true;
                    }
                }
                else{
                    diplayFishInfo(1);
                }
                if(levelOfPokeBall == 0){
                    userPokeBall.addPoints(0, 1);
                    totalPoints+=userPokeBall.getPoints();
                    pointsAcrossActivities+=userPokeBall.getPoints();
                }
                else if(levelOfPokeBall == 1){
                    userPokeBall.addPoints(1, 1);
                    totalPoints+=userPokeBall.getPoints();
                    pointsAcrossActivities+=userPokeBall.getPoints();
                }
                else if(levelOfPokeBall == 2){
                    userPokeBall.addPoints(2, 1);
                    totalPoints+=userPokeBall.getPoints();
                    pointsAcrossActivities+=userPokeBall.getPoints();
                }
                else if(levelOfPokeBall == 3){
                    userPokeBall.addPoints(3, 1);
                    totalPoints+=userPokeBall.getPoints();
                    pointsAcrossActivities+=userPokeBall.getPoints();
                }
                System.out.println("TOTAL PITS: " + pointsAcrossActivities);
            }
            else{
                Toast.makeText(this, "Not Caught!", Toast.LENGTH_SHORT).show();
                pokeFloatBall.setTranslationX(10);
                pokeFloatBall.setTranslationY(100);

            }
        }
        pointsTextView.setText("Points: " + totalPoints);
    }
//    https://stackoverflow.com/questions/18398198/how-to-know-if-two-images-are-intersect-while-one-image-moving-in-android
    int probReturn = 0;

    public void resetFishImages(){
        fishImagesArrayOne[0].setImageResource(R.drawable.fishyone);
        fishImagesArrayOne[1].setImageResource(R.drawable.fishtwoactual);
        fishImagesArrayOne[2].setImageResource(R.drawable.fishthreeactual);

        fishImagesArrayThree[0].setImageResource(R.drawable.fishfour);
        fishImagesArrayThree[1].setImageResource(R.drawable.fishfive);
        fishImagesArrayThree[2].setImageResource(R.drawable.fishsix);
    }
    public void diplayFishInfo(int whichT){
        if(whichT == 0){
            pointsTextView.setText("Points: " + pointsAcrossActivities);
            Toast.makeText(this, "Caught!", Toast.LENGTH_SHORT).show();
            pokeFloatBall.setTranslationX(10);
            pokeFloatBall.setTranslationY(100);
        }
        else{
            Toast.makeText(this, "Already Caught!", Toast.LENGTH_SHORT).show();
            pokeFloatBall.setTranslationX(10);
            pokeFloatBall.setTranslationY(100);
        }
    }
    public int tellProbability(int whichFish){
//        Math.random() * (max + 1 - min)) + min
        if(levelOfPokeBall == 0){
            if(whichFish == 1){
                //out of 10 probability
                probReturn = (int)(Math.random() * ((10 - 3) + 1)) + 3;
            }
            if(whichFish == 2){
                //out of 10 probability
                probReturn = (int)(Math.random() * ((10 - 2) + 1)) + 2;
            }
        }
        else if(levelOfPokeBall == 1){
            if(whichFish == 1){
                //out of 10 probability
                probReturn = (int)(Math.random() * ((10 - 4) + 1)) + 4;
            }
            if(whichFish == 2){
                //out of 10 probability
                probReturn = (int)(Math.random() * ((10 - 3) + 1)) + 3;
            }
        }
        else if(levelOfPokeBall == 2){
            if(whichFish == 1){
                //out of 10 probability
                probReturn = (int)(Math.random() * ((10 - 5) + 1)) + 5;
            }
            if(whichFish == 2){
                //out of 10 probability
                probReturn = (int)(Math.random() * ((10 - 4) + 1)) + 4;
            }
        }
        else if(levelOfPokeBall == 2){
            if(whichFish == 1){
                //out of 10 probability
                probReturn = (int)(Math.random() * ((10 - 6) + 1)) + 6;
            }
            if(whichFish == 2){
                //out of 10 probability
                probReturn = (int)(Math.random() * ((10 - 5) + 1)) + 5;
            }
        }
        else if(levelOfPokeBall == 3){
            if(whichFish == 1){
                //out of 10 probability
                probReturn = (int)(Math.random() * ((10 - 7) + 1)) + 7;
            }
            if(whichFish == 2){
                //out of 10 probability
                probReturn = (int)(Math.random() * ((10 - 6) + 1)) + 6;
            }
        }
        System.out.println(probReturn);

        return probReturn;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//in order to make transition between activities look nice
    }
}
