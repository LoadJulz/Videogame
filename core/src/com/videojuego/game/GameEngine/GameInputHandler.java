package com.videojuego.game.GameEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class GameInputHandler implements InputProcessor {

    CharacterEntity charEntity;

    public GameInputHandler(CharacterEntity charEntity){
        this.charEntity = charEntity;
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        Gdx.app.debug("PointX", "Point2: " + screenX);
        GameGestureHandler.flingDistance -= screenX;


        charEntity.setLeftMove(false);
        charEntity.setRightMove(false);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
