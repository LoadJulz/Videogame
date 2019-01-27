package com.videojuego.game.GameEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.videojuego.game.Dimen;

public class BallEntity implements Pool.Poolable {

    private GameUtil gameUtil;
    private BallType type;

    Sprite sprite;
    Texture img;
    TextureRegion[][] regions;
    int positionX,positionY;
    int speed;
    public int order;

    boolean alive;

    public BallEntity(GameUtil gameUtil,BallType type){
        this.gameUtil = gameUtil;
        this.type = type;
        alive = false;
    }


    public BallEntity(GameUtil gameUtil,int order){
        this.gameUtil = gameUtil;
        alive = false;
        this.order = order;
    }
    public void init(int speed){

        switch (type){
            case HealthUp:
                img = new Texture(Gdx.files.internal("heartup.png"));
                break;
            case DoubleScore:
                img = new Texture(Gdx.files.internal("doublescore.png"));
                break;
            case Fireball:
                img = new Texture(Gdx.files.internal("fireballsheet.png"));
                break;
        }

        alive = true;
        this.positionY = (int) Dimen.GAME_WORLD_HEIGHT + 20;
        this.speed = speed;

        regions = TextureRegion.split(img,14,14);
        sprite = new Sprite(regions[0][0]);
        sprite.setSize(Dimen.GAME_WORLD_WIDTH/20,Dimen.GAME_WORLD_HEIGHT/20);

        int min = 0;
        int max = (int) Dimen.GAME_WORLD_WIDTH - (int) (sprite.getWidth());

        positionX = (int)(Math.random() * (max-min)) + min;

        sprite.setPosition(positionX,Dimen.GAME_WORLD_HEIGHT);
    }


    public Sprite getSprite(){
        return sprite;
    }

    public int getPositionY(){
        return this.positionY;
    }

    public void update(){
        this.positionY = this.positionY - speed;
        if(this.positionY < - getSprite().getHeight()){
            alive = false;
        }else{
            getSprite().setPosition(positionX,getPositionY());
        }
    }

    @Override
    public void reset() {
        alive = false;
    }

    public BallType getType(){
        return type;
    }

    public void setType(BallType type){
        this.type = type;
    }
}
