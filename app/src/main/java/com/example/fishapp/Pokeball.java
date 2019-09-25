package com.example.fishapp;

public class Pokeball {
    int totalPoints;

    public Pokeball(){
        totalPoints = 0;
    }

    public void addPoints(int whichBall, int typeOfFish){
        if(whichBall == 0){
            if(typeOfFish == 1){
                totalPoints+=(int)Math.random() * (10 + 1 - 2) + 2;
            }
            if(typeOfFish == 2){
                totalPoints+=(int)Math.random() * (10 + 1 - 3) + 3;
            }
        }
        if(whichBall == 1){
            if(typeOfFish == 1){
                totalPoints+=(int)Math.random() * (10 + 1 - 4) + 4;
            }
            if(typeOfFish == 2){
                totalPoints+=(int)Math.random() * (10 + 1 - 5) + 5;
            }
        }
        if(whichBall == 2){
            if(typeOfFish == 1){
                totalPoints+=(int)Math.random() * (10 + 1 - 5) + 5;
            }
            if(typeOfFish == 2){
                totalPoints+=(int)Math.random() * (10 + 1 - 6) + 6;
            }
        }
        if(whichBall == 3){
            if(typeOfFish == 1){
                totalPoints+=(int)Math.random() * (10 + 1 - 6) + 6;
            }
            if(typeOfFish == 2){
                totalPoints+=(int)Math.random() * (10 + 1 - 7) + 7;
            }
        }

    }

    public int getPoints(){
        return totalPoints;
    }

}
