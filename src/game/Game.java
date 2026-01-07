/**
 * The main class that manages the entire game, including level transitions, GUI management, music playback, and user input.
 * <p>
 * This class is the entry point to Happy Chicken and is responsible for initialising and updating the game state based on the player progress.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;

import javax.swing.*;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * Your main game entry point
 */
public class Game {
    private GameLevel level;
    private GameView view;
    private ChickenController chickenController;
    private JFrame frame;
    private SoundClip gameMusic;
    private boolean isMuted = false;
    private boolean isRoastedSoundPlaying = false;
    private GUI gui;

    /** Initialise a new Game. */
    public Game() {

        //initialise level to Level1
        level = new Level3(this);
        chickenController = new ChickenController(level.getChicken(), level);

        //make a view to look into the game world
        view = new GameView(level, 600, 600);
        view.addKeyListener(chickenController);
        view.addMouseListener(new GiveFocus());

        //optional: draw a 1-metre grid over the view
        // view.setGridResolution(1);

        //create a Java window (frame) and add the game view to it
        frame = new JFrame("Happy Chicken");
        frame.add(view);

        //enable the frame to quit the application when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        //don't let the frame be resized
        frame.setResizable(false);
        //size the frame to fit the world view
        frame.pack();
        //finally, make the frame visible
        frame.setVisible(true);

        //optional: uncomment this to make a debugging view
        //JFrame debugView = new DebugViewer(level, 600, 600);

        gui = new GUI(this, view);
        gui.addIntro();
        updateMusicForLevel();
    }

    public JFrame getFrame() {
        return frame;
    }

