package im.fuad.rit.copads.p4;

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
     * @see C4ModelListener.markerAdded()
     */
    public void markerAdded(Player player, Integer row, Integer col) {
        this.board.play(player.getNumber(), row, col); this.modelListener.markerAdded(player, row,
                col); }

    /**
     * @see C4ModelListener.cleared()
     */
    public void cleared() {
        this.board.clear();

        this.modelListener.cleared();
    }

    /**
     * @see C4ModelListener.gameStarted()
     */
    public void gameStarted() { this.modelListener.gameStarted(); }

    /**
     * @see C4ModelListener.gameOver()
     */
    public void gameOver() { this.modelListener.gameOver(); }

    /**
     * @see C4ModelListener.turn()
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
