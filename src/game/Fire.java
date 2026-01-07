/**
 * Represents a static fire in the game.
 * <p>
 * The Fire is a non-moving obstacle that causes the chicken to be roasted on contact, resulting in a game over.
 * <p>
 * It is visualised using a custom image and a custom polygon shape.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;

public class Fire extends StaticBody {
    private static final Shape fireShape = new PolygonShape(-0.1f,4.95f, 5.99f,-2.3f, 5.91f,-3.72f, 4.62f,-4.76f, -4.27f,-4.89f, -6.09f,-3.35f, -5.56f,-1.28f);

    private static final BodyImage image = new BodyImage("data/fire.png", 10f);

    /**
     * Constructs a new Fire in the specified world.
     *
     * @param world the game world where the fire will be placed
     */
    public Fire(World world) {
        super(world, fireShape);
        addImage(image);
    }
}
