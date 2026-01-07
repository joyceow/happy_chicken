/**
 * Represents a dynamic fire that moves horizontally within a predefined range.
 * <p>
 * It extends DynamicBody and implements StepListener to enable real-time movement during the simulation steps.
 * When the chicken collides with a MovingFire, it gets roasted and the game ends.
 * It registers itself as a StepListener to update its position every step of the simulation.
 * <p>
 * Movement is automatically reversed once it exceeds its allowed distance in either direction.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class MovingFire extends DynamicBody implements StepListener {
    private static final Shape movingFireShape = new PolygonShape(-0.1f,4.95f, 5.99f,-2.3f, 5.91f,-3.72f, 4.62f,-4.76f, -4.27f,-4.89f, -6.09f,-3.35f, -5.56f,-1.28f);

    private static final BodyImage image = new BodyImage("data/fire.png", 10f);

    /**
     * Speed of horizontal movement.
     */
    private float speed = 0.6f;

    /**
     * Maximum distance the fire can move from its initial position before changing direction.
     */
    private float moveDistance = 20f;

    /**
     * Stores the initial position of the fire to calculate movement range.
     */
    private Vec2 initialPosition;

    /**
     * Constructs a new MovingFire in the specified world.
     * Initialises shape, image, and registers the object for simulation step updates.
     *
     * @param world the game world the fire belongs to
     */
    public MovingFire(World world) {
        super(world, movingFireShape);
        addImage(image);
        //store the initial position of the fire
        initialPosition = getPosition();
        //register for step events
        world.addStepListener(this);
    }

    /**
     * Handles the horizontal movement logic for the fire.
     * The fire will move back and forth between its starting position and moveDistance in the positive and negative direction.
     */
    public void move() {
        //get the current position of the fire
        Vec2 currentPosition = getPosition();

        //check if the grass has moved too far in the positive or negative direction and reverse direction
        if (currentPosition.x > initialPosition.x + moveDistance || currentPosition.x < initialPosition.x - moveDistance) {
            //reverse direction of fire
            speed = -speed;
        }

        //apply new position
        setPosition(new Vec2(currentPosition.x + speed, currentPosition.y));
    }

    /**
     * Called before each step in the simulation. Triggers the movement logic.
     *
     * @param stepEvent the event fired before each simulation
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        //call the move method during each simulation step
        move();
    }

    /**
     * Called after each step in the simulation. Not used in this class.
     *
     * @param stepEvent the event fired after each simulation step
     */
    @Override
    public void postStep(StepEvent stepEvent) {
    }
}
