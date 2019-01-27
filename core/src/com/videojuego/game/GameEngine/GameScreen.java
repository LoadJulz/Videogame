package com.videojuego.game.GameEngine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.videojuego.game.AbstractScreen;
import com.videojuego.game.Dimen;


public class GameScreen extends AbstractScreen {

    GameUtil util;
    CharacterEntity charEntity;
    Texture textureHeart, textureNoHeart;
    String score = "Saved: " + "00000";
    String dodgeCounter = "5s";
    float scoreWidth,scoreHeight,dodgeCounterWidth,dodgeCounterHeight,doubleScoreWidth;
    ScoreCounter scoreCounter;
    BallManager firBallManager;
    SpriteBatch gameBatch;
    BitmapFont font;


    //Heartdisplay Temp Values
    int initialX = 1;
    int initialY = (int) Dimen.GAME_WORLD_HEIGHT - 50;
    float gap = 0.5f;
    float size = Dimen.GAME_WORLD_WIDTH / 10;

    public GameScreen(Game game, GameUtil util) {
        super(game);
        this.util = util;
    }

    @Override
    public void show() {


        util.resetGame();


        charEntity = new CharacterEntity(util);
        scoreCounter = new ScoreCounter();
        firBallManager = new BallManager(0.3f,3,util);

        GlyphLayout layout = new GlyphLayout(util.getFont(25), score);
        scoreWidth = layout.width;
        scoreHeight = layout.height;

        layout = new GlyphLayout(util.getFont(25), dodgeCounter);
        dodgeCounterWidth = layout.width;
        dodgeCounterHeight = layout.height;

        layout = new GlyphLayout(util.getFont(25), "2X ");
        doubleScoreWidth = layout.width;




        gameBatch = new SpriteBatch();

        textureHeart = new Texture(Gdx.files.internal("heart.png"));

        textureNoHeart = new Texture(Gdx.files.internal("noheart.png"));

        charEntity.move.start();
        determineLevel();
        createFont();




        //Fixed beginning dodge bug

        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                InputMultiplexer multiplexer = new InputMultiplexer();
                multiplexer.addProcessor(new GameInputHandler(charEntity));
                multiplexer.addProcessor(new GestureDetector(new GameGestureHandler(charEntity)));
                Gdx.input.setInputProcessor(multiplexer);
            }
        },0.1f,1f,1);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        util.camera.update();

        firBallManager.updateFireballs();
        charEntity.update();

        gameBatch.begin();
        gameBatch.setProjectionMatrix(util.camera.combined);

        charEntity.getSpriteChar().draw(gameBatch);

        if(charEntity.movementX < 0){
            if(charEntity.getSpriteChar().getX() > 0){
                charEntity.getSpriteChar().translate(charEntity.movementX, 0);
            }
        }else if(charEntity.movementX > 0){
            if(charEntity.getSpriteChar().getX() < Dimen.GAME_WORLD_WIDTH - charEntity.getSpriteChar().getWidth()){
                charEntity.getSpriteChar().translate(charEntity.movementX, 0);
            }
        }


        Rectangle rectChar = charEntity.getSpriteChar().getBoundingRectangle();

        rectChar.setSize(rectChar.getWidth()*0.8f,rectChar.getHeight()*0.8f);




        for(int i = 0; i < firBallManager.getList().size; i++){

            firBallManager.getList().get(i).getSprite().draw(gameBatch);

        }


        int life = util.getLifeCounter();

        if(life== 3) {
            gameBatch.draw(textureHeart, initialX, initialY, size, size);
            gameBatch.draw(textureHeart, initialX + size + gap, initialY, size, size);
            gameBatch.draw(textureHeart, initialX + size * 2 + gap * 2, initialY, size, size);
        }else if(life==2){
            gameBatch.draw(textureHeart, initialX, initialY, size, size);
            gameBatch.draw(textureHeart, initialX + size + gap, initialY, size, size);
            gameBatch.draw(textureNoHeart, initialX + size * 2 + gap * 2, initialY, size, size);
        }else if(life==1){
            gameBatch.draw(textureHeart, initialX, initialY, size, size);
            gameBatch.draw(textureNoHeart, initialX + size + gap, initialY, size, size);
            gameBatch.draw(textureNoHeart, initialX + size * 2 + gap * 2, initialY, size, size);
        }


            score = "Saved: " + scoreCounter.getCount();



        dodgeCounter = charEntity.timeDogdeAvaiable + "s";



        font.draw(gameBatch,score,Dimen.GAME_WORLD_WIDTH-scoreWidth,Dimen.GAME_WORLD_HEIGHT-scoreHeight);

        if(scoreCounter.getScoreMultiplier() > 0){
            font.draw(gameBatch,scoreCounter.getScoreMultiplier() + "X",Dimen.GAME_WORLD_WIDTH-scoreWidth-doubleScoreWidth-gap,Dimen.GAME_WORLD_HEIGHT-scoreHeight);

        }

        if(charEntity.timeDogdeAvaiable < 1){
            font.draw(gameBatch,"Dodge rdy",0+ 10,0+30);
        }else{
            font.draw(gameBatch,dodgeCounter,0+ 10,0+30);
        }

        gameBatch.end();


        if(!charEntity.dodgeMove) {
            for (int i = 0; i < firBallManager.getList().size; i++) {



                Rectangle rectFireball = firBallManager.getList().get(i).sprite.getBoundingRectangle();
                rectFireball.setSize(rectFireball.getWidth() * 0.8f, rectFireball.getHeight() * 0.8f);


                if (rectChar.overlaps(rectFireball)) {




                    if (firBallManager.getList().get(i).getType() == BallType.Fireball) {

                        charEntity.isHit = true;

                        util.decreaseLifeCounter();

                        charEntity.zeile += 3;


                        //TODO: check if this is correct and ends everytime with normal sprite + make char in this time invincible
                        Timer hitChar = new Timer();



                        hitChar.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {

                                    charEntity.isHit = false;


                            }
                        }, 2);


                        if (util.getLifeCounter() == 0) {
                            util.setGameOver();
                        }
                    }else if(firBallManager.getList().get(i).getType() == BallType.DoubleScore){

                        scoreCounter.doubleScore();

                    }else if(firBallManager.getList().get(i).getType() == BallType.HealthUp){

                        util.increaseLifeCounter();
                    }

                    firBallManager.getList().removeIndex(i);
                }


            }
        }

        if(util.getGameOver()){
            finishGame();
        }


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        charEntity.getSpriteChar().getTexture().dispose();
        this.dispose();
    }


    public void finishGame(){
        charEntity.move.clear();
        firBallManager.getSpawn().clear();

        firBallManager.getList().clear();
        scoreCounter.stopScoreCount();

        game.setScreen(new GameOverScreen(game,util,scoreCounter.getCount()));
    }


    public void determineLevel(){

        Timer level = new Timer();

        level.scheduleTask(new Timer.Task() {
            @Override
            public void run() {

                firBallManager.increaseLevel();
            }
        },0,20f);

    }

    public void createFont(){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
        font.setColor(Color.BLACK);

    }



}
