package im.fuad.rit.copads.p4;

/**
 * Defines the operations necessary for objects that want to listen to model events.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public interface C4ModelListener {
    /**
     * Hook to be called when a marker is added to the board.
     *
     * @param player the player who's making the move.
     * @param row the row in which the marker is being added.
     * @param col the column in which the marker is being added.
     */
    public void markerAdded(Player player, Integer row, Integer col);

    /**
     * Hook to be called when the board is cleared.
     */
    public void cleared();

    /**
     * Hook to be called when the game starts.
     */
    public void gameStarted();

    /**
     * Hook to be called when the game ends.
     */
    public void gameOver();

    /**
     * Hook to be called when the a new turn starts.
     *
     * @param player the player to which this new turn belongs.
     */
    public void turn(Player player);
}
