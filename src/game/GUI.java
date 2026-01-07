/**
 *  The GUI class manages the graphical user interface for the game.
 * <p>
 * It includes elements such as:
 * <ul>
 *     <li>a reset button to restart the game after a game-over.</li>
 *     <li>a mut/unmute button for toggling sound.</li>
 *     <li>introduction overlays shown at the beginning of each level.</li>
 * </ul>
 * This class also interacts with the game's frame and layers custom components onto the display.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JFrame frame;
    private JButton resetButton;
    private JButton muteButton;
    private GameView view;
    private Game game;
    private GameLevel level;

    /**
     * Constructs the GUI for the game.
     *
     * @param game the main Game object
     * @param view the game view where GUI elements will be layered on
     */
    public GUI(Game game, GameView view) {
        this.game = game;
        this.view = view;
        setupButtons();
    }

    /**
     * Initialises and sets up the buttons (reset and mute)
     */
    private void setupButtons() {
        frame = game.getFrame();

        //initialize the reset button (but hide it initially)
        resetButton = new JButton("Reset Game");
        resetButton.setFont(new Font("Monospaced", Font.BOLD, 18));
        resetButton.setPreferredSize(new Dimension(150, 40));
        resetButton.setVisible(false);
        resetButton.addActionListener(e -> game.resetGame());

        //add the reset button to the frame
        frame.add(resetButton, BorderLayout.SOUTH);

        //initialize the mute button
        muteButton = new JButton("🔊");
        muteButton.setFont(new Font("SansSerif", Font.BOLD, 35));
        muteButton.setFocusPainted(false);
        muteButton.setContentAreaFilled(false);
        muteButton.setBorderPainted(false);
        muteButton.setOpaque(false);
        muteButton.setToolTipText("Mute / Unmute");
        muteButton.setBounds(515, 10, 80, 80);  // x, y, width, height (top-right corner)

        muteButton.addActionListener(e -> game.toggleMute());

        //overlay mute button on view
        JLayeredPane layeredPane = frame.getLayeredPane();
        layeredPane.add(muteButton, JLayeredPane.POPUP_LAYER);
    }

    public JButton getMuteButton() {
        return muteButton;
    }

    /**
     * Displays the introduction at the beginning of a level.
     */
    public void addIntro() {
        level = game.getLevel();
        level.stop();
        Image background = new ImageIcon("data/background3.jpg").getImage();

        JPanel overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                //draw child components (like text and button)
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        overlayPanel.setOpaque(false);
        overlayPanel.setBackground(new Color(0, 0,0,0));
        overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));

        //container for instructions
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setOpaque(false);
        instructionsPanel.setLayout(new BorderLayout());

        //add introduction text
        JTextArea instructions;
        if (level instanceof Level1) {
            instructions = new JTextArea("\n\n\n\n\n\n\n\n\n  " +
                    "Welcome to Happy Chicken!\n\n  " +
                    "Collect the eggs and avoid the fires so that you\n  " +
                    "don't get cooked.\n  " +
                    "After reaching the ground, go into the nest to go\n  " +
                    "home and proceed to the next level!\n\n  " +
                    "Have Fun!"
            );
        } else if (level instanceof Level2) {
            instructions = new JTextArea("\n\n\n\n\n\n\n\n\n  " +
                    "Great job! You're home!\n\n  " +
                    "But oh no! The nest is burning down!\n  " +
                    "Collect all the eggs and avoid the fires so that\n  " +
                    "you don't get cooked.\n  " +
                    "Climb upwards to find the door to exit the nest\n  " +
                    "before the nest burns down!\n\n  " +
                    "Hurry!"
            );
        } else if (level instanceof Level3) {
            instructions = new JTextArea("\n\n\n\n\n\n\n\n\n  " +
                    "Phew! You escaped the nest in time!\n\n  " +
                    "Collect the eggs and avoid the fires so that you\n  " +
                    "don't get cooked.\n  " +
                    "Do beware of the moving fires!\n  " +
                    "After reaching the ground, collect the water bucket\n  " +
                    "and shoot water thrice to extinguish the last fire\n  " +
                    "in order to collect the golden egg and win the game!\n\n  " +
                    "Have fun!"
            );
        } else {
            instructions = new JTextArea("Welcome to Happy Chicken!");
        }
        instructions.setFont(new Font("Monospaced", Font.PLAIN, 18));
        instructions.setEditable(false);
        instructions.setOpaque(false);
        instructions.setForeground(Color.WHITE);
        instructionsPanel.add(instructions, BorderLayout.CENTER);
        overlayPanel.add(instructionsPanel);

        //add vertical spacing
        overlayPanel.add(Box.createRigidArea(new Dimension(0, 85)));

        //container for controls
        JPanel controlsPanel = new JPanel();
        controlsPanel.setOpaque(false);
        controlsPanel.setLayout(new BorderLayout());

        //add Controls text at the bottom
        JTextArea controlsText;
        if (level instanceof Level1) {
            controlsText = new JTextArea("\n\n   Controls:\n" +
                    "   ← Move left     → Move right\n");
        } else if (level instanceof Level2) {
            controlsText= new JTextArea("\n   Controls:\n" +
                    "   ← Move left     → Move right     [ Space ] - Jump\n");
        } else if (level instanceof Level3) {
            controlsText= new JTextArea("   Controls:\n" +
                    "   ← Move left     → Move right     [ Shift ] - Shoot water\n");
        } else {
            controlsText = null;
        }
        controlsText.setFont(new Font("Monospaced", Font.PLAIN, 15));
        controlsText.setEditable(false);
        controlsText.setOpaque(false);
        controlsText.setForeground(Color.BLACK);
        controlsText.setBackground(new Color(0, 0, 0, 0));
        //add some padding
        controlsText.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        controlsPanel.add(controlsText, BorderLayout.CENTER);
        overlayPanel.add(controlsPanel);

        //add in button
        JButton startButton;
        if (level instanceof Level1) {
            startButton = new JButton("Click me to start!");
        } else {
            startButton = new JButton("Click me to continue!");
        }
        startButton.setFont(new Font("Monospaced", Font.BOLD, 18));
        startButton.setForeground(new Color(147, 185, 255, 240));
        overlayPanel.add(startButton, BorderLayout.SOUTH);

        //position and size
        overlayPanel.setBounds(0, 0, 600, 600);
        overlayPanel.setVisible(true);

        //add overlay to the GameView's glass pane or layered pane
        JLayeredPane layeredPane = frame.getLayeredPane();
        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);

        //button action to remove overlay
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layeredPane.remove(overlayPanel);
                layeredPane.repaint();
                // regain focus for keyboard input
                view.requestFocusInWindow();

                if (level instanceof Level2) {
                    ((Level2) level).resumeTimer();
                }

                // resume the game
                level.start();
            }
        });
    }

    /**
     * Displays the reset button (after game is over).
     */
    public void showGameOverButton() {
        if (level instanceof Level2) {
            resetButton.setForeground(new Color(205, 101, 48, 219));
        } else {
            resetButton.setForeground(new Color(147, 185, 255, 240));
        }
        //show the reset button
        resetButton.setVisible(true);
    }

    /**
     * Hides the reset button.
     */
    public void hideResetButton() {
        if (resetButton != null) {
            resetButton.setVisible(false);
        }
    }

    /**
     * Updates the internal level reference when a new level is loaded.
     */
    public void updateLevelReference() {
        this.level = game.getLevel();
    }
}
