package im.fuad.rit.copads.p4.server;

import java.io.IOException;

import java.util.HashMap;

import im.fuad.rit.copads.p4.C4ViewListener;
import im.fuad.rit.copads.p4.C4ModelListener;
import im.fuad.rit.copads.p4.C4Board;

class SessionManager implements C4ViewListener {
    private HashMap<String, C4Model> sessions;

    public SessionManager() {
        this.sessions = new HashMap<String, C4Model>();
    }

    public void join(C4ModelListener listener, String playerName) {
        C4Model model = sessions.get(playerName);

        if (model == null) {
            model = new C4Model();
            sessions.put(playerName, model);
        }

        model.join(listener, playerName);
    }

    /**
     * @see C4ViewListener.addMarker()
     */
    public void addMarker(Integer column) throws IOException {

    }

    /**
     * @see C4ViewListener.clear()
     */
    public void clear() throws IOException {

    }
}
