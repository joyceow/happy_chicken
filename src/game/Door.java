/**
 * Represents a level exit door in the game.
 * <p>
 * The Door is a static body that serves as the destination for the chicken at the end of a level (Level2).
 * Reaching the door triggers a level transition in the game.
 * <p>
 * It is visualised using a custom image and has a predefined box shape.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;

public class Door extends StaticBody{

    private static final Shape doorShape = new BoxShape(2.5f,6);

    private static final BodyImage image = new BodyImage("data/door.png", 12f);

    /**
     * Constructs a new Door in the specified world.
     *
     * @param world the game world where the door will be placed
     */
    public Door(World world) {
        super(world, doorShape);
        addImage(image);
    }
}
