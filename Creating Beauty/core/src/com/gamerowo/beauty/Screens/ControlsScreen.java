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


public class ControlsScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    public ControlsScreen(Game game){
        this.game = game;
        viewport = new FitViewport(CreatingBeauty.getWorldWidth(), CreatingBeauty.getWorldHeight(), new OrthographicCamera());
        stage = new Stage(viewport, ((CreatingBeauty) game).getBatch());

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label l1 = new Label("Controls", font);
        Label l2 = new Label("Moving: Arrow Keys\nJump: Z\nSwitch characters: X\nDash: C", font);
        Label l3 = new Label("NOTE: Aamirah can kill enemies but can't dash,\nand Danny can dash but not kill enemies.", font);
        Label l4 = new Label("Press R to return to title", font);

        table.add(l1).expandX();
        table.row();
        table.add(l2).expandX().padTop(10f);
        table.row();
        table.add(l3).expandX().padTop(10f);
        table.row();
        table.add(l4).expandX().padTop(10f);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
            game.setScreen(new GameStartScreen((CreatingBeauty) game));
        }
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