    /**
     * Updates the background music based on the current game level.
     * <p>
     * Stops any currently playing music, then attempts to load and play a new music track based on the current game level.
     * Music is only played if it is successfully loaded and the game is not muted.
     * <p>
     * Audio-related exceptions are also handled (eg. unsupported file formats, I/O errors, or unavailable audio lines).
     */
    private void updateMusicForLevel() {
        //stop old music
        if (gameMusic != null) {
            gameMusic.stop();
        }

        /**
         * Static block to preload sound clips for gameMusic.
         * This block is executed once when the class is first loaded.
         */
        try {
            if (level instanceof Level1) {
                gameMusic = new SoundClip("data/level1.wav");
            } else if (level instanceof Level2) {
                gameMusic = new SoundClip("data/level2.wav");
            } else if (level instanceof Level3) {
                gameMusic = new SoundClip("data/level3.wav");
            }

            //play music only if not muted
            if (gameMusic != null && !isMuted) {
                gameMusic.loop();
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error loading music: " + e);
        }
    }

    public SoundClip getGameMusic() {
        return gameMusic;
    }

    /**
     * Toggles the game's mute state between being muted and unmuted.
     * <p>
     * When unmuting:
     * <ul>
     *     <li>if the chicken is cooked and the roasted sound should be playing, it resumes the roasted sound.</li>
     *     <li>otherwise, the background music is unmuted if available.</li>
     *     <li>the GUI mute button is also updated to show that the sound is now on.</li>
     * </ul>
     * When muting:
     * <ul>
     *     <li>the background music and chicken roasted sound are stopped.</li>
     *     <li>the GUI mute button text is updated to show that the sound is now off.</li>
     * </ul>
     * Ensures that the game window regains focus after toggling, so keyboard input continues to work properly.
     */
    public void toggleMute() {
        if (isMuted) {
            //unmute game
            if (isRoastedSoundPlaying) {
                level.getChicken().playRoastedSound();
            } else if (gameMusic != null) {
                gameMusic.loop();
            }
            gui.getMuteButton().setText("🔊");
            isMuted = false;
        } else {
            //mute game
            if (gameMusic != null) {
                gameMusic.stop();
            }
            Chicken.getRoastedSound().stop();
            gui.getMuteButton().setText("🔇");
            isMuted = true;
        }

        //ensure keyboard input works again
        view.requestFocusInWindow();
    }

    /**
     * Returns whether the game is currently muted.
     * @return true if the game is muted; false otherwise.
     */
    public boolean isMuted() {
        return isMuted;
    }

    public void setRoastedSoundPlaying(boolean playing) {
        isRoastedSoundPlaying = playing;
    }

    /**
     * Advances the game to the next level based on the current level instance.
     * <p>
     * This method performs the following actions for each level transition:
     * <ul>
     *     <li>the current level is stopped.</li>
     *     <li>reinitialise the level to the next level.</li>
     *     <li>the game view is updated to reflect the new view and background.</li>
     *     <li>the chicken reference is updated in the controller to the new level's chicken.</li>
     *     <li>a step listener is added to the new level to allow the view to follow the chicken.</li>
     *     <li>the music is updated to match the new level.</li>
     *     <li>an introduction screen is added for the new level via GUI</li>
     * </ul>
     */
    public void goToNextLevel() {
        if (level instanceof Level1) {
            //stop the current level
            level.stop();
            //switch to Level2
            level = new Level2(this);
            //update the view and background with the new level
            view.setWorld(level);
            view.setBackgroundImage(level);
            //update chicken reference
            chickenController.updateChicken(level.getChicken(), level);
            //add step listener to the view to ensure view follows chicken as it walks
            level.addStepListener(view);
            //JFrame debugView = new DebugViewer(level, 600, 600);
            //pause the timer
            ((Level2) level).pauseTimer();
            //update music based on the current level
            updateMusicForLevel();
            //add in introduction screen
            gui.addIntro();


        }else if (level instanceof Level2){
            //stop the current level
            level.stop();
            //switch to Level3
            level = new Level3(this);
            //update the view and background with the new level
            view.setWorld(level);
            view.setBackgroundImage(level);
            //update chicken reference
            chickenController.updateChicken(level.getChicken(), level);
            //add step listener to the view to ensure view follows chicken as it walks
            level.addStepListener(view);
            //JFrame debugView = new DebugViewer(level, 600, 600);
            //update music based on the current level
            updateMusicForLevel();
            //add in introduction screen
            gui.addIntro();

        } else if (level instanceof Level3){
            //end the game
            System.out.println("Well done! Game completed.");
        }
    }

    public GameLevel getLevel() {
        return level;
    }

    /**
     * Resets the game to its initial state by stopping the current level, reinitialising to Level1,
     * resetting game elements like sound and GUI components, and preparing the game to start again.
     * <p>
     * This method performs the following steps:
     * <ul>
     *     <li>the reset button is hidden and roasted sound effects are stopped.</li>
     *     <li>the current background music and current game level are stopped.</li>
     *     <li>if the current level is Level2, the internal timer is stopped.</li>
     *     <li>a new instance of Level1 is created, and the view and background image are updated.</li>
     *     <li>the chicken's state is reset and the chicken's controller is updated.</li>
     *     <li>necessary listeners are added and focus for user input is requested.</li>
     *     <li>the GUI reference is updated to the new level and the music is managed based on the mute status.</li>
     *     <li>the newly created level and started.</li>
     * </ul>
     * This method is typically triggered when the player chooses to restart or reset the game after a game over or completion.
     */
    public void resetGame() {
        //hide the reset button again after clicking
        gui.hideResetButton();

        //stop roasted sound
        Chicken.getRoastedSound().stop();
        setRoastedSoundPlaying(false);

        //stop the current music before resetting the game
        if (gameMusic != null) {
            gameMusic.stop();
        }

        //reset the game by reinitializing to the first level
        level.stop();

        //stop the old timer
        if (level instanceof Level2) {
            ((Level2) level).pauseTimer();
        }

        level = new Level1(this);
        //update the view and background with the new level
        view.setWorld(level);
        view.setBackgroundImage(level);

        //update the chicken controller
        chickenController.updateChicken(level.getChicken(), level);
        level.getChicken().resetRoasted();
        //add step listener to view
        level.addStepListener(view);

        //make sure keyboard input works again
        view.requestFocusInWindow();

        //update GUI regarding the new level
        gui.updateLevelReference();

        //update music based on the current level
        updateMusicForLevel();
        //only start the music if game is not muted
        if (isMuted) {
            gameMusic.stop();
        }

        //start the level
        level.start();
    }

    public GUI getGUI() {
        return gui;
    }

    /** Run the game. */
    public static void main(String[] args) {

        new Game();
    }
}
