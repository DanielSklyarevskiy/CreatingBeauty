package com.gamerowo.beauty.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

import com.gamerowo.beauty.Sprites.Checkpoint;
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
    public Texture checkpointTexture = new Texture("beauty/b.png");;
    public Texture refresherTexture = new Texture("refresher.png");;

    private Music music;

    public PlayScreen(CreatingBeauty game){
        marioAtlas = new TextureAtlas("Mario_and_Enemies.pack");
        playerAtlas = new TextureAtlas("Player.pack");

        this.game = game;
        cam = new OrthographicCamera();
        port = new FitViewport(CreatingBeauty.getWorldWidth() / CreatingBeauty.getPPM(), CreatingBeauty.getWorldHeight() / CreatingBeauty.getPPM(), cam);
        hud = new Hud(game.getBatch());

        startLevel();
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
            if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
                player.setIsAamirah(!player.getIsAamirah());
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.C) && !player.getIsAamirah() && player.getDashesRemaining() > 0) {
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
            CreatingBeauty.currentLevel++;
            startLevel();
        }
    }
    public void update(float dt){

        handleInput(dt);

        world.step(1/60f, 6, 2);

        player.update(dt);
        for (Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            enemy.b2Body.setActive(true);
        }
        hud.update(dt);
        for (Refresher refresher : creator.getRefreshers()){
            refresher.update(dt, game.getBatch());
        }

        if(player.currentState != Player.State.DEAD){
            cam.position.x = player.getB2Body().getPosition().x;
        }

        cam.update();

        renderer.setView(cam);
    }

    public void startLevel(){
        if(music != null) {
            music.stop();
        }
        if(CreatingBeauty.currentLevel == 0) {
            checkpointTexture = new Texture("beauty/b.png");
            mapLoader = new TmxMapLoader();
            map = mapLoader.load("Mario.tmx");
            music = CreatingBeauty.manager.get("audio/music/mario_music.ogg", Music.class);

        }
        else if(CreatingBeauty.currentLevel == 1) {
            checkpointTexture = new Texture("beauty/e.png");
            mapLoader = new TmxMapLoader();
            map = mapLoader.load("Mario3.tmx");
            music = CreatingBeauty.manager.get("audio/music/mario_three_music.mp3", Music.class);
        }
        else if(CreatingBeauty.currentLevel == 2) {
            checkpointTexture = new Texture("beauty/a.png");
            mapLoader = new TmxMapLoader();
            map = mapLoader.load("Kirby.tmx");
            music = CreatingBeauty.manager.get("audio/music/kirby_music.mp3", Music.class);
        }
        else if(CreatingBeauty.currentLevel == 3) {
            checkpointTexture = new Texture("beauty/t.png");
            mapLoader = new TmxMapLoader();
            map = mapLoader.load("Cave.tmx");
            music = CreatingBeauty.manager.get("audio/music/cave_music.mp3", Music.class);
        }
        renderer = new OrthogonalTiledMapRenderer(map, 1 / CreatingBeauty.getPPM());
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        player = new Player(this);
        creator = new B2WorldCreator(this, player);

        world.setContactListener(new WorldContactListener());

        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //b2dr.render(world, cam.combined);

        game.getBatch().setProjectionMatrix(cam.combined);
        game.getBatch().begin();
        player.draw(game.getBatch());
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.getBatch());
        for (Checkpoint checkpoint : creator.getCheckpoints()) {
            game.getBatch().draw(checkpointTexture, checkpoint.boundX/ CreatingBeauty.getPPM(), checkpoint.boundY/ CreatingBeauty.getPPM(), 0.3f, 0.3f);
        }
        for (Refresher refresher: creator.getRefreshers()){
            if(refresher.isActive()) {
                game.getBatch().draw(refresherTexture, refresher.boundX / CreatingBeauty.getPPM(), refresher.boundY / CreatingBeauty.getPPM(), 0.2f, 0.2f);
            }
        }
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

    public Music getMusic() {
        return music;
    }

}
