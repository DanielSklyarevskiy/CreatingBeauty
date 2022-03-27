package com.gamerowo.beauty;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gamerowo.beauty.Screens.GameStartScreen;
import com.gamerowo.beauty.Screens.PlayScreen;

public class CreatingBeauty extends Game {
	private static final int WORLD_WIDTH = 400;
	private static final int WORLD_HEIGHT = 208;
	private static final float PPM = 100;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short CHECKPOINT_BIT = 256;
	public static final short REFRESHER_BIT = 512;
	public static final short TOP_BIT = 1024;
	public static final short ABYSS_BIT = 2048;

	private SpriteBatch batch;

	public static AssetManager manager;
	public static int currentLevel;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.load("audio/sounds/mariodie.wav", Sound.class);
		manager.finishLoading();

		setScreen(new GameStartScreen(this));
	}

	@Override
	public void render () { super.render(); }
	
	@Override
	public void dispose () {
		super.dispose();
		manager.dispose();
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
}
