/**
 * Handles collision events for fire-related hazards in the game.
 * <p>
 * This class is responsible for managing interactions between other game objects and either Fire or LastMovingFire.
 * Specifically, it destroys eggs upon contact and tracks water projectile hits to extinguish the LastMovingFire.
 * Water projectiles ae counted, and after three hits, the lastMovingFire is destroyed and a GoldEgg is spawned.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import org.jbox2d.common.Vec2;

public class FireCollisions implements CollisionListener {

    private Fire fire;
    private int count = 1;
    private LastMovingFire lastMovingFire;
    private Game game;

    /**
     * Constructs a FireCollision listener for Fires.
     *
     * @param fire the fire object this listener is attached to
     * @param game the main game instance
     */
    public FireCollisions(Fire fire, Game game) {
        this.fire=fire;
        this.game = game;
    }

    /**
     * Constructs a FireCollision listener for the LastMovingFire.
     * @param lastMovingFire the final fire object to be extinguished
     * @param game the main game instance
     */
    public FireCollisions(LastMovingFire lastMovingFire, Game game) {
        this.lastMovingFire = lastMovingFire;
        this.game = game;
    }

    /**
     * Called automatically when a collision occurs with the associated fire body.
     * <p>
     * Handles different behaviours depending on what the fire collides with
     * <ul>
     *     <li>Destroys Egg objects immediately.</li>
     *     <li>Destroys WaterProjectile and increments the hit counter. After three hits, extinguishes the fire, plays a sound, and spawns a GoldEgg.</li>
     * </ul>
     *
     * @param collisionEvent the collision event details
     */
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Egg) {
            collisionEvent.getOtherBody().destroy();
        } else if (collisionEvent.getOtherBody() instanceof WaterProjectile) {
            if (count < 3){
                collisionEvent.getOtherBody().destroy();
                count++;
                System.out.println(count);
            } else if (count == 3){
                //extinguish fire
                collisionEvent.getOtherBody().destroy();
                lastMovingFire.destroy();
                QueenEgg.playYay(game);
                System.out.println("Fire extinguished! Good job!");

                //spawn gold egg after fire is extinguished
                GoldEgg goldEgg = new GoldEgg(game.getLevel());
                goldEgg.setPosition(new Vec2(5, -107.5f));
            }
        }
    }
}
