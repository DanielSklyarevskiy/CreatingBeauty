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
                    ((Enemy) fixA.getUserData()).hitOnHead();
                else
                    ((Enemy) fixB.getUserData()).hitOnHead();
                break;
            case CreatingBeauty.ENEMY_BIT | CreatingBeauty.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == CreatingBeauty.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case CreatingBeauty.PLAYER_BIT | CreatingBeauty.ENEMY_BIT:
                Gdx.app.log("PLAYER", "DIED");
                break;
            case CreatingBeauty.ENEMY_BIT | CreatingBeauty.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
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
