/**
 * Represents a collectible egg in the game.
 * <p>
 * Eggs are dynamic bodies that can be collected by the player to increase their egg count.
 * When collected, they are destroyed and a chirp sound is played.
 * <p>
 * The egg is represented by a circular shape and a custom image.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Egg extends DynamicBody {
    private static final Shape eggShape = new CircleShape(1.5f);

    private static final BodyImage image = new BodyImage("data/egg.png", 3f);

    /**
     * Constructs an new Egg in the specified world.
     *
     * @param world the game world where the egg will be placed
     */
    public Egg(World world) {
        super(world, eggShape);
        addImage(image);
    }

    private static SoundClip chirp;

    /**
     * Static block to preload sound clips for chirp sound.
     * This block is executed once when the class is first loaded.
     */
    static {
        try {
            chirp = new SoundClip("data/chirp.wav");
            System.out.println("Loading chirp sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    /**
     * Plays a chirp sound when the egg is collected if the game is not currently muted.
     *
     * @param game the main game object, used to check the mute state
     */
    public static void playChirp(Game game) {
        if (chirp != null && !game.isMuted()) {
            chirp.play();
        }
    }
}
