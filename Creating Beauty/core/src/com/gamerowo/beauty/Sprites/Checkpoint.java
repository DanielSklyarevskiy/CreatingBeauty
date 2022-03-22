package com.gamerowo.beauty.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.gamerowo.beauty.CreatingBeauty;
import com.gamerowo.beauty.Screens.PlayScreen;


public class Checkpoint extends InteractiveTileObject{
    public Checkpoint(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(CreatingBeauty.CHECKPOINT_BIT);
    }

    @Override
    public void onHeadHit() {

    }
}
