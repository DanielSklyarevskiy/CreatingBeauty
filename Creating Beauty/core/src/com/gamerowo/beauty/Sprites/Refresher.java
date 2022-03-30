package com.gamerowo.beauty.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.gamerowo.beauty.CreatingBeauty;
import com.gamerowo.beauty.Screens.PlayScreen;


public class Refresher extends InteractiveTileObject{
    private Player player;
    private boolean isActive = true;
    private Texture texture;
    public float refresherTimeCount;

    public float boundX;
    public float boundY;

    public Refresher(PlayScreen screen, Rectangle bounds, Player player){
        super(screen, bounds);
        texture = new Texture("refresher.png");
        fixture.setUserData(this);
        setCategoryFilter(CreatingBeauty.REFRESHER_BIT);
        this.player = player;

        boundX = bounds.getX() - bounds.getHeight() / 2;
        boundY = bounds.getY() - bounds.getWidth() / 2;
    }

    @Override
    public void onHeadHit() {
    }

    public void update(float dt, SpriteBatch batch){
        if(refresherTimeCount <= 3) refresherTimeCount += dt;
        else if (!isActive){
            isActive = true;
            setCategoryFilter(CreatingBeauty.REFRESHER_BIT);
        }
    }

    public void setPlayerDashes(int dashes){
        System.out.println("iwi");
        player.setDashesRemaining(dashes);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean s){
        isActive = s;
    }
}
