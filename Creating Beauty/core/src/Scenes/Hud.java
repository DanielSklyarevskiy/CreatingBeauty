package Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.gamerowo.beauty.CreatingBeauty;

public class Hud {
    private Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label countDownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label livesLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(CreatingBeauty.getWorldWidth(), CreatingBeauty.getWorldHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label(String.format("TIME", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));;
        levelLabel = new Label(String.format("1-1", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));;
        worldLabel = new Label(String.format("WORLD", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));;
        livesLabel = new Label(String.format("LIVES", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));;

        table.add(livesLabel).expandX().padTop(5);
        table.add(worldLabel).expandX().padTop(5);
        table.add(timeLabel).expandX().padTop(5);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();

        stage.addActor(table);
    }

    //getters
    public Stage getStage() {
        return stage;
    }
}
