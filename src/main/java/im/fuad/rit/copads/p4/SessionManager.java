package im.fuad.rit.copads.p4;

import java.io.IOException;

import java.util.HashMap;

import im.fuad.rit.copads.p4.C4ViewListener;
import im.fuad.rit.copads.p4.C4ModelListener;
import im.fuad.rit.copads.p4.C4Board;

/**
 * This class maitains and associates model objects (sessions) with game clients.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class SessionManager implements C4ViewListener {
    private C4Model sessionWaitingForPlayer;
    private HashMap<C4ModelListener, C4Model> sessions;

    /**
     * Initializes a session manager.
     */
    public SessionManager() {
        this.sessions = new HashMap<C4ModelListener, C4Model>();
    }

    /**
     * Adds the view proxy to an existing open session or creates a new session for this proxy.
     *
     * @param proxy the view proxy joining a session.
     * @param playerName the new player's name.
     */
    public void join(C4ViewProxy proxy, String playerName) {
        C4Model model = sessions.get(proxy);

        if (model == null) {
            if (this.sessionWaitingForPlayer == null || this.sessionWaitingForPlayer.isTerminated()) {
                this.sessionWaitingForPlayer = model = new C4Model();
            } else {
                model = this.sessionWaitingForPlayer;

                this.sessionWaitingForPlayer = null;
            }

            sessions.put(proxy, model);
        }

        model.join(proxy, playerName);
        proxy.setViewListener(model);
    }

    /**
     * Adds the view proxy to an existing open session or creates a new session for this proxy.
     *
     * @param listener model listener (which is an instance of C4ViewProxy) representing the
     * client.
     * @param playerName the new player's name.
     */
    public void join(C4ModelListener listener, String playerName) {
        join((C4ViewProxy) listener, playerName);
    }

    /**
     * Empty method to comply with the interface definition of a C4ViewListener.
     *
     * @param playerNumber the player number for which the marker is to be added.;
     * @param column the column in which the marker is to be added.
     */
    public void addMarker(Integer playerNumber, Integer column) { }

    /**
     * Empty method to comply with the interface definition of a C4ViewListener.
     */
    public void clear() { }

    /**
     * Empty method to comply with the interface definition of a C4ViewListener.
     */
    public void leave() { }
}
