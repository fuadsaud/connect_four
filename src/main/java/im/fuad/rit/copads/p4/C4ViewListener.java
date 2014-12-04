package im.fuad.rit.copads.p4;

import java.io.IOException;

import im.fuad.rit.copads.p4.C4ViewProxy;

/**
 * Defines the operations necessary for objects that want to listen to view events.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public interface C4ViewListener {
    /**
     * Informs the model that a marker should be added for the current player in the given column.
     *
     * @param playerNumber the player number for which the marker is to be added.;
     * @param column the column in which the marker is to be added.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void addMarker(Integer playerNumber, Integer column) throws IOException;

    /**
     * Informs the model to clear the game board.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void clear() throws IOException;

    /**
     * Joins this game model.
     *
     * @param listener the object to listen for model events for this new player.
     * @param playerName this new player's name.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void join(C4ModelListener listener, String playerName) throws IOException;

    /**
     * Leave the game model.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void leave() throws IOException;
}
