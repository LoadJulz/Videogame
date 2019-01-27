package com.videojuego.game.GameEngine;

import com.badlogic.gdx.utils.Timer;

public class ScoreCounter {

    int count;
    int add;
    Timer countIteration;
    int scoreMultiplier = 0;

    public ScoreCounter(){

        this.count = 0;
        this.add = 10;

       countIteration = new Timer();

        countIteration.scheduleTask(new Timer.Task() {
            @Override
            public void run() {


            increaseCounter();

            }
        },2,1);



    }

    public int getCount() {
        return count;
    }

    public void increaseCounter(){
        add = 10;
        if(scoreMultiplier>0){
            add = add*scoreMultiplier;
        }

        count += add;

    }

    public void stopScoreCount(){
        countIteration.stop();
        countIteration.clear();
    }

    public void doubleScore(){

        scoreMultiplier += 2;

        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {



                scoreMultiplier -= 2;

            }
        },5);
    }

    public int getScoreMultiplier(){
        return scoreMultiplier;
    }


}
