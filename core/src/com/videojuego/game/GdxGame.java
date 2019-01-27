package com.videojuego.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.videojuego.game.GameEngine.GameUtil;


public class GdxGame extends Game {

	public GdxGame game;
	GameUtil util;

	@Override
	public void create () {

		util = new GameUtil();



		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		this.game = this;







		setScreen(new MenuScreen(this,util));

	}

	@Override
	public void dispose () {

	}




}

