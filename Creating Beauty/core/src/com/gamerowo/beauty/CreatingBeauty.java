package com.gamerowo.beauty;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gamerowo.beauty.Screens.Cutscene;
import com.gamerowo.beauty.Screens.EndScreen;
import com.gamerowo.beauty.Screens.GameStartScreen;
import com.gamerowo.beauty.Screens.PlayScreen;
import java.util.*;

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
	public static final short HOLMER_BIT = 4096;

	private SpriteBatch batch;

	public static AssetManager manager;
	public static int currentLevel = -1;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/music/kirby_music.mp3", Music.class);
		manager.load("audio/music/mario_three_music.mp3", Music.class);
		manager.load("audio/music/cave_music.mp3", Music.class);
		manager.load("audio/music/robobot.mp3", Music.class);
		manager.load("audio/music/sans.mp3", Music.class);
		manager.load("audio/sounds/goomba_sound.mp3", Sound.class);
		manager.load("audio/sounds/koopa_sound.mp3", Sound.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.load("audio/sounds/mariodie.wav", Sound.class);
		manager.load("audio/sounds/levelComplete.mp3", Sound.class);
		manager.load("audio/voices/0000.mp3", Sound.class);
		manager.load("audio/voices/0001.mp3", Sound.class);
		manager.load("audio/voices/0002.mp3", Sound.class);
		manager.load("audio/voices/0003.mp3", Sound.class);
		manager.load("audio/voices/0100.mp3", Sound.class);
		manager.load("audio/voices/0101.mp3", Sound.class);
		manager.load("audio/voices/0102.mp3", Sound.class);
		manager.load("audio/voices/0103.mp3", Sound.class);
		manager.load("audio/voices/0104.mp3", Sound.class);
		manager.load("audio/voices/0105.mp3", Sound.class);
		manager.load("audio/voices/0106.mp3", Sound.class);
		manager.load("audio/voices/0200.mp3", Sound.class);
		manager.load("audio/voices/0201.mp3", Sound.class);
		manager.load("audio/voices/0202.mp3", Sound.class);
		manager.load("audio/voices/0203.mp3", Sound.class);
		manager.load("audio/voices/0204.mp3", Sound.class);
		manager.load("audio/voices/0205.mp3", Sound.class);
		manager.load("audio/voices/0206.mp3", Sound.class);
		manager.load("audio/voices/0207.mp3", Sound.class);
		manager.load("audio/voices/0208.mp3", Sound.class);
		manager.load("audio/voices/0300.mp3", Sound.class);
		manager.load("audio/voices/0301.mp3", Sound.class);
		manager.load("audio/voices/0302.mp3", Sound.class);
		manager.load("audio/voices/0303.mp3", Sound.class);
		manager.load("audio/voices/0304.mp3", Sound.class);
		manager.load("audio/voices/0305.mp3", Sound.class);
		manager.load("audio/voices/0306.mp3", Sound.class);
		manager.load("audio/voices/0307.mp3", Sound.class);
		manager.load("audio/voices/0308.mp3", Sound.class);
		manager.load("audio/voices/0309.mp3", Sound.class);
		manager.load("audio/voices/0310.mp3", Sound.class);
		manager.load("audio/voices/0400.mp3", Sound.class);
		manager.load("audio/voices/0401.mp3", Sound.class);
		manager.load("audio/voices/0402.mp3", Sound.class);
		manager.load("audio/voices/0403.mp3", Sound.class);
		manager.load("audio/voices/0404.mp3", Sound.class);
		manager.load("audio/voices/0405.mp3", Sound.class);

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
