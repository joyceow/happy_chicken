/**
 * The Chicken Controller class handles keyboard input to control the Chicken character's movement and actions in the game.
 * <p>
 * It listens for key events to make the chicken walk, jump (in Level2), or shoot water projectiles (after water bucket is collected in Level3).
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */
package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import city.cs.engine.*;

public class ChickenController implements KeyListener {

    private Chicken chicken;
    private GameLevel level;
    private int count = 0;

    /**
     * Constructs a new controller to handle keyboard input for a chicken in a specified level.
     *
     * @param chicken the chicken character to control
     * @param level the current game level
     */
    public ChickenController(Chicken chicken, GameLevel level) {
        this.chicken = chicken;
        this.level = level;
    }

    /**
     * Updates the reference to the chicken and level, typically after a level reset or transition.
     *
     * @param chicken the new chicken character
     * @param level the new game level
     */
    public void updateChicken(Chicken chicken, GameLevel level) {
        this.chicken = chicken;
        this.level = level;
    }

    /**
     * Not used but required by KeyListener class interface.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Handles key press events to control chicken actions.
     * <ul>
     *     <li>Left Arrow: Walk left</li>
     *     <li>Right Arrow: Walk right</li>
     *     <li>Spacebar: Jump (Level2 only)</li>
     *     <li>Shift: Shoot water (after water bucket is collected in Level3, maximum 3 times)</li>
     * </ul>
     *
     * @param e the keyboard event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //control chicken to make chicken move sideways
        if (code == KeyEvent.VK_LEFT) {
            chicken.startWalking(-8);
        } else if (code == KeyEvent.VK_RIGHT) {
            chicken.startWalking(8);

        //control chicken to make chicken jump
        } else if ((code == KeyEvent.VK_SPACE) && (level instanceof Level2)) {
            chicken.jump(12);

        //control chicken to make chicken shoot water projectiles
        } else if ((code == KeyEvent.VK_SHIFT) && WaterBucket.getCollectedBucket() && count < 3) {
            WaterProjectile waterProjectile = new WaterProjectile(level, chicken.getPosition(), chicken.getDirection());
            waterProjectile.launch();
            count++;
        }
    }

    /**
     * Handles key release events to stop the chicken's walking movement.
     *
     * @param e the keyboard event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        //stop chicken from moving when key is released
        if (code == KeyEvent.VK_LEFT) {
            chicken.stopWalking();
        } else if (code == KeyEvent.VK_RIGHT) {
            chicken.stopWalking();
        }
    }
}
