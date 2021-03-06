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
import com.gamerowo.beauty.Sprites.Refresher;

public class WorldContactListener implements ContactListener {

    Player player;
    public WorldContactListener(Player player){
        this.player = player;
    }

    @Override
    public void beginContact(Contact contact){
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case CreatingBeauty.ENEMY_HEAD_BIT | CreatingBeauty.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == CreatingBeauty.ENEMY_HEAD_BIT)
                    if((player).getIsAamirah())
                        ((Enemy) fixA.getUserData()).hitOnHead((Player) fixB.getUserData());
                    else
                        (player).hit((Enemy) fixA.getUserData());
                else
                    if((player).getIsAamirah()) //makes everything crash a lot
                        ((Enemy) fixB.getUserData()).hitOnHead((Player) fixA.getUserData());
                    else
                        (player).hit((Enemy) fixB.getUserData());
                break;
            case CreatingBeauty.ENEMY_BIT | CreatingBeauty.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == CreatingBeauty.ENEMY_BIT){
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);}
                else{
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);}
                break;
            case CreatingBeauty.PLAYER_BIT | CreatingBeauty.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == CreatingBeauty.PLAYER_BIT)
                    (player).hit((Enemy) fixB.getUserData());
                else
                    (player).hit((Enemy) fixA.getUserData());
                break;
            case CreatingBeauty.PLAYER_BIT | CreatingBeauty.BRICK_BIT:
            case CreatingBeauty.PLAYER_BIT | CreatingBeauty.COIN_BIT:
            case CreatingBeauty.PLAYER_BIT | CreatingBeauty.CHECKPOINT_BIT:
                if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
                    Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
                    Fixture object = head == fixA ? fixB : fixA;

                    if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                        ((InteractiveTileObject) object.getUserData()).onHeadHit();
                    }
                    else if (fixA.getFilterData().categoryBits == CreatingBeauty.PLAYER_BIT) {
                        (player).setJumpsRemaining(1);
                        (player).setDashesRemaining(2);
                    }
                    else {
                        (player).setJumpsRemaining(1);
                        (player).setDashesRemaining(2);
                    }
                    break;
                }
            case CreatingBeauty.PLAYER_BIT | CreatingBeauty.TOP_BIT:
                if (fixA.getFilterData().categoryBits == CreatingBeauty.PLAYER_BIT){
                    (player).setJumpsRemaining(1);
                    (player).setDashesRemaining(2);
                }
                else {
                    (player).setJumpsRemaining(1);
                    (player).setDashesRemaining(2);
                }
                break;
            case CreatingBeauty.ENEMY_BIT | CreatingBeauty.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).onEnemyHit((Enemy) fixB.getUserData());
                ((Enemy) fixB.getUserData()).onEnemyHit((Enemy) fixA.getUserData());
                break;
            case CreatingBeauty.PLAYER_BIT | CreatingBeauty.REFRESHER_BIT:
                if (fixA.getFilterData().categoryBits == CreatingBeauty.PLAYER_BIT){
                    ((Refresher) fixB.getUserData()).setPlayerDashes(2);
                    ((Refresher) fixB.getUserData()).setCategoryFilter(CreatingBeauty.NOTHING_BIT);
                    ((Refresher) fixB.getUserData()).setIsActive(false);
                    ((Refresher) fixB.getUserData()).refresherTimeCount = 0;
                }
                else {
                    ((Refresher) fixA.getUserData()).setPlayerDashes(2);
                    ((Refresher) fixA.getUserData()).setCategoryFilter(CreatingBeauty.NOTHING_BIT);
                    ((Refresher) fixA.getUserData()).setIsActive(false);
                    ((Refresher) fixA.getUserData()).refresherTimeCount = 0;
                }
                break;
            case CreatingBeauty.PLAYER_BIT | CreatingBeauty.ABYSS_BIT:
            case CreatingBeauty.PLAYER_BIT | CreatingBeauty.HOLMER_BIT:
                if (fixA.getFilterData().categoryBits == CreatingBeauty.PLAYER_BIT)
                    (player).die();
                else if (fixB.getFilterData().categoryBits == CreatingBeauty.PLAYER_BIT)
                    (player).die();
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
