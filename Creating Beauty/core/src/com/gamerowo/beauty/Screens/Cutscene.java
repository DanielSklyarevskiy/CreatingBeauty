package com.gamerowo.beauty.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamerowo.beauty.CreatingBeauty;

public class Cutscene implements Screen{
    private Viewport viewport;
    private Stage stage;
    private OrthographicCamera cam;

    public static int photoIndex = 0;
	public static Texture[] photos = {new Texture(Gdx.files.internal("CutscenePictures/DannyCryingInBed.jpg")),
									  new Texture(Gdx.files.internal("CutscenePictures/DannyHasAnIdea.jpg")),
									  new Texture(Gdx.files.internal("CutscenePictures/AamirahCoolPose.jpg")),
									  new Texture(Gdx.files.internal("CutscenePictures/DannyInCompSciRoom.jpg")),
									  new Texture(Gdx.files.internal("CutscenePictures/PortalAppears.jpg")),
									  new Texture(Gdx.files.internal("CutscenePictures/JumpingInPortal.jpg")),
									  new Texture(Gdx.files.internal("CutscenePictures/HolmerU.jpg")),
                                      new Texture(Gdx.files.internal("CutscenePictures/HolmerShocked.jpg")),
									  new Texture(Gdx.files.internal("CutscenePictures/HolmerBeauty.jpg")),
									  new Texture(Gdx.files.internal("CutscenePictures/GodJing.jpg"))};
	public static int sceneIndex = 0;
    public static int voiceIndex = 0;
    public static Sound[][] voicelines = {{ CreatingBeauty.manager.get("audio/voices/0000.mp3", Sound.class),
                                            CreatingBeauty.manager.get("audio/voices/0001.mp3", Sound.class),
                                            CreatingBeauty.manager.get("audio/voices/0002.mp3", Sound.class),
                                            CreatingBeauty.manager.get("audio/voices/0003.mp3", Sound.class)},
                                           {CreatingBeauty.manager.get("audio/voices/0100.mp3", Sound.class),
                                            CreatingBeauty.manager.get("audio/voices/0101.mp3", Sound.class),
                                            CreatingBeauty.manager.get("audio/voices/0102.mp3", Sound.class),
                                            CreatingBeauty.manager.get("audio/voices/0103.mp3", Sound.class),
                                            CreatingBeauty.manager.get("audio/voices/0104.mp3", Sound.class),
                                            CreatingBeauty.manager.get("audio/voices/0105.mp3", Sound.class),
                                            CreatingBeauty.manager.get("audio/voices/0106.mp3", Sound.class)}
	};
    private Game game;
    private Texture texture = photos[photoIndex];
    private SpriteBatch batch = new SpriteBatch();

    public Cutscene(Game game){
        this.game = game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(CreatingBeauty.getWorldWidth(),
                                        CreatingBeauty.getWorldHeight(), cam);
        stage = new Stage(viewport, ((CreatingBeauty) game).getBatch());
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setProjectionMatrix(cam.combined);
        batch.draw(texture, 0, 0, 40000 / CreatingBeauty.getPPM(), 22000 / CreatingBeauty.getPPM());
        batch.end();
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            voicelines[sceneIndex][voiceIndex].play();
            voiceIndex++;
            //photoIndex++;
            //game.setScreen(new Cutscene((CreatingBeauty) game));
        }
    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void show() {
        
    }

}