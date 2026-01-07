/**
 * Represents a dynamic water projectile that can be launched by the player to extinguish fires in the game.
 * <p>
 * It moves in a given direction at a constant speed when launched.
 * The projectile is only triggered after the WaterBucket has been collected in Level3.
 * <p>
 * The water projectile is represented by a circle shape and a custom image.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class WaterProjectile extends DynamicBody {
    /**
     * The direction in which the projectile will travel.
     */
    private Vec2 direction;

    /**
     * Constant speed at which the projectile is launched.
     */
    private static final float SPEED = 10f;

    /**
     * Constructs a new WaterProjectile in the specified world at a given position and with a given movement direction.
     * The projectile is immediately assigned a linear velocity based on the direction.
     *
     * @param world the game world where the water projectile will be spawned
     * @param position the starting position of the projectile
     * @param direction the direction in which the projectile should move
     */
    public WaterProjectile(World world, Vec2 position, Vec2 direction) {
        super(world, new CircleShape(2));
        setPosition(position);
        this.direction = direction;
        setLinearVelocity(direction.mul(SPEED));
    }

    /**
     * Adds a visual representation of the projectile, using a custom image.
     * This is called after creation to display the water graphic.
     */
    public void launch() {
        this.addImage(new BodyImage("data/water.png", 10f));
    }
}

