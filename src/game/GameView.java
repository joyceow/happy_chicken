/**
 * A customised view for displaying the game world and GUI elements.
 * <p>
 * This class extends UserView Class and implements StepListener to follow the chicken's movement and render various UI elements
 * such as egg count, game over messages, level-specified backgrounds, and timers.
 * <p>
 * The camera smoothly follows the chicken character as it moves through the level, and level-specific logic is used to adjust background images and messages.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.UserView;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;

public class GameView extends UserView implements StepListener{
    private Image background;

    /**
     * The camera's target position for smooth transitions.
     */
    private Vec2 targetPosition;

    private GameLevel level;

    /**
     * Constructs the game view and initialises background and listeners.
     *
     * @param w the current game level
     * @param width the width of the view
     * @param height the height of the view
     */
    public GameView(GameLevel w, int width, int height) {

        super(w, width, height);
        level = w;
        setBackgroundImage(level);
        w.addStepListener(this);

        //initialise the camera's target position
        targetPosition = level.getChicken().getPosition();
    }

    /**
     * Sets the background image depending on the current level.
     *
     * @param level the current game level
     */
    public void setBackgroundImage(GameLevel level) {
        if (level instanceof Level1) {
            background = new ImageIcon("data/background1.jpg").getImage();
        } else if (level instanceof Level2) {
            background = new ImageIcon("data/background2.jpg").getImage();
        } else if (level instanceof Level3) {
            background = new ImageIcon("data/background3.jpg").getImage();
        }
    }

    /**
     * Paints the background image for the level.
     *
     * @param g the graphics context to draw on
     */
    @Override
    protected void paintBackground(Graphics2D g) {
    g.drawImage(background, 0, 0, this);
    }

    /**
     * Updates the view to follow the chicken smoothly as the level steps.
     *
     * @param stepEvent the step event from the world
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        //make the view follow the chicken as it falls downwards and moves
        Vec2 currentPosition = level.getChicken().getPosition();
        float deltaX = (targetPosition.x - currentPosition.x) * (0.2f);
        float deltaY = (targetPosition.y - currentPosition.y) * (0.001f);

        float cameraY = currentPosition.y + deltaY;

        //if level is Level3, view does not fall too low below the ground
        if (level instanceof Level3) {
            float groundLevel = -104f;

            if (cameraY < groundLevel) {
                cameraY = groundLevel;
            }
        }

        setCentre(new Vec2(currentPosition.x + deltaX, cameraY));
    }

    /**
     * Updates the level reference when the world changes.
     *
     * @param w the new world
     */
    @Override
    public void setWorld(World w) {
        super.setWorld(w);
        this.level = (GameLevel)w;
    }

    /**
     * Not used but required by StepListener.
     */
    @Override
    public void postStep(StepEvent stepEvent) {

    }

    /**
     * Font used for status messages like egg count and game messages.
     */
    public static final Font STATUS_FONT = new Font("Monospaced", Font.PLAIN, 20);

    /**
     * Paints UI elements on top of the game, such as egg count, timer, and game over messages.
     *
     * @param g the graphics context to draw on
     */
    @Override
    protected void paintForeground(Graphics2D g) {
        //print number of eggs collected by chicken in the screen
        int credits = level.getChicken().getCredits();

        if (!(level instanceof Level3 && level.isComplete())) {

            if (level instanceof Level1 || level instanceof Level3) {
                g.setColor(Color.BLUE);
            } else if (level instanceof Level2) {
                g.setColor(Color.BLACK);
            }
            g.setFont(STATUS_FONT);
            g.drawString("Eggs collected: " + credits, 50, 50);
        }

        //print game over message
        if ((level instanceof Level1 || level instanceof Level3) && level.isGameOver()) {
            g.setColor(Color.RED);
            g.setFont(STATUS_FONT);
            //calculate center position of the screen
            String gameOverText = "Oh no! You're cooked! Game Over!";
            FontMetrics metrics = g.getFontMetrics(STATUS_FONT);
            int textWidth = metrics.stringWidth(gameOverText);
            int screenCenterX = getWidth() / 2;

            //position the text in the center
            int x = screenCenterX - (textWidth / 2);

            g.drawString(gameOverText, x, 80);
        } else if (level instanceof Level2 && level.isGameOver() && ((Level2) level).getTimeLeft() != 0) {
            g.setColor(Color.RED);
            g.setFont(STATUS_FONT);
            //calculate center position of the screen
            String gameOverText = "Oh no! You're cooked! Game Over!";
            FontMetrics metrics = g.getFontMetrics(STATUS_FONT);
            int textWidth = metrics.stringWidth(gameOverText);
            int screenCenterX = getWidth() / 2;

            //position the text in the center
            int x = screenCenterX - (textWidth / 2);

            g.drawString(gameOverText, x, 80);
        } else if (level instanceof Level3 && level.isComplete()) {
            g.setColor(new Color(61, 22, 255, 229));
            g.setFont(new Font("Monospaced", Font.PLAIN, 25));
            //calculate center position of the screen
            String gameOverText = "Golden egg collected! You won!";
            FontMetrics metrics = g.getFontMetrics(new Font("Monospaced", Font.PLAIN, 25));
            int textWidth = metrics.stringWidth(gameOverText);
            int screenCenterX = getWidth() / 2;

            //position the text in the center
            int x = screenCenterX - (textWidth / 2);

            g.drawString(gameOverText, x, 95);
        }

        //for level2
        if (level instanceof Level2) {
            Level2 level2 = (Level2)level;
            int timeLeft = level2.getTimeLeft();
            if (timeLeft < 10) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            g.setFont(STATUS_FONT);
            if (timeLeft == 0) {

                //calculate center position of the screen
                String gameOverText = "Time's up! The nest burnt down! Game Over!";
                FontMetrics metrics = g.getFontMetrics(STATUS_FONT);
                int textWidth = metrics.stringWidth(gameOverText);
                int screenCenterX = getWidth() / 2;

                //position the text in the center
                int x = screenCenterX - (textWidth / 2);

                g.drawString(gameOverText, x, 80);
            } else if (timeLeft > 0 && !(level.isGameOver())) {
                //calculate center position of the screen
                String time = "00:00";
                FontMetrics metrics = g.getFontMetrics(STATUS_FONT);
                int textWidth = metrics.stringWidth(time);
                int screenCenterX = getWidth() / 2;

                //position the text in the center
                int x = screenCenterX - (textWidth / 2);

                g.drawString(level2.getMinsLeft() + ":" + level2.getSecsLefts(), x, 80);
            }
        }
    }
}
