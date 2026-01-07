/**
 * Represents a collectible golden egg in the game.
 * <p>
 * The golden egg is a static body that appears at the end of Level3 when the player successfully extinguishes the final fire.
 * Collecting this golden egg signifies that the player has completed and won the game.
 * <p>
 * Only one golden egg can be collected in a game session.
 * This is tracked using a static flag.
 * <p>
 * The golden egg is represented by a box shape and a custom image.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;

public class GoldEgg extends StaticBody{

    private static final Shape eggShape = new BoxShape(2.5f,4f);

    private static final BodyImage image = new BodyImage("data/goldEgg.png", 8f);

    /**
     * Constructs a new GoldEgg in the specified world.
     *
     * @param world the game world where the gold egg will be placed
     */
    public GoldEgg(World world) {
        super(world, eggShape);
        addImage(image);
    }

    private static boolean goldEggCollected = false;

    public static void setGoldEggCollected() {
        goldEggCollected = true;
    }
    public static boolean isGoldEggCollected() {
        return goldEggCollected;
    }
}
