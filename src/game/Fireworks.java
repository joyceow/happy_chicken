/**
 * Represents a visual firework effect displayed at the end of the game.
 * <p>
 * This class creates a static body that shows a celebratory animated GIF of fireworks.
 * It is used at the end of Level3 after the player has completed and won the game.
 * <p>
 * Fireworks do not interact physically with the player or the environment, but serves purely as a decorative element in the background.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;

public class Fireworks extends StaticBody {
    private static final Shape fireworkShape = new BoxShape(10,10);

    private static final BodyImage image = new BodyImage("data/fireworks.gif", 20f);

    /**
     * Constructs a new Fireworks in the specified world.
     * @param world the game world where fireworks is displayed
     */
    public Fireworks(World world) {
        super(world, fireworkShape);
        addImage(image);
    }
}
