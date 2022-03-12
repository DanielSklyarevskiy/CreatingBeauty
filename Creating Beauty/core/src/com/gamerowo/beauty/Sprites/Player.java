package com.gamerowo.beauty.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gamerowo.beauty.CreatingBeauty;
import com.gamerowo.beauty.Screens.PlayScreen;

public class Player extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD};
    public State currentState;
    public State previousState;
    private World world;
    private Body b2Body;
    private TextureRegion aamirahStand;
    private TextureRegion aamirahDead;
    private TextureRegion aamirahJump;
    private Animation aamirahRun;
    private TextureRegion dannyStand;
    private TextureRegion dannyDead;
    private TextureRegion dannyJump;
    private Animation dannyRun;
    private int jumpsRemaining;
    private int dashesRemaining;
    private final float dashSpeed;
    private float stateTimer;
    private boolean runningRight;
    private boolean isDead;

    public Player(PlayScreen screen){
        super(screen.getPlayerAtlas().findRegion("PlayerAnimations"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        jumpsRemaining = 1;
        dashesRemaining = 2;
        dashSpeed = 1.3f;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 3; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 32 + 8, 0, 32, 42));
        aamirahRun = new Animation(0.1f, frames);
        frames.clear();

        aamirahStand = new TextureRegion(getTexture(), 32, 0, 32, 42);

        aamirahJump = new TextureRegion(getTexture(), 64, 0, 32, 42);

        aamirahDead = new TextureRegion(getTexture(), 0, 0, 32, 42);

        //Array<TextureRegion> frames = new Array<TextureRegion>();
//        for (int i = 3; i < 6; i++)
//            frames.add(new TextureRegion(getTexture(), i * 32 + 8, 0, 32, 42));
//        aamirahRun = new Animation(0.1f, frames);
//        frames.clear();
//
//        aamirahStand = new TextureRegion(getTexture(), 32, 0, 32, 42);
//
//        aamirahJump = new TextureRegion(getTexture(), 256, 0, 32, 42);
//
//        aamirahDead = new TextureRegion(getTexture(), 0, 0, 32, 42);

        definePlayer();
        setBounds(0, 0, 24 / CreatingBeauty.getPPM(), 24 / CreatingBeauty.getPPM());
        setRegion(aamirahStand);
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
                region = aamirahJump;
                break;
            case RUNNING:
                region = (TextureRegion) aamirahRun.getKeyFrame(stateTimer, true);
                break;
            case DEAD:
                region = aamirahDead;
                break;
            default:
                region = aamirahStand;
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
        if(isDead)
            return State.DEAD;
        else if(b2Body.getLinearVelocity().y > 0 || (b2Body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2Body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2Body.getLinearVelocity().x != 0)
            return State.RUNNING;
        return State.STANDING;
    }

    public void definePlayer(){
        BodyDef bDef = new BodyDef();
        bDef.position.set(120 / CreatingBeauty.getPPM(), 50 / CreatingBeauty.getPPM());
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / CreatingBeauty.getPPM());
        fDef.filter.categoryBits = CreatingBeauty.PLAYER_BIT;
        fDef.filter.maskBits = CreatingBeauty.GROUND_BIT | CreatingBeauty.COIN_BIT | CreatingBeauty.BRICK_BIT |
                               CreatingBeauty.OBJECT_BIT | CreatingBeauty.ENEMY_BIT | CreatingBeauty.ENEMY_HEAD_BIT;

        fDef.shape = shape;
        b2Body.createFixture(fDef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / CreatingBeauty.getPPM(), 6 / CreatingBeauty.getPPM()), new Vector2(2 / CreatingBeauty.getPPM(), 6 / CreatingBeauty.getPPM()));
        fDef.shape = head;
        fDef.isSensor = true;

        b2Body.createFixture(fDef).setUserData("head");
    }

    public void hit(Enemy enemy){
        if(enemy instanceof Koopa && ((Koopa)enemy).getCurrentState() == Koopa.State.STANDING_SHELL){
            ((Koopa) enemy).kick(this.getX() <= enemy.getX() ? Koopa.KICK_RIGHT_SPEED : Koopa.KICK_LEFT_SPEED);
        }
        else {
            CreatingBeauty.manager.get("audio/music/mario_music.ogg", Music.class).stop();
            CreatingBeauty.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            isDead = true;
            Filter filter = new Filter();
            filter.maskBits = CreatingBeauty.NOTHING_BIT;
            for (Fixture fixture: b2Body.getFixtureList())
                fixture.setFilterData(filter);
            b2Body.applyLinearImpulse(new Vector2(0, 4f), b2Body.getWorldCenter(), true);
        }
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

    public boolean isDead(){
        return isDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public float getDashSpeed() {
        return dashSpeed;
    }

    public int getJumpsRemaining() {
        return jumpsRemaining;
    }

    public void setJumpsRemaining(int jumpsRemaining) {
        this.jumpsRemaining = jumpsRemaining;
    }

    public int getDashesRemaining() {
        return dashesRemaining;
    }

    public void setDashesRemaining(int dashesRemaining) {
        this.dashesRemaining = dashesRemaining;
    }
}
