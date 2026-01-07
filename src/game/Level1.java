/**
 * The Level1 class represents the first level of Happy Chicken.
 * <p>
 * In this level, the player has to control a chicken that must collect eggs while avoiding fire.
 * This level contains multiple static bodies (ground and borders), collectible Egg objects, dangerous Fire obstacles, special QueenEgg collectibles, and a ChickenNest that leads to the next level.
 * <p>
 * Fire destroys eggs and end the game if the chicken touches them.
 * Eggs are placed in multiple rows throughout the level.
 * collision listeners are set up to detect interactions with the fires.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Level1 extends GameLevel {
    private Egg egg;
    private Fire fire;
    private QueenEgg queenEgg;
    private ChickenNest chickenNest;

    /**
     * Constructs the first level of the game.
     * Sets up platforms, fires, eggs, and collision logic.
     *
     * @param game the main game instance controlling the game state and transitions
     */
    public Level1(Game game) {
        super(game);

        //make a ground platform
        Shape shape = new BoxShape(30, 5f);
        StaticBody ground = new StaticBody(this, shape);
        ground.setPosition(new Vec2(0f, -116f));
        ground.addImage(new BodyImage("data/grass.jpeg", 10));

        //make side borders for the game
        Shape borderShape = new BoxShape(7.5f, 150f);
        StaticBody border1 = new StaticBody(this, borderShape);
        border1.setPosition(new Vec2(40, 0));
        border1.addImage(new BodyImage("data/border1.jpg", 222));

        StaticBody border2 = new StaticBody(this, borderShape);
        border2.setPosition(new Vec2(-40, 0));
        border2.addImage(new BodyImage("data/border1.jpg", 222));

        //add in a chicken character
        getChicken().setPosition(new Vec2(0, 110.5f));
        //getChicken().setPosition(new Vec2(0, -100.5f)); //land chicken straight beside the nest


        //looping of eggs
        for (int j=0; j<10; j++) {
            for (int i = 0; i < 3; i++) {
                egg = new Egg(this);
                egg.setPosition(new Vec2(-15 + (15 * i), 100-(20*j)));
                egg.setGravityScale(0);
            }
        }

        for (int j=0; j<10; j++) {
            for (int i = 0; i < 4; i++) {
                egg = new Egg(this);
                egg.setPosition(new Vec2(-22 + (15 * i), 90-(20*j)));
                egg.setGravityScale(0);
            }
        }


        //adding fires
        fire = new Fire(this);
        fire.setPosition(new Vec2(-8, 90));
        fire.addCollisionListener(new FireCollisions(fire, game));

        fire = new Fire(this);
        fire.setPosition(new Vec2(15, 45));
        fire.addCollisionListener(new FireCollisions(fire, game));

        fire = new Fire(this);
        fire.setPosition(new Vec2(0, 0));
        fire.addCollisionListener(new FireCollisions(fire, game));

        fire = new Fire(this);
        fire.setPosition(new Vec2(20, -40));
        fire.addCollisionListener(new FireCollisions(fire, game));

        fire = new Fire(this);
        fire.setPosition(new Vec2(-14, -72));
        fire.addCollisionListener(new FireCollisions(fire, game));


        //add queen eggs
        queenEgg = new QueenEgg(this);
        queenEgg.setPosition(new Vec2(0 , 10));
        queenEgg.setGravityScale(0);

        queenEgg = new QueenEgg(this);
        queenEgg.setPosition(new Vec2(-15 , -15));
        queenEgg.setGravityScale(0);

        queenEgg = new QueenEgg(this);
        queenEgg.setPosition(new Vec2(15 , -30));
        queenEgg.setGravityScale(0);

        queenEgg = new QueenEgg(this);
        queenEgg.setPosition(new Vec2(15 , -74));
        queenEgg.setGravityScale(0);


        //add in chicken pen to proceed to the next level
        chickenNest = new ChickenNest(this);
        chickenNest.setPosition(new Vec2(15, -108.5f));


        //add in collision listener for chicken character
        ChickenCollisions chickenCollisions = new ChickenCollisions(getChicken(), game);
        getChicken().addCollisionListener(chickenCollisions);
    }

    /**
     * Determines if the level is complete.
     * <p>
     * This method always returns false as the level has no built-in condition to end the level.
     *
     * @return false always
     */
    public boolean isComplete(){
        return false;
    }

    /**
     * Checks whether the game is over by determining if the chicken is roasted.
     *
     * @return true if the chicken is roasted, false otherwise
     */
    public boolean isGameOver(){
        if (getChicken().roasted()) {
            return true;
        } else {
            return false;
        }
    }
}
