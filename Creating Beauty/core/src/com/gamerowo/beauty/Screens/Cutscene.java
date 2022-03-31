package com.gamerowo.beauty.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamerowo.beauty.CreatingBeauty;

public class Cutscene implements Screen{
    private Viewport viewport;
    private Stage stage;

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

    private Game game;
    private Texture texsture = photos[photoIndex];
    private SpriteBatch batch = new SpriteBatch();

    public Cutscene(Game game){
        this.game = game;
        viewport = new FitViewport(CreatingBeauty.getWorldWidth(), CreatingBeauty.getWorldHeight(), new OrthographicCamera());
        stage = new Stage(viewport, ((CreatingBeauty) game).getBatch());

        /*Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        
        //Label playGameLabel = new Label("Press space to start and R for the controls", font);

        //table.add(texsture);
        
        table.row();
        //table.add(playGameLabel).expandX().padTop(10f);

        stage.addActor(table);*/
    }

    @Override
    public void dispose() {
        batch.dispose();
        texsture.dispose();
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
        batch.draw(texsture, 0, 0, 1000, 750);
        batch.end();
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            photoIndex++;
            game.setScreen(new Cutscene((CreatingBeauty) game));
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