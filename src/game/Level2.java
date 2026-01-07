/**
 * The Level2 class represents the second level of Happy Chicken.
 * <p>
 * In this level, the player must escape a burning nest by collecting all the eggs within 2 minutes.
 * This level includes multiple platforms, eggs, grass tiles, and a door to proceed to the next level.
 * If the timer runs out before the player escapes, the nest "burns down" and the chicken gets roasted.
 * <p>
 * This class also manages an internal countdown timer and contains logic to visually place elements like Egg, Fire, and Grass.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import javax.swing.*;
import static java.lang.Math.round;

public class Level2 extends GameLevel {
    private Egg egg;
    private Fire fire;
    private Grass grass;
    private Door door;

    private Timer timer;
    private int timeLeft;

    /**
     * Constructs the second level of the game, initialising platforms, fire hazards, collectible eggs, and a timer that counts down fom 2 minutes.
     *
     * @param game the main game controller
     */
    public Level2(Game game) {
        super(game);

        //setup timer of 2 minutes
        timeLeft = 120;
        setupTimer();

        //make a ground platform
        Shape shape = new BoxShape(50, 10f);
        StaticBody ground = new StaticBody(this, shape);
        ground.setPosition(new Vec2(0f, -125f));
        ground.addImage(new BodyImage("data/soil.jpg", 20));

        //make side borders for the game
        Shape borderShape = new BoxShape(4f, 50f);

        StaticBody border1= new StaticBody(this, borderShape);
        border1.setPosition(new Vec2(48, -70));
        border1.addImage(new BodyImage("data/border2.jpg", 100));

        StaticBody border2= new StaticBody(this, borderShape);
        border2.setPosition(new Vec2(-52, -70));
        border2.addImage(new BodyImage("data/border2.jpg", 100));

        //add in a chicken character
        getChicken().setPosition(new Vec2(-34, -105.5f));

        //looping of grass and eggs and fire
        for (int i=0; i<3; i++) {
            grassAndEgg(2-(12*i), -113);
        }

        for (int i=0; i<3; i++) {
            grassAndEgg(20+(10*i), -110+(5*i));
        }

        for (int i=0; i<2; i++) {
            grassAndEgg(28-(10*i), -95+(5*i));
        }

        for (int i=0; i<4; i++) {
            grassAndEgg(6-(12*i), -90);
        }

        grassAndEgg(-42, -85);

        for (int i=0; i<2; i++) {
            fire = new Fire(this);
            fire.setPosition(new Vec2(-(24*i), -92));
        }

        for (int i=0; i<3; i++) {
            grassAndEgg(-30+(10*i),-78+(5*i));
        }

        for (int i=0; i<4; i++) {
            grassAndEgg((12*i), -63);
        }

        fire = new Fire(this);
        fire.setPosition(new Vec2(-14, -71));

        for (int i=0; i<2; i++) {
            fire = new Fire(this);
            fire.setPosition(new Vec2(5.5f+(24*i), -64));
        }


        //add in door to proceed to the next level
        door = new Door(this);
        door.setPosition(new Vec2(42, -60));


        //add in collision listener for chicken character
        ChickenCollisions chickenCollisions = new ChickenCollisions(getChicken(), game);
        getChicken().addCollisionListener(chickenCollisions);
    }

    /**
     * Helper method to place a Grass tile and an Egg above it.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void grassAndEgg(int x, int y){
        grass = new Grass(this);
        grass.setPosition(new Vec2(x, y));

        egg = new Egg(this);
        egg.setPosition(new Vec2(x, y+4));
        egg.setGravityScale(0);
    }

    /**
     * Determines if the level is complete.
     *
     * @return true if the chicken has collected all 20 eggs, false otherwise
     */
    public boolean isComplete(){
        if (getChicken().getCredits() == 20) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Starts the countdown timer (2 minutes)
     * Triggers game over logic if the time runs out.
     */
    public void setupTimer() {
        //create a timer that ticks every second
        timer = new Timer(1000, e -> updateTimer());
        timer.start();
    }

    /**
     * Updates the timer countdown every second.
     * Ends the level if time reaches zero.
     */
    public void updateTimer() {
        //update the timer countdown
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            //handle end of level when timer reaches 0
            timer.stop();
            timeUp();
        }
    }

    /**
     * Logic that triggers when timer runs out.
     * Roasts the chicken and stops the game.
     */
    public void timeUp() {
        System.out.println("Oh no! The nest burnt down!");
        getChicken().setRoasted();
        //play roasted sound
        try {
            SoundClip roastedSound = new SoundClip("data/roasted.wav");
            roastedSound.loop();
        } catch (Exception e) {
            System.out.println("Error playing roasted sound: " + e);
        }
        getChicken().getWorld().stop();
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public int getMinsLeft() {
        return round(timeLeft/60);
    }

    public int getSecsLefts() {
        return timeLeft%60;
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
     * Pauses the level timer.
     */
    public void pauseTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    /**
     * Resumes the level timer.
     */
    public void resumeTimer() {
        if (timer != null) {
            timer.start();
        }
    }
}
