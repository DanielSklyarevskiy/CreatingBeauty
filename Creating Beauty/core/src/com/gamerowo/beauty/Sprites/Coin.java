package com.gamerowo.beauty.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gamerowo.beauty.CreatingBeauty;
import com.gamerowo.beauty.Scenes.Hud;
import com.gamerowo.beauty.Screens.PlayScreen;

public class Coin extends InteractiveTileObject{
    private static TiledMapTileSet set;
    private final int BLANK_COIN = 28;
    public Coin(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        TmxMapLoader mapLoader = new TmxMapLoader();
        set = mapLoader.load("Mario.tmx").getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(CreatingBeauty.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        System.out.println(set);
        if(getCell().getTile().getId() == BLANK_COIN)
            CreatingBeauty.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else {
            Hud.addScore(200);
            CreatingBeauty.manager.get("audio/sounds/coin.wav", Sound.class).play();
        }
        getCell().setTile(set.getTile(BLANK_COIN));
    }
}
