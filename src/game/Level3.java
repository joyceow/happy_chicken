/**
 * The Level3 class represents the third and final level of Happy Chicken.
 * <p>
 * This level features more advanced obstacles, including moving fires, a collectable water bucket that triggers the appearance of a golden egg, and multiple collectable eggs and queen eggs.
 * Players must avoid fires and collect eggs, and ultimately grab the golden egg to win.
 * <p>
 * Special game objects like MovingFire, lastMovingFire, and GoldEgg are introduced in this level, along with a dynamic grass list.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.util.List;
import java.util.ArrayList;

public class Level3 extends GameLevel {
    private Egg egg;
    private Fire fire;
    private QueenEgg queenEgg;
    private MovingFire movingFire;
    private Grass grass;
    private WaterBucket bucket;
    private LastMovingFire lastMovingFire;
    private GoldEgg goldEgg;
    private List<Grass> grassList = new ArrayList<>();

    /**
     * Constructs and initialises the third level of the game.
     * Populates the world with eggs, fires, grass platforms, the final extinguishable fire, and sets up win/loss conditions involving the gold egg and fire hazard.
     *
     * @param game the main game instance used to pass the game state
     */
    public Level3(Game game) {
        super(game);

        //make a ground platform
        Shape shape = new BoxShape(30, 5f);
        StaticBody ground = new StaticBody(this, shape);
        ground.setPosition(new Vec2(0f, -116f));
        ground.addImage(new BodyImage("data/grass.jpeg",10));

        //make side borders for the game
        Shape borderShape = new BoxShape(15f, 300f);
        StaticBody border1 = new StaticBody(this, borderShape);
        border1.setPosition(new Vec2(45, 0));
        border1.addImage(new BodyImage("data/border1.jpg", 424));

        StaticBody border2 = new StaticBody(this, borderShape);
        border2.setPosition(new Vec2(-45, 0));
        border2.addImage(new BodyImage("data/border1.jpg", 424));

        //add in a chicken character
        getChicken().setPosition(new Vec2(0, 115.5f));
        //getChicken().setPosition(new Vec2(-10, -90.5f)); //to land straight on the ground


        //looping of eggs
        for (int j=0; j<2; j++) {
            for (int i = 0; i < 3; i++) {
                egg = new Egg(this);
                egg.setPosition(new Vec2(-15 + (15 * i), 100-(20*j)));
                egg.setGravityScale(0);
            }
        }

        for (int j=0; j<2; j++) {
            for (int i = 0; i < 4; i++) {
                egg = new Egg(this);
                egg.setPosition(new Vec2(-22 + (15 * i), 90-(20*j)));
                egg.setGravityScale(0);
            }
        }

        for (int j=0; j<4; j++) {
            for (int i = 0; i < 4; i++) {
                egg = new Egg(this);
                egg.setPosition(new Vec2(-22 + (15 * i), 40-(20*j)));
                egg.setGravityScale(0);
            }
        }

        for (int j=0; j<4; j++) {
            for (int i = 0; i < 3; i++) {
                egg = new Egg(this);
                egg.setPosition(new Vec2(-15 + (15 * i), 30-(20*j)));
                egg.setGravityScale(0);
            }
        }

        for (int i = 0; i < 3; i++) {
            egg = new Egg(this);
            egg.setPosition(new Vec2(-15 + (15 * i), -50));
            egg.setGravityScale(0);
        }

        for (int j=0; j<2; j++) {
            for (int i = 0; i < 4; i++) {
                egg = new Egg(this);
                egg.setPosition(new Vec2(-22 + (15 * i), -60-(22*j)));
                egg.setGravityScale(0);
            }
        }


        //adding fires
        fire = new Fire(this);
        fire.setPosition(new Vec2(9, 90));
        fire.addCollisionListener(new FireCollisions(fire, game));

        movingFire = new MovingFire(this);
        movingFire.setPosition(new Vec2(0, 50));
        movingFire.setGravityScale(0);

        fire = new Fire(this);
        fire.setPosition(new Vec2(-14, 20));
        fire.addCollisionListener(new FireCollisions(fire, game));

        fire = new Fire(this);
        fire.setPosition(new Vec2(16, 0));
        fire.addCollisionListener(new FireCollisions(fire, game));

        movingFire = new MovingFire(this);
        movingFire.setPosition(new Vec2(-5, -40));
        movingFire.setGravityScale(0);

        movingFire = new MovingFire(this);
        movingFire.setPosition(new Vec2(5, -70));
        movingFire.setGravityScale(0);

        lastMovingFire = new LastMovingFire(this);
        lastMovingFire.setPosition(new Vec2(-10, -106f));
        lastMovingFire.setGravityScale(0);
        lastMovingFire.addCollisionListener(new FireCollisions(lastMovingFire, game));


        //add queen eggs
        for (int i = 0; i < 3; i++) {
            queenEgg = new QueenEgg(this);
            queenEgg.setPosition(new Vec2(-15 + (15 * i), 60));
            queenEgg.setGravityScale(0);
        }

        for (int i = 0; i < 4; i++) {
            queenEgg = new QueenEgg(this);
            queenEgg.setPosition(new Vec2(-22 + (15 * i), -30));
            queenEgg.setGravityScale(0);
        }


        //add in grass platform
        for (int i=0; i < 8; i++) {
            grass = new Grass(this);
            grass.setPosition(new Vec2(27-(i * 6), -99));
            // add to the list (so that they can be cleared in the end)
            grassList.add(grass);
        }


        //add in water bucket to collect
        bucket = new WaterBucket(this);
        bucket.setPosition(new Vec2(-27, -108.8f));

        if (WaterBucket.getCollectedBucket()) {
            goldEgg = new GoldEgg(this);
            goldEgg.setPosition(new Vec2(0, -107.5f));
        }


        //add in collision listener for chicken character
        ChickenCollisions chickenCollisions = new ChickenCollisions(getChicken(), game);
        getChicken().addCollisionListener(chickenCollisions);
    }

    /**
     * Checks if the level is complete by verifying if the gold egg was collected.
     *
     * @return true if the player collected the gold egg, otherwise false
     */
    public boolean isComplete(){
        if (goldEgg.isGoldEggCollected()){
            return true;
        } else {
            return false;
        }
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

    /**
     * Removes all grass platforms from the level (for the last celebration screen after the game is won).
     */
    public void clearGrass() {
        for (Grass grass : grassList) {
            grass.destroy();
        }
        //clear the list after destroying
        grassList.clear();
    }
}
