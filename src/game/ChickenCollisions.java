/**
 * Handles collision events for the Chicken class character.
 * <p>
 * This class listens for interactions between the chicken and other objects in the game world such as eggs, queen eggs, fires, nests, and golden eggs,
 * and updates the game state accordingly - including score, level progression, and game over states.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import org.jbox2d.common.Vec2;

public class ChickenCollisions implements CollisionListener {
    private Chicken chicken;
    private Game game;

    /**
     * Constructs a new collision listener for the given chicken and game context.
     *
     * @param chicken the chicken instance to monitor for collisions
     * @param game the main game controller for state updates
     */
    public ChickenCollisions(Chicken chicken, Game game) {
        this.chicken=chicken;
        this.game=game;
    }

    /**
     * Handles logic for different object collisions involving the chicken.
     * <ul>
     *     <li>Egg / QueenEgg: Increments score and plays sound.</li>
     *     <li>Fire: Roasts the chicken and ends the game.</li>
     *     <li>Nest / Door: Advances to the next level.</li>
     *     <li>WaterBucket: Collects water and sets bucket flag to enable chicken to shoot water.</li>
     *     <li>GoldEgg: Triggers a win and celebration with fireworks. </li>
     * </ul>
     * @param collisionEvent the collision event detected by the engine
     */
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Egg){
            chicken.setCredits(chicken.getCredits()+1);
            collisionEvent.getOtherBody().destroy();
            Egg.playChirp(game);

        } else if (collisionEvent.getOtherBody() instanceof QueenEgg) {
            chicken.setCredits(chicken.getCredits()+2);
            collisionEvent.getOtherBody().destroy();
            QueenEgg.playYay(game);

            //apply an upward impulse to make the chicken bounce
            //get the current velocity of chicken
            Vec2 currentVelocity = chicken.getLinearVelocity();
            //set an upward velocity
            Vec2 bounceVelocity = new Vec2(currentVelocity.x, 30);
            //apply the velocity to the chicken
            chicken.setLinearVelocity(bounceVelocity);

        } else if (collisionEvent.getOtherBody() instanceof Fire || collisionEvent.getOtherBody() instanceof MovingFire || collisionEvent.getOtherBody() instanceof LastMovingFire) {
            game.getLevel().isGameOver();
            chicken.setRoasted();
            System.out.println("Oh no! You're cooked!");
            System.out.println("Game Over!");
            chicken.getWorld().stop();

        } else if (collisionEvent.getOtherBody() instanceof ChickenNest) {
            QueenEgg.playYay(game);
            game.goToNextLevel();

        } else if (collisionEvent.getOtherBody() instanceof Door && game.getLevel().isComplete()) {
            QueenEgg.playYay(game);
            game.goToNextLevel();

        } else if (collisionEvent.getOtherBody() instanceof WaterBucket) {
            collisionEvent.getOtherBody().destroy();
            QueenEgg.playYay(game);
            WaterBucket.setCollectedBucket();
            System.out.println("Water collected!");

        } else if (collisionEvent.getOtherBody() instanceof GoldEgg) {
            collisionEvent.getOtherBody().destroy();
            QueenEgg.playYay(game);
            System.out.println("Gold egg collected! You won!");

            ((Level3) game.getLevel()).clearGrass();
            GoldEgg.setGoldEggCollected();

            //trigger celebratory fireworks
            for (int i=0; i<3; i++) {
                Fireworks fireworks = new Fireworks(game.getLevel());
                fireworks.setPosition(new Vec2((-20)+(i*20), -99f));
            }
            game.getGUI().showGameOverButton();
        }

        System.out.println(chicken.getCredits());
    }
}
