package com.gamerowo.beauty.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.gamerowo.beauty.CreatingBeauty;
import com.gamerowo.beauty.Screens.PlayScreen;

public class Player extends Sprite {
    private World world;
    private Body b2Body;
    private TextureRegion playerStand;

    public Player(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        definePlayer();
        playerStand = new TextureRegion(getTexture(), 0, 10, 16, 16);
        setBounds(0, 0, 16 / CreatingBeauty.getPPM(), 16 / CreatingBeauty.getPPM());
        setRegion(playerStand);
    }

    public void update(float dt){
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    public void definePlayer(){
        BodyDef bDef = new BodyDef();
        bDef.position.set(200 / CreatingBeauty.getPPM(), 32 / CreatingBeauty.getPPM());
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / CreatingBeauty.getPPM());

        fDef.shape = shape;
        b2Body.createFixture(fDef);
    }

    //getters & setters
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Body getB2Body() {
        return b2Body;
    }

    public void setB2Body(Body b2Body) {
        this.b2Body = b2Body;
    }
}
