package com.gamerowo.beauty.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.gamerowo.beauty.CreatingBeauty;
import com.gamerowo.beauty.Screens.PlayScreen;


public class Refresher extends InteractiveTileObject{
    private Player player;
    public boolean isActive = true;
    private TiledMapTileSet set;
    public float refresherTimeCount;

    private final int BLANK_COIN = 28;

    public Refresher(PlayScreen screen, Rectangle bounds, Player player){
        super(screen, bounds);
        set = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(CreatingBeauty.REFRESHER_BIT);
    }

    @Override
    public void onHeadHit() {
    }

    public void update(float dt){
        if(refresherTimeCount >= 1) refresherTimeCount += dt;
        else if (!isActive){
            isActive = true;
            //getCell().setTile(set.getTile(BLANK_COIN));
        }
    }
}
