package com.videojuego.game.GameEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;
import com.videojuego.game.Dimen;

public class CharacterEntity {

    public int frame = 0;
    public int zeile = 0;

    public float movementX = 0;
    float frameRateMove = 1/10f;

    public Timer move,blink,dodgeTimer;

    GameUtil util;

    Texture textureChar;
    TextureRegion[][] regionChar;
    Sprite spriteChar;

    boolean leftMove,rightMove,dodgeMove,dodgeAvaiable,isHit;

    int timeDogdeAvaiable = 5;



    public CharacterEntity(final GameUtil util){

        this.util = util;
        this.textureChar = new Texture(Gdx.files.internal("spsheet.png"));
        this.regionChar = TextureRegion.split(textureChar,16,32);
        this.spriteChar = new Sprite(regionChar[0][0]);
        this.spriteChar.setSize(Dimen.GAME_WORLD_WIDTH/9,Dimen.GAME_WORLD_HEIGHT/9);


        this.spriteChar.setPosition(Dimen.GAME_WORLD_WIDTH/2,Dimen.GAME_WORLD_HEIGHT/2 - Dimen.GAME_WORLD_HEIGHT/3);


        move = new Timer();

        move.scheduleTask(new Timer.Task() {
            @Override
            public void run() {

                if(dodgeMove){
                    frame++;
                            if(frame > 7){
                                setDodgeMoveLeft(false);
                                setDodgeMoveRight(false);
                                frame = 0;
                                zeile = 0;
                                timeDogdeAvaiable = 5;
                            }
                }else{
                    frame++;

                    if(frame > 3){
                        frame = 0;

                    }


                }

                spriteChar.setRegion(regionChar[zeile][frame]);

            }
        },0,frameRateMove);

        blink = new Timer();



        //TODO: Sekundengenau machen

        dodgeTimer = new Timer();

        blink.scheduleTask(new Timer.Task() {

            boolean waitTemp = false;

            @Override
            public void run() {



                if(!dodgeAvaiable){


                    if(timeDogdeAvaiable==5 && waitTemp == true){
                        waitTemp = false;
                    }else{
                        timeDogdeAvaiable--;
                    }


                    if(timeDogdeAvaiable==0){
                        dodgeAvaiable = true;
                        waitTemp = true;
                    }
                }

            }

        },1/2f,1);





    }



    public Sprite getSpriteChar(){
        return spriteChar;
    }


    public void update(){

        if(dodgeMove){

            frameRateMove = 1/4f;

            if(leftMove) {
                movementX = -5f;
                zeile = 7;
            }else{
                movementX = 5f;
                zeile = 6;
            }



        }else if(leftMove){

            frameRateMove = 1/10f;

            movementX = -3.5f;
            zeile = 1;
        }else if(rightMove){

            frameRateMove = 1/10f;

            movementX = 3.5f;
            zeile = 2;
        }else{

            frameRateMove = 1/15f;

            zeile = 0;
            movementX = 0;
        }

        if(isHit && !dodgeMove){
            zeile += 3;
        }

    }


    public void setLeftMove(boolean value){
        if(!dodgeMove){
            if(value) {
                leftMove = true;
                rightMove = false;
            }else{
                leftMove = false;
            }
        }

    }

    public void setRightMove(boolean value){

        if(!dodgeMove) {
            if (value) {
                leftMove = false;
                rightMove = true;
            } else {
                rightMove = false;
            }
        }
    }

    public void setDodgeMoveLeft(boolean value){
        dodgeAvaiable = false;
        if(value){
            dodgeMove = true;
            leftMove = true;
            rightMove = false;
        }else{
            dodgeMove = false;
            leftMove = false;
            rightMove = false;
        }
    }

    public void setDodgeMoveRight(boolean value){
        dodgeAvaiable = false;
        if(value){
            dodgeMove = true;
            leftMove = false;
            rightMove = true;
        }else{
            dodgeMove = false;
            leftMove = false;
            rightMove = false;
        }
    }




}
