/**
 * A mouse adapter that requests focus for a component when the mouse enters it.
 * <p>
 * This is useful in game windows to ensure that the component (the panel) receives keyboard input after the player hovers over it.
 * <p>
 * By requesting focus on mouse enter, players can immediately interact with the game using the keyboard without needing to manually click on the window.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GiveFocus extends MouseAdapter {

    /**
     * Requests focus for the component when the mouse enters it.
     *
     * @param e the mouse event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().requestFocus();
    }

}
