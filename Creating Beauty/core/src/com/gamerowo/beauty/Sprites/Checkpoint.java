package com.gamerowo.beauty.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.gamerowo.beauty.CreatingBeauty;
import com.gamerowo.beauty.Screens.PlayScreen;


public class Checkpoint extends InteractiveTileObject{
    private Texture texture;

    public Checkpoint(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(CreatingBeauty.CHECKPOINT_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
        screen.getMusic().stop();
        CreatingBeauty.manager.get("audio/sounds/levelComplete.mp3", Sound.class).play();
        CreatingBeauty.currentLevel++;
        screen.startLevel();
    }
}
