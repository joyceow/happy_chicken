/**
 * Represents a collectible queen egg in the game.
 * <p>
 * It is a dynamic body and has a boc shape and custom image.
 * When collected, it triggers a "yay" sound effect and contributes doubly to the player's egg count.
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

public class QueenEgg extends DynamicBody {
    private static final Shape queenShape = new PolygonShape(-0.99f,-0.09f, -1.01f,-1.89f, 0.93f,-1.93f, 1.01f,-0.27f, 0.28f,1.9f, -0.28f,1.93f);

    private static final BodyImage image = new BodyImage("data/queenEgg.png", 4f);

    public QueenEgg(World world) {
        super(world, queenShape);
        addImage(image);
    }

    private static SoundClip yay;

    /**
     * Static block to preload sound clip for "yay" sound effect.
     * This block is executed once when the class is first loaded.
     */
    static {
        try {
            yay = new SoundClip("data/yay.wav");
            System.out.println("Loading yay sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    /**
     * Plays the "yay" sound when the queen egg is collected, if sounds are not muted.
     *
     * @param game the current game instance, used to check if the game is currently muted
     */
    public static void playYay(Game game) {
        if (yay != null && !game.isMuted()) {
            yay.play();
        }
    }
}
