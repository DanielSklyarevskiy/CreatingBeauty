package Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gamerowo.beauty.CreatingBeauty;
import com.gamerowo.beauty.Screens.PlayScreen;
import com.gamerowo.beauty.Sprites.Brick;
import com.gamerowo.beauty.Sprites.Coin;
import com.gamerowo.beauty.Sprites.Enemy;
import com.gamerowo.beauty.Sprites.Goomba;
import com.gamerowo.beauty.Sprites.Koopa;
import com.gamerowo.beauty.Sprites.Player;
import com.gamerowo.beauty.Sprites.Refresher;

public class B2WorldCreator {
    private Array<Goomba> goombas;
    private Array<Koopa> koopas;
    private Array<Refresher> refreshers;

    public B2WorldCreator(PlayScreen screen, Player player) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //ground
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / CreatingBeauty.getPPM(), (rect.getY() + rect.getHeight() / 2) / CreatingBeauty.getPPM());

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / CreatingBeauty.getPPM(), rect.getHeight() / 2 / CreatingBeauty.getPPM());
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //pipes
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / CreatingBeauty.getPPM(), (rect.getY() + rect.getHeight() / 2) / CreatingBeauty.getPPM());

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / CreatingBeauty.getPPM(), rect.getHeight() / 2 / CreatingBeauty.getPPM());
            fdef.shape = shape;
            fdef.filter.categoryBits = CreatingBeauty.OBJECT_BIT;
            body.createFixture(fdef);
        }
        //bricks
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Brick(screen, rect);
        }
        //coins
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(screen, rect);
        }
        //goombas
        goombas = new Array<Goomba>();
        for(MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new Goomba(screen, rect.getX() / CreatingBeauty.getPPM(), rect.getY() / CreatingBeauty.getPPM()));
        }
        //koopas
        koopas = new Array<Koopa>();
        for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            koopas.add(new Koopa(screen, rect.getX() / CreatingBeauty.getPPM(), rect.getY() / CreatingBeauty.getPPM()));
        }
        //refreshers
        refreshers = new Array<Refresher>();
        for(MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            refreshers.add(new Refresher(screen, rect, player));
        }
    }

    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(goombas);
        enemies.addAll(koopas);
        return enemies;
    }
    public Array<Refresher> getRefreshers(){
        return refreshers;
    }
}
