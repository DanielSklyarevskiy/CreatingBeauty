package com.gamerowo.beauty.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.gamerowo.beauty.CreatingBeauty;
import com.gamerowo.beauty.Screens.PlayScreen;

public class Goomba extends Enemy{

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / CreatingBeauty.getPPM(), 16 / CreatingBeauty.getPPM());
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2Body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16));
        }
        else if(!destroyed){
            setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / CreatingBeauty.getPPM());
        fDef.filter.categoryBits = CreatingBeauty.ENEMY_BIT;
        fDef.filter.maskBits = CreatingBeauty.GROUND_BIT | CreatingBeauty.COIN_BIT | CreatingBeauty.BRICK_BIT |
                               CreatingBeauty.ENEMY_BIT | CreatingBeauty.PLAYER_BIT | CreatingBeauty.OBJECT_BIT;

        fDef.shape = shape;
        b2Body.createFixture(fDef);

        //Create the head
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / CreatingBeauty.getPPM());
        vertice[1] = new Vector2(5, 8).scl(1 / CreatingBeauty.getPPM());
        vertice[2] = new Vector2(-3, 3).scl(1 / CreatingBeauty.getPPM());
        vertice[3] = new Vector2(3, 3).scl(1 / CreatingBeauty.getPPM());
        head.set(vertice);

        fDef.shape = head;
        fDef.restitution = 0.5f;
        fDef.filter.categoryBits = CreatingBeauty.ENEMY_HEAD_BIT;
        b2Body.createFixture(fDef).setUserData(this);
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
    }
}
