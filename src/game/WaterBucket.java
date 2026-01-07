/**
 * Represents a static water bucket that can be collected in the game to activate  certain game mechanics such as the shooting of water projectiles.
 * <p>
 * Once collected, it sets a static flag to signal its collection state.
 * <p>
 * The water bucket is represented by a box shape and a custom image.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;

public class WaterBucket extends StaticBody {
    private static final Shape bucketShape = new BoxShape(2,2);

    private static final BodyImage image = new BodyImage("data/waterBucket.png", 4.5f);

    /**
     * Constructs a new WaterBucket in the specified world.
     *
     * @param world the game world where the water bucket will be placed
     */
    public WaterBucket(World world) {
        super(world, bucketShape);
        addImage(image);
    }

    private static boolean collectedBucket = false;

    public static boolean setCollectedBucket() {
        return collectedBucket = true;
    }

    public static boolean getCollectedBucket() {
        return collectedBucket;
    }
}
