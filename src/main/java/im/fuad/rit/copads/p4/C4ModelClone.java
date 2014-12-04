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

    /**
     * Sets this client's player number. Actual implementation just forwards the event to the
     * registered model listener.
     *
     * @param playerNumber the player's number (1 or 2).
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void number(Integer playerNumber) throws IOException {
        this.modelListener.number(playerNumber);
    }

    /**
     * Registers a player in the session. Actual implementation just forwards the event to the
     * registered model listener.
     *
     * @param playerNumber the player's number (1 or 2).
     * @param playerName the player's name.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void name(Integer playerNumber, String playerName) throws IOException {
        this.modelListener.name(playerNumber, playerName);
    }

    /**
    /**
     * Hook to be called when a marker is added to the board. Actual implementation just forwards
     * the event to the registered model listener.
     *
     * @param playerNumber the number of the player who's making the move.
     * @param row the row in which the marker is being added.
     * @param col the column in which the marker is being added.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void markerAdded(Integer playerNumber, Integer row, Integer col) throws IOException {
        this.board.play(playerNumber, row, col);
        this.modelListener.markerAdded(playerNumber, row, col);
    }

    /**
     * Hook to be called when the board is cleared. Actual implementation just forwards
     * the event to the registered model listener.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void cleared() throws IOException {
        this.board.clear();

        this.modelListener.cleared();
    }

    /**
     * Notifies that the game session was terminated because a player left it. Actual
     * implementation just forwards the event to the registered model listener.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void left() throws IOException {
        this.modelListener.left();
    }

    /**
     * Initiates a new turn in the game for the given player. Actual implementation just forwards
     * the event to the registered model listener.
     *
     * @param playerNumber the player's number (1 or 2).
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void turn(Integer playerNumber) throws IOException {
        this.modelListener.turn(playerNumber);
    }

    /**
     * Sets this object's model listener.
     *
     * @param listener the model listener to be registered.
     */
    public void setModelListener(C4ModelListener listener) {
        this.modelListener = listener;
    }
}
