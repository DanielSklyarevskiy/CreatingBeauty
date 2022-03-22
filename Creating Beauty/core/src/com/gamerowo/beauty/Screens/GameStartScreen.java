package com.gamerowo.beauty.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamerowo.beauty.CreatingBeauty;

public class GameStartScreen implements Screen{
    private Viewport viewport;
    private Stage stage;

    private Game game;

    public GameStartScreen(Game game){
        this.game = game;
        viewport = new FitViewport(CreatingBeauty.getWorldWidth(), CreatingBeauty.getWorldHeight(), new OrthographicCamera());
        stage = new Stage(viewport, ((CreatingBeauty) game).getBatch());

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameStartLabel = new Label("Start game", font);
        Label playGameLabel = new Label("Click to start", font);

        table.add(gameStartLabel).expandX();
        table.row();
        table.add(playGameLabel).expandX().padTop(10f);

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            game.setScreen(new PlayScreen((CreatingBeauty) game));
            //PlayScreen.setGameStart(true);
        }
        //dispose();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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