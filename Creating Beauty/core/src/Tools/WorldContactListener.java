package Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gamerowo.beauty.CreatingBeauty;
import com.gamerowo.beauty.Sprites.Enemy;
import com.gamerowo.beauty.Sprites.InteractiveTileObject;
import com.gamerowo.beauty.Sprites.Player;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact){
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        switch (cDef) {
            case CreatingBeauty.ENEMY_HEAD_BIT | CreatingBeauty.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == CreatingBeauty.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead((Player) fixB.getUserData());
                else
                    ((Enemy) fixB.getUserData()).hitOnHead((Player) fixA.getUserData()); //creashes when dashing into koopa shell
                break;
            case CreatingBeauty.ENEMY_BIT | CreatingBeauty.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == CreatingBeauty.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case CreatingBeauty.PLAYER_BIT | CreatingBeauty.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == CreatingBeauty.PLAYER_BIT)
                    ((Player) fixA.getUserData()).hit((Enemy) fixB.getUserData());
                else
                    ((Player) fixB.getUserData()).hit((Enemy) fixA.getUserData());
                break;
            case CreatingBeauty.PLAYER_BIT | CreatingBeauty.GROUND_BIT:
            case CreatingBeauty.PLAYER_BIT | CreatingBeauty.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == CreatingBeauty.PLAYER_BIT)
                    ((Player) fixA.getUserData()).setJumpsRemaining(1);
                else if (((Player)fixB.getUserData()).getJumpsRemaining() == 0)
                    ((Player) fixB.getUserData()).setJumpsRemaining(1);
                break;
            case CreatingBeauty.ENEMY_BIT | CreatingBeauty.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).onEnemyHit((Enemy) fixB.getUserData());
                ((Enemy) fixB.getUserData()).onEnemyHit((Enemy) fixA.getUserData());
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
