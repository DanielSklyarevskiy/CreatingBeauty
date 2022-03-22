package com.gamerowo.beauty.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamerowo.beauty.CreatingBeauty;

import com.gamerowo.beauty.Scenes.Hud;

import com.gamerowo.beauty.Sprites.Enemy;
import com.gamerowo.beauty.Sprites.Player;
import com.gamerowo.beauty.Sprites.Refresher;

import Tools.B2WorldCreator;
import Tools.WorldContactListener;

public class PlayScreen implements Screen {
    private CreatingBeauty game;
    private TextureAtlas marioAtlas;
    private TextureAtlas playerAtlas;
    private OrthographicCamera cam;
    private Viewport port;
    private Hud hud;
    private static boolean gameStart;

    //tilemap
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //box2d
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprites
    private Player player;

    private Music music;

    public PlayScreen(CreatingBeauty game){
        marioAtlas = new TextureAtlas("Mario_and_Enemies.pack");
        playerAtlas = new TextureAtlas("Player.pack");

        this.game = game;
        cam = new OrthographicCamera();
        port = new FitViewport(CreatingBeauty.getWorldWidth() / CreatingBeauty.getPPM(), CreatingBeauty.getWorldHeight() / CreatingBeauty.getPPM(), cam);
        hud = new Hud(game.getBatch());

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Mario.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / CreatingBeauty.getPPM());
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this, player);

        player = new Player(this);

        world.setContactListener(new WorldContactListener());

        music = CreatingBeauty.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
    }

    public TextureAtlas getMarioAtlas(){
        return marioAtlas;
    }
    public TextureAtlas getPlayerAtlas(){
        return playerAtlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(player.currentState != Player.State.DEAD){
            if(Gdx.input.isKeyJustPressed(Input.Keys.Z) && player.getJumpsRemaining() > 0){
                player.getB2Body().applyLinearImpulse(new Vector2(0, 4f), player.getB2Body().getWorldCenter(), true);
                player.setJumpsRemaining(player.getJumpsRemaining() - 1);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getB2Body().getLinearVelocity().x <= 2){
                player.getB2Body().applyLinearImpulse(new Vector2(0.1f, 0), player.getB2Body().getWorldCenter(), true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getB2Body().getLinearVelocity().x >= -2){
                player.getB2Body().applyLinearImpulse(new Vector2(-0.1f, 0), player.getB2Body().getWorldCenter(), true);
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
                player.setIsAamirah(!player.getIsAamirah());
                System.out.println(player.getIsAamirah());
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.X) && !player.getIsAamirah() && player.getDashesRemaining() > 0) {
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    player.getB2Body().setLinearVelocity(new Vector2(player.getDashSpeed() * 2.5f, player.getDashSpeed() * 2.5f));
                } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    player.getB2Body().setLinearVelocity(new Vector2(-player.getDashSpeed() * 2.5f, player.getDashSpeed() * 2.5f));
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    player.getB2Body().setLinearVelocity(new Vector2(player.getDashSpeed() * 2f, -player.getDashSpeed() * 2f));
                } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    player.getB2Body().setLinearVelocity(new Vector2(-player.getDashSpeed() * 2f, -player.getDashSpeed() * 2f));
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    player.getB2Body().setLinearVelocity(new Vector2(0, player.getDashSpeed() * 3));
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    player.getB2Body().applyLinearImpulse(new Vector2(0, -player.getDashSpeed() * 1.5f), player.getB2Body().getWorldCenter(), true);
                } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    player.getB2Body().applyLinearImpulse(new Vector2(-player.getDashSpeed(), 0), player.getB2Body().getWorldCenter(), true);
                } else {
                    player.getB2Body().applyLinearImpulse(new Vector2(player.getDashSpeed(), 0), player.getB2Body().getWorldCenter(), true);
                }
                player.setDashesRemaining(player.getDashesRemaining()-1);
            }
        }
    }
    public void update(float dt){

        handleInput(dt);

        world.step(1/60f, 6, 2);

        player.update(dt);
        for (Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224 / CreatingBeauty.getPPM())
                enemy.b2Body.setActive(true);
        }
        hud.update(dt);
        for (Refresher refresher : creator.getRefreshers()){
            refresher.update(dt);
        }

        if(player.currentState != Player.State.DEAD){
            cam.position.x = player.getB2Body().getPosition().x;
        }

        cam.update();

        renderer.setView(cam);
    }

    @Override
    public void render(float delta) {
        /*while(!gameStart){
            game.setScreen(new GameStartScreen(game));
        }
        //dispose();
        */
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, cam.combined);

        game.getBatch().setProjectionMatrix(cam.combined);
        game.getBatch().begin();
        player.draw(game.getBatch());
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.getBatch());
        game.getBatch().end();

        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    public boolean gameOver(){
        if(player.currentState == Player.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }
    public static boolean getGameStart(){
        return gameStart;
    }
    public static void setGameStart(boolean gS){
        gameStart = gS;
    }

    @Override
    public void resize(int width, int height) {
        port.update(width, height);
    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
