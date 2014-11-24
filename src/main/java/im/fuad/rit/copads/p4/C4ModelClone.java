package im.fuad.rit.copads.p4;

import java.io.IOException;

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

    public void number(Integer playerNumber) { }
    public void name(Integer playerNumber, String playerName) { }
    public void add(Integer player, Integer row, Integer col) { }

    /**
     * @see C4ModelListener.markerAdded()
     */
    public void markerAdded(Integer playerNumber, Integer row, Integer col) throws IOException {
        this.board.play(playerNumber, row, col);
        this.modelListener.markerAdded(playerNumber, row, col);
    }

    /**
     * @see C4ModelListener.cleared()
     */
    public void cleared() throws IOException {
        this.board.clear();

        this.modelListener.cleared();
    }

    /**
     * @see C4ModelListener.gameStarted()
     */
    public void gameStarted() throws IOException { this.modelListener.gameStarted(); }

    /**
     * @see C4ModelListener.gameOver()
     */
    public void gameOver() throws IOException { this.modelListener.gameOver(); }

    /**
     * @see C4ModelListener.turn()
     */
    public void turn(Integer playerNumber) throws IOException {
        this.modelListener.turn(playerNumber);
    }

    public void clear() {}

    /**
     * Sets this object's model listener.
     *
     * @param listener the model listener to be registered.
     */
    public void setModelListener(C4ModelListener listener) {
        this.modelListener = listener;
    }
}
