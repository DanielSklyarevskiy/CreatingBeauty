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
                                            CreatingBeauty.manager.get("audio/voices/0106.mp3", Sound.class)},
                    {CreatingBeauty.manager.get("audio/sounds/bump.wav", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0200.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0201.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0202.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0203.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0204.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0205.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0206.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0207.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0208.mp3", Sound.class)},
            {CreatingBeauty.manager.get("audio/sounds/bump.wav", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0300.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0301.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0302.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0303.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0304.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0305.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0306.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0307.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0308.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0309.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0310.mp3", Sound.class)},
            {CreatingBeauty.manager.get("audio/voices/0400.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0401.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0402.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0403.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0404.mp3", Sound.class),
                    CreatingBeauty.manager.get("audio/voices/0405.mp3", Sound.class)}
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
                if (voiceIndex < voicelines[sceneIndex].length) {
                    if (voicelines[sceneIndex][voiceIndex] != voicelines[sceneIndex][0])
                        voicelines[sceneIndex][voiceIndex - 1].stop();
                    voicelines[sceneIndex][voiceIndex].play();
                } else {
                    voicelines[sceneIndex][voiceIndex - 1].stop();
                    sceneIndex++;
                    voiceIndex = 0;
                    voicelines[sceneIndex][voiceIndex].play();
                }
                if (voiceIndex < voicelines[sceneIndex].length &&
                        (voicelines[sceneIndex][voiceIndex] == voicelines[0][3] ||
                                voicelines[sceneIndex][voiceIndex] == voicelines[1][0] ||
                                voicelines[sceneIndex][voiceIndex] == voicelines[1][2] ||
                                voicelines[sceneIndex][voiceIndex] == voicelines[1][6] ||
                                voicelines[sceneIndex][voiceIndex] == voicelines[2][4] ||
                                voicelines[sceneIndex][voiceIndex] == voicelines[2][7] ||
                                voicelines[sceneIndex][voiceIndex] == voicelines[3][1] ||
                                voicelines[sceneIndex][voiceIndex] == voicelines[3][5]))
                {
                    photoIndex++;
                    game.setScreen(new Cutscene((CreatingBeauty) game));
                } else if (voiceIndex < voicelines[sceneIndex].length &&
                        (voicelines[sceneIndex][voiceIndex] == voicelines[1][3] ||
                                voicelines[sceneIndex][voiceIndex] == voicelines[4][0])) {
                    photoIndex--;
                    game.setScreen(new Cutscene((CreatingBeauty) game));
                } else if (voiceIndex < voicelines[sceneIndex].length &&
                        (voicelines[sceneIndex][voiceIndex] == voicelines[1][4])) {
                    photoIndex += 2;
                    game.setScreen(new Cutscene((CreatingBeauty) game));
                }
                if(voiceIndex < voicelines[sceneIndex].length && (voicelines[sceneIndex][voiceIndex] == voicelines[2][0] ||
                        voicelines[sceneIndex][voiceIndex] == voicelines[3][0])){
                    voicelines[sceneIndex][voiceIndex].stop();
                    CreatingBeauty.currentLevel++;
                    game.setScreen(new PlayScreen((CreatingBeauty) game));
                }
                if(voiceIndex < voicelines[sceneIndex].length && voicelines[sceneIndex][voiceIndex] == voicelines[4][5]){
                    game.setScreen(new EndScreen((CreatingBeauty) game));
                }
                voiceIndex++;
                System.out.println(sceneIndex + " " + voiceIndex);
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