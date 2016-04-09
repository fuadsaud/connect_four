package im.fuad.rit.copads.p4;

import java.io.IOException;

/**
 * Defines the operations necessary for objects that want to listen to model events.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public interface C4ModelListener {
    /**
     * Hook to be called when a marker is added to the board.
     *
     * @param playerNumber the number of the player who's making the move.
     * @param row the row in which the marker is being added.
     * @param col the column in which the marker is being added.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void markerAdded(Integer playerNumber, Integer row, Integer col) throws IOException;

    /**
     * Hook to be called when the board is cleared.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void cleared() throws IOException;

    /**
     * Sets this client's player number.
     *
     * @param playerNumber the player's number (1 or 2).
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void number(Integer playerNumber) throws IOException;

    /**
     * Registers a player in the session.
     *
     * @param playerNumber the player's number (1 or 2).
     * @param playerName the player's name.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void name(Integer playerNumber, String playerName) throws IOException;

    /**
     * Initiates a new turn in the game for the given player.
     *
     * @param playerNumber the player's number (1 or 2).
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void turn(Integer playerNumber) throws IOException;

    /**
     * Notifies that the game session was terminated because a player left it.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void left() throws IOException;
}
