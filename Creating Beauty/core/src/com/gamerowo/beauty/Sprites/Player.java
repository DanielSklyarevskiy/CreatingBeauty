package com.gamerowo.beauty.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gamerowo.beauty.CreatingBeauty;
import com.gamerowo.beauty.Screens.PlayScreen;

public class Player extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    private World world;
    private Body b2Body;
    private TextureRegion playerStand;
    private Animation playerRun;
    private Animation playerJump;
    private float stateTimer;
    private boolean runningRight;

    public Player(PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 10, 16, 16));
        playerRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 10, 16, 16));
        playerJump = new Animation(0.1f, frames);
        frames.clear();

        playerStand = new TextureRegion(getTexture(), 0, 10, 16, 16);

        definePlayer();
        setBounds(0, 0, 16 / CreatingBeauty.getPPM(), 16 / CreatingBeauty.getPPM());
        setRegion(playerStand);
    }

    public void update(float dt){
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = (TextureRegion) playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = playerStand;
                break;
        }

        if((b2Body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2Body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(b2Body.getLinearVelocity().y > 0 || (b2Body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2Body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2Body.getLinearVelocity().x != 0)
            return State.RUNNING;
        return State.STANDING;
    }

    public void definePlayer(){
        BodyDef bDef = new BodyDef();
        bDef.position.set(160 / CreatingBeauty.getPPM(), 32 / CreatingBeauty.getPPM());
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / CreatingBeauty.getPPM());
        fDef.filter.categoryBits = CreatingBeauty.PLAYER_BIT;
        fDef.filter.maskBits = CreatingBeauty.GROUND_BIT | CreatingBeauty.COIN_BIT | CreatingBeauty.BRICK_BIT |
                               CreatingBeauty.OBJECT_BIT | CreatingBeauty.ENEMY_BIT | CreatingBeauty.ENEMY_HEAD_BIT;

        fDef.shape = shape;
        b2Body.createFixture(fDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / CreatingBeauty.getPPM(), 6 / CreatingBeauty.getPPM()), new Vector2(2 / CreatingBeauty.getPPM(), 6 / CreatingBeauty.getPPM()));
        fDef.shape = head;
        fDef.isSensor = true;

        b2Body.createFixture(fDef).setUserData("head");
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
