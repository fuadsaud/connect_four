package im.fuad.rit.copads.p4.server;

import java.io.IOException;

import java.util.HashMap;

import im.fuad.rit.copads.p4.C4ViewListener;
import im.fuad.rit.copads.p4.C4Board;

class SessionManager implements C4ViewListener {
    private HashMap<Integer, C4Model> sessions;

    public SessionManager() {
        this.sessions = new HashMap<Integer, C4Model>();
    }

    public void join(C4ViewProxy proxy, Integer sessionNumber) {
        C4Model model = sessions.get(sessionNumber);

        if (model == null) {
            model = new C4Model();
            sessions.put(sessionNumber, model);
        }

        model.addModelListener(proxy);
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
