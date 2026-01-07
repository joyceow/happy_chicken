/**
 * Represents the chicken's nest in the game.
 * <p>
 * The ChickenNest is a static body that serves as the destination for the chicken at the end of a level (Level1).
 * Reaching the nest triggers a level transition in the game.
 * <p>
 * It is visualised using a custom image and has a predefined box shape.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;

public class ChickenNest extends StaticBody{

    private static final Shape penShape = new BoxShape(5,2.5f);

    private static final BodyImage image = new BodyImage("data/chickenNest.png", 5.5f);

    /**
     * Constructs a new ChickenNest in the specified game world.
     *
     * @param world the game world where the nest will be placed
     */
    public ChickenNest(World world) {
        super(world, penShape);
        addImage(image);
    }
}
