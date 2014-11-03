package im.fuad.rit.copads.p3;

/**
 * This class provides the client with a clone of the model present on the game server.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class C4ModelClone implements C4ModelListener {
    private C4Board board;
    private C4ModelListener modelListener;

    /**
     * Initializes a model clone.
     *
     * @param board the game board to be used.
     */
    public C4ModelClone(C4Board board) {
        this.board = board;
    }

    /**
     * Hook to be called when a marker is added to the board. It adds the marker to the game
     * board and fires the same event on the registered listener.
     *
     * @param player the player who's making the move.
     * @param row the row in which the marker is being added.
     * @param col the column in which the marker is being added.
     */
    public void markerAdded(Player player, Integer row, Integer col) {
        this.board.play(player.getNumber(), row, col); this.modelListener.markerAdded(player, row,
                col); }

    /**
     * Hook to be called when the board is cleared. It clears the board and fires the same event
     * on the registered listener.
     */
    public void cleared() {
        this.board.clear();

        this.modelListener.cleared();
    }

    /**
     * Hook to be called when the game starts. It fires the same event on the registered listener.
     */
    public void gameStarted() { this.modelListener.gameStarted(); }

    /**
     * Hook to be called when the game ends. It fires the same event on the registered listener.
     */
    public void gameOver() { this.modelListener.gameOver(); }

    /**
     * Hook to be called when the a new turn starts. It fires the same event on the registered
     * listener.
     *
     * @param player the player to which this new turn belongs.
     */
    public void turn(Player player) { this.modelListener.turn(player); }

    /**
     * Sets this object's model listener.
     *
     * @param listener the model listener to be registered.
     */
    public void setModelListener(C4ModelListener listener) {
        this.modelListener = listener;
    }
}
