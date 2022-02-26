package com.gamerowo.beauty;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.gamerowo.beauty.Screens.PlayScreen;

public class CreatingBeauty extends Game {
	private static final int WORLD_WIDTH = 400;
	private static final int WORLD_HEIGHT = 208;
	private static final float PPM = 100;

	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	//getters
	public SpriteBatch getBatch() {
		return batch;
	}
	public static int getWorldWidth() {
		return WORLD_WIDTH;
	}
	public static int getWorldHeight() {
		return WORLD_HEIGHT;
	}
	public static float getPPM() { return PPM; }

	//setters
	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}
}
