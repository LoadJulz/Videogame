package com.videojuego.game.GameEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;


public class BallManager {

    private Array<BallEntity> activeFireballs;
    private Pool<BallEntity> fireballPool;
    private Timer spawn;
    private float spawnRate;
    int speed;
    GameUtil gameUtil;
    int number = 0;
    public int number(){
        number++;

        return number;
    }

    public BallManager(float spawnRate, int speedPar, final GameUtil util){
        this.spawnRate = spawnRate;
        this.speed = speedPar;
        this.activeFireballs = new Array<BallEntity>();
        this.gameUtil = util;

        this.fireballPool = new Pool<BallEntity>() {
            @Override
            protected BallEntity newObject() {

                    return new BallEntity(util,number());


            }
        };

        spawn = new Timer();

        spawn.scheduleTask(new Timer.Task() {
            @Override
            public void run() {

                BallEntity item = fireballPool.obtain();

                int random = (int)(Math.random() * 50 + 1);

                if(random > 48){
                    if(util.getLifeCounter()==3){
                        item.setType(BallType.DoubleScore);
                    }else{
                        item.setType(BallType.HealthUp);
                    }
                }else{
                    item.setType(BallType.Fireball);
                }



                item.init(speed);
                activeFireballs.add(item);

            }
        },2,spawnRate);

    }

    public float getSpawnRate() {
        return spawnRate;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpawnRate(float spawnRate) {
        this.spawnRate = spawnRate;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }



    public Array<BallEntity> getList(){
        return this.activeFireballs;
    }

    public Timer getSpawn(){
        return this.spawn;
    }

    public void increaseLevel(){
        this.speed++;
        this.spawnRate = spawnRate*0.3f;
    }

    public void updateFireballs(){



        BallEntity item;


        for(int i = 0; i < activeFireballs.size; i++){
            item = activeFireballs.get(i);

            if(item.alive == false){
                activeFireballs.removeIndex(i);
                fireballPool.free(item);
            }else{
                item.update();
            }
       }
    }


}
