package im.fuad.rit.copads.p4;

import java.io.IOException;

import java.util.HashMap;

import im.fuad.rit.copads.p4.C4ViewListener;
import im.fuad.rit.copads.p4.C4ModelListener;
import im.fuad.rit.copads.p4.C4Board;

class SessionManager implements C4ViewListener {
    private C4Model sessionWaitingForPlayer;
    private HashMap<C4ModelListener, C4Model> sessions;

    public SessionManager() {
        this.sessions = new HashMap<C4ModelListener, C4Model>();
    }

    public void join(C4ViewProxy proxy, String playerName) {
        C4Model model = sessions.get(proxy);

        if (model == null) {
            if (this.sessionWaitingForPlayer == null) {
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
     * @see C4ViewListener.join()
     */
    public void join(C4ModelListener listener, String playerName) {
        join((C4ViewProxy) listener, playerName);
    }

    /**
     * @see C4ViewListener.addMarker()
     */
    public void addMarker(Integer playerNumber, Integer column) throws IOException { }

    /**
     * @see C4ViewListener.clear()
     */
    public void clear() throws IOException { }

    /**
     * @see C4ViewListener.disconnect()
     */
    public void leave() throws IOException { }
}
