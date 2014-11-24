package im.fuad.rit.copads.p4.server;

import java.io.IOException;

import java.util.HashMap;

class SessionManager {
    private C4Model sessionWaitingForPlayer;
    private HashMap<C4ViewProxy, C4Model> sessions;

    public SessionManager() {
        this.sessions = new HashMap<C4ViewProxy, C4Model>();
    }

    public C4Model joinSession(C4ViewProxy proxy) {
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

        return model;
    }
}
