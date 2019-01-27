package com.videojuego.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.videojuego.game.GameEngine.GameScreen;
import com.videojuego.game.GameEngine.GameUtil;

public class MenuScreen extends AbstractScreen {


    GameUtil util;
    private TextButton playButton;
    private Stage stage;
    Skin skin;
    float textWidth;

    Label label;
    BitmapFont font;

    public MenuScreen(Game game, GameUtil util) {
        super(game);
        this.util = util;

        createFont();

        util.camera = new OrthographicCamera(Dimen.GAME_WORLD_WIDTH,Dimen.GAME_WORLD_HEIGHT);
        util.camera.position.set(Dimen.GAME_WORLD_WIDTH/2,Dimen.GAME_WORLD_HEIGHT/2,0);

        GlyphLayout layout = new GlyphLayout(font, "The Loop");
        textWidth = layout.width;

        stage = new Stage();
        Group group = new Group();
        group.setBounds(0,0,Dimen.GAME_WORLD_WIDTH,Dimen.GAME_WORLD_HEIGHT);

        label = new Label("The Loop", new Label.LabelStyle(font, Color.BLACK));
        label.setPosition(Dimen.GAME_WORLD_WIDTH/2-textWidth/2,Dimen.GAME_WORLD_HEIGHT/1.4f);

        skin = new Skin(Gdx.files.internal("Data/uiskin.json"));

        playButton = new TextButton("Play",skin);

        TextButton.TextButtonStyle style = playButton.getStyle();
        style.font = util.getFont(40);
        playButton.setStyle(style);
        playButton.setSize(Dimen.GAME_WORLD_WIDTH/1.5f,Dimen.GAME_WORLD_HEIGHT/5);
        playButton.setPosition(Dimen.GAME_WORLD_WIDTH/2-playButton.getWidth()/2,Dimen.GAME_WORLD_HEIGHT/2-playButton.getHeight()/2);

        group.addActor(label);
        group.addActor(playButton);


        stage.addActor(group);

        stage.getCamera().position.set(Dimen.GAME_WORLD_WIDTH/2,Dimen.GAME_WORLD_HEIGHT/2,0);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show(){





    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();




        if(playButton.isPressed()){
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
        parameter.size = 40;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
        font.setColor(Color.BLACK);

    }
}
