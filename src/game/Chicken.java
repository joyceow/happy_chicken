/**
 * The main character of the game - a chicken controlled by the player.
 * <p>
 * This class extends the Walker class and defines the chicken's physical shape, image states (default and roasted),
 * sound effects, and gameplay-related logic such as credits, checking if the chicken is "roasted", and interacting with the Game and GUI classes.
 *
 * The chicken class also handles audio feedback when roasted, and can switch visuals accordingly. It interacts closely with the game's state
 * controller to trigger resets or transits.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

//the Walker class extends from a DynamicBody class
public class Chicken extends Walker {
    private static final Shape chickenShape = new PolygonShape(-0.12f,2.6f, -2.77f,-0.01f, -2.17f,-1.51f, -1.54f,-2.42f, 1.56f,-2.46f, 2.24f,-1.44f, 2.8f,-0.17f);

    private int credits;

    private static final BodyImage image = new BodyImage("data/chicken.GIF", 6f);

    //roast chicken image
    private static final BodyImage roastedImage = new BodyImage("data/roastChicken.gif", 6f);

    private boolean roasted;
    private Game game;
    private static SoundClip roastedSound;
    private static SoundClip awhSound;

    /**
     * Creates a new chicken character within the specified game world.
     *
     * @param world the physics world in which the chicken exists
     * @param game reference to the main Game controller for state updates
     */
    public Chicken(World world, Game game) {
        super(world, chickenShape);
        this.game = game;
        addImage(image);
        credits = 0;
        roasted = false;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public boolean roasted() {
        return roasted;
    }

    /**
     * Static block to preload sound clips for roasted and "awh" effects.
     * This block is executed once when the class is first loaded.
     */
    static {
        try {
            roastedSound = new SoundClip("data/roasted.wav");
            System.out.println("Loading roasted sound");
            awhSound = new SoundClip("data/awh.wav");
            System.out.println("Loading awh sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    /**
     * Plays the sound effects for when the chicken gets roasted.
     * Plays a one-time "awh" sound followed by a looping roasted sound.
     */
    public void playRoastedSound() {
        awhSound.play();
        roastedSound.loop();
        game.setRoastedSoundPlaying(true);
    }

    public static SoundClip getRoastedSound() {
        return roastedSound;
    }

    /**
     * Marks the chicken as roasted, swaps its image, plays the corresponding sound, and stops the game level.
     * The GUI reset button is also triggered to appear on the screen.
     */
    public void setRoasted() {
        this.roasted = true;

        //remove the current chicken image
        this.removeAllImages();
        //add roasted chicken image
        this.addImage(roastedImage);

        //play roasted sound if game is not muted
        if (!game.isMuted()) {
            game.getGameMusic().stop();
            playRoastedSound();
        }
        game.setRoastedSoundPlaying(true);

        //stop the current level and show reset button
        if (game != null) {
            game.getLevel().stop();
            game.getGUI().showGameOverButton();
        }
    }

    /**
     * Resets the chicken's roasted state back to normal, replacing the roasted image with the default walking image.
     */
    public void resetRoasted() {
        //reset roasted boolean
        this.roasted = false;
        //remove roasted image
        this.removeAllImages();
        // reset to default chicken image
        this.addImage(image);
    }

    /**
     * Gets the direction the chicken is currently moving in.
     *
     * @return a unit Vec2 vector repressenting direction:
     *         (1, 0) if moving right,
     *         (-1, 0) if moving left,
     *         (0, 0) if standing still
     */
    public Vec2 getDirection() {
        //get the velocity of the chicken
        Vec2 velocity = getLinearVelocity();

        if (velocity.x > 0) {
            //moving right
            return new Vec2(1, 0);
        } else if (velocity.x < 0) {
            //moving left
            return new Vec2(-1, 0);
        } else {
            //not moving
            return new Vec2(0, 0);
        }
    }
}

