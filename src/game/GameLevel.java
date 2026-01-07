/**
 * An abstract base class representing a game level.
 * <p>
 * This class extends World class and provides a common foundation for different levels in the game.
 * It includes the main Chicken character and a reference to the main Game instance.
 * <p>
 * Subclasses must implement logic for determining when a level is complete or when the game is over.
 * <p>
 * @author      Joyce Ow joyce.ow@city.ac.uk
 * @version     2.0
 * @since       2025
 */

package game;

import city.cs.engine.World;

public abstract class GameLevel extends World {
    /**
     * The main chicken character in the level.
     */
    private Chicken chicken;
    private Game game;

    /**
     * Constructs a new game level and initialises the chicken character.
     *
     * @param game the main game object managing levels and state
     */
    public GameLevel(Game game){
        this.game=game;

        chicken = new Chicken(this, game);
    }

    public Chicken getChicken(){
        return chicken;
    }

    /**
     *Determines whether the level is complete.
     *
     * @return true of the level objectives are fulfilled; false otherwise
     */
    public abstract boolean isComplete();

    /**
     * Determines whether the game should end due to gave over conditions in the level.
     *
     * @return true if the player has failed the level; false otherwise
     */
    public abstract boolean isGameOver();
}
