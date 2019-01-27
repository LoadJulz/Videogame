package com.videojuego.game.GameEngine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.videojuego.game.AbstractScreen;
import com.videojuego.game.Dimen;

public class GameOverScreen extends AbstractScreen {

    GameUtil util;
    private TextButton retryButton;
    private Stage stage;
    Skin skin;
    int score;
    String text,highScoreText;
    Label label, highScoreLabel;
    float textWidth,textHeight,hightscoreWidth;

    BitmapFont font;

    public GameOverScreen(Game game, GameUtil util, int score) {
        super(game);
        this.util = util;
        this.score = score;
    }

    @Override
    public void show() {

        Preferences prefs = Gdx.app.getPreferences("My Preferences");


        if(prefs.getInteger("highscore")<score){
            prefs.putInteger("highscore", score);
            prefs.flush();
            highScoreText = "New Highscore: " + prefs.getInteger("highscore");
        }else{
            highScoreText = "Your Highscore: " + prefs.getInteger("highscore");
        }


        text = "You saved " + score +  " lifes.";

        createFont();


        GlyphLayout layout = new GlyphLayout(font, text);
        textWidth = layout.width;
        textHeight = layout.height;


        layout = new GlyphLayout(font, highScoreText);
        hightscoreWidth = layout.width;


        stage = new Stage();
        Group group = new Group();
        group.setBounds(0,0,Dimen.GAME_WORLD_WIDTH/2,Dimen.GAME_WORLD_HEIGHT/2);


        label = new Label(text, new Label.LabelStyle(font, Color.BLACK));
        label.setPosition(Dimen.GAME_WORLD_WIDTH/2-textWidth/2,Dimen.GAME_WORLD_HEIGHT/1.5f);

        highScoreLabel = new Label(highScoreText, new Label.LabelStyle(font, Color.BLACK));
        highScoreLabel.setPosition(Dimen.GAME_WORLD_WIDTH/2-hightscoreWidth/2,Dimen.GAME_WORLD_HEIGHT/1.5f - textHeight -20);

        skin = new Skin(Gdx.files.internal("Data/uiskin.json"));

        retryButton = new TextButton("Retry",skin);
        TextButton.TextButtonStyle style = retryButton.getStyle();
        style.font = util.getFont(40);
        retryButton.setStyle(style);
        retryButton.setSize(Dimen.GAME_WORLD_WIDTH/2,Dimen.GAME_WORLD_HEIGHT/5);
        retryButton.setPosition(Dimen.GAME_WORLD_WIDTH/2- retryButton.getWidth()/2,Dimen.GAME_WORLD_HEIGHT/2- retryButton.getHeight()/2 - 1);


        util.batch = new SpriteBatch();
        group.addActor(label);
        group.addActor(highScoreLabel);
        group.addActor(retryButton);



        stage.addActor(group);

        stage.getCamera().position.set(Dimen.GAME_WORLD_WIDTH/2,Dimen.GAME_WORLD_HEIGHT/2,0);



        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        util.batch.begin();


        stage.draw();

        util.batch.end();


        if(retryButton.isPressed()){
            game.setScreen(new GameScreen(game,util));
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
