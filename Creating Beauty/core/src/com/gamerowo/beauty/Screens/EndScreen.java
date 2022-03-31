package com.gamerowo.beauty.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.gamerowo.beauty.Scenes.Hud;


public class EndScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    public EndScreen(Game game){
        this.game = game;
        viewport = new FitViewport(CreatingBeauty.getWorldWidth(), CreatingBeauty.getWorldHeight(), new OrthographicCamera());
        stage = new Stage(viewport, ((CreatingBeauty) game).getBatch());

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label w1 = new Label("Congrats!", font);
        Label w2 = new Label("Stats:", font);
        Label w3 = new Label("Time: " + Hud.getTimer(), font);
        Label w4 = new Label("Score: " + Hud.getScore(), font);

        table.add(w1).expandX();
        table.row();
        table.add(w2).expandX().padTop(10f);
        table.row();
        table.add(w3).expandX().padTop(10f);
        table.row();
        table.add(w4).expandX().padTop(10f);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
