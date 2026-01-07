/**
 * Represents a static grass platform in the game.
 * <p>
 * This class creates a static body that is used as part of the scenery and for player interaction.
 * <p>
 * The egg is represented by a box shape and a custom image.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;

public class Grass extends StaticBody {
    private static final Shape grassShape = new BoxShape(3,1.5f);

    private static final BodyImage image = new BodyImage("data/grass.png", 4f);

    /**
     * Constructs a new Grass in the specified world.
     *
     * @param world the game world where the grass will be placed
     */
    public Grass(World world) {
        super(world, grassShape);
        addImage(image);
    }
}
