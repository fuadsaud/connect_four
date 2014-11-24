package im.fuad.rit.copads.p4;

import java.io.IOException;

import im.fuad.rit.copads.p4.server.C4ViewProxy;

/**
 * Defines the operations necessary for objects that want to listen to view events.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public interface C4ViewListener {
    /**
     * Informs the model that a marker should be added for the current player in the given column.
     *
     * @param column the column in which the marker is to be added.
     */
    public void addMarker(Integer playerNumber, Integer column) throws IOException;

    /**
     * Informs the model to clear tha game board.
     */
    public void clear() throws IOException;

    public void join(C4ModelListener listener, String playerName) throws IOException;
}
