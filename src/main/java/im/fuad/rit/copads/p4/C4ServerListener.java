package im.fuad.rit.copads.p4;

import java.io.IOException;

/**
 * Defines the operations necessary for objects that want to listen to server events based on the
 * application communication protocol.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public interface C4ServerListener {
    /**
     * Sets this client's player number.
     *
     * @param playerNumber the player's number (1 or 2).
     */
    public void number(Integer playerNumber) throws IOException;

    /**
     * Registers a player in the session. If it is the opponent that's being registered it also
     * fires the start event in the model listener.
     *
     * @param playerNumber the player's number (1 or 2).
     * @param playerName the player's name.
     */
    public void name(Integer playerNumber, String playerName) throws IOException;

    /**
     * Initiates a new turn in the game for the given player.
     *
     * @param playerNumber the player's number (1 or 2).
     */
    public void turn(Integer playerNumber) throws IOException;

    /**
     * Add a marker for the given player in the given position.
     *
     * @param playerNumber the player's number (1 or 2).
     * @param row the row in which to add the marker.
     * @param column the column in which to add the marker.
     */
    public void add(Integer playerNumber, Integer row, Integer col) throws IOException;

    /**
     * Clear the game board.`
     */
    public void clear() throws IOException;
}
