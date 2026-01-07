/**
 * Represents a fire that moves horizontally across the screen in the game.
 * <p>
 * It is a DynamicBody that moves slowly from right to left once the water bucket has been collected.
 * It registers itself as a StepListener to update its position every step of the simulation.
 * <p>
 * This is used in Level3 as the final fire obstacle the player must extinguish to win the game.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class LastMovingFire extends DynamicBody implements StepListener {
    private static final Shape lastMovingFireShape = new PolygonShape(-0.1f,4.95f, 5.99f,-2.3f, 5.91f,-3.72f, 4.62f,-4.76f, -4.27f,-4.89f, -6.09f,-3.35f, -5.56f,-1.28f);

    private static final BodyImage image = new BodyImage("data/fire.png", 10f);

    /**
     * Speed at which the fire moves left.
     */
    private float speed = 0.05f;

    /**
     * Constructs a new LastMovingFire in the specified world.
     *
     * @param world the game world where the fire will be placed
     */
    public LastMovingFire(World world) {
        super(world, lastMovingFireShape);
        addImage(image);
        world.addStepListener(this);
    }

    /**
     * Moves the fire horizontally towards the left at a constant speed.
     */
    public void move() {
        //get the current position of the fire
        Vec2 currentPosition = getPosition();

        //move the grass by the speed in the negative x-direction
        setPosition(new Vec2(currentPosition.x - speed, currentPosition.y));
    }

    /**
     * Called before each physics step; triggers movement if the water bucket has been collected.
     *
     * @param stepEvent the event triggered before the simulation step
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        if (WaterBucket.getCollectedBucket()) {
            move();
        }
    }

    /**
     * Called after each simulation step.
     *
     * @param stepEvent the event triggered after the simulation step
     */
    @Override
    public void postStep(StepEvent stepEvent) {
    }
}
