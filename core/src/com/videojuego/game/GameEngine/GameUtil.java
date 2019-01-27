package com.videojuego.game.GameEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.videojuego.game.GameEngine.FireballEntity;
import com.videojuego.game.GdxGame;

import java.util.ArrayList;

public class GameUtil {

    SpriteBatch batch;
    public OrthographicCamera camera;


    int lifeCounter = 3;


    boolean dead = false;



    public GameUtil(){


    }


    public void decreaseLifeCounter(){
        this.lifeCounter--;
    }

    public void increaseLifeCounter(){
        if(getLifeCounter()< 3){
            this.lifeCounter++;
        }

    }

    public int getLifeCounter(){
        return this.lifeCounter;
    }

    public void setGameOver(){
        this.dead = true;
    }

    public boolean getGameOver(){
        return this.dead;
    }

    public void resetGame(){
        this.lifeCounter = 3;
        this.dead = false;

    }

    public BitmapFont getFont(int size){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
        font.setColor(Color.BLACK);
        return font;
    }






}
