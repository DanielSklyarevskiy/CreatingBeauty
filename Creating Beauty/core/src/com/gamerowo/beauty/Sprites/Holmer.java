package com.gamerowo.beauty.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.gamerowo.beauty.CreatingBeauty;
import com.gamerowo.beauty.Screens.PlayScreen;

import java.awt.geom.RectangularShape;

public class Holmer extends Enemy{
    private Texture texture;

    public Holmer(PlayScreen screen, float x, float y, Texture texture) {
        super(screen, x, y);
        this.texture = texture;
        setBounds(getX(), getY(), 250 / CreatingBeauty.getPPM(), 250 / CreatingBeauty.getPPM());
        velocity = new Vector2(1.6f, 0);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = BodyDef.BodyType.KinematicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(100 / CreatingBeauty.getPPM());
        fDef.filter.categoryBits = CreatingBeauty.HOLMER_BIT;
        fDef.filter.maskBits = CreatingBeauty.PLAYER_BIT;

        fDef.shape = shape;
        b2Body.createFixture(fDef).setUserData(this);
    }

    @Override
    public void update(float dt) {
        b2Body.setLinearVelocity(velocity);
        System.out.println(velocity);
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        setRegion(texture);
    }

    @Override
    public void hitOnHead(Player player) {

    }

    @Override
    public void onEnemyHit(Enemy enemy) {

    }
}
