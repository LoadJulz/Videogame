package com.videojuego.game.GameEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class GameGestureHandler implements GestureListener {

    public CharacterEntity charAnimData;

    public GameGestureHandler(CharacterEntity charAnimData){
        this.charAnimData = charAnimData;
    }

    public static float flingDistance = 0;

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        flingDistance = x;

        Gdx.app.debug("PointX", "Point1: " + flingDistance);

        if (x < Gdx.graphics.getWidth() / 2){

            charAnimData.setLeftMove(true);

        }else if(y > Gdx.graphics.getWidth() / 2){

            charAnimData.setRightMove(true);

        }

        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        return false;

    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        if(charAnimData.dodgeAvaiable){
            flingDistance = Math.abs(flingDistance);



            if (velocityX < 0 &&  flingDistance > 75){
                Gdx.app.debug("PointX", "Yes");
                charAnimData.setDodgeMoveLeft(true);



            }else if(velocityX > 0  && flingDistance > 75){

                charAnimData.setDodgeMoveRight(true);

            }

            return true;
        }

        return false;

    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
