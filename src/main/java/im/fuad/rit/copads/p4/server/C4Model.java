package im.fuad.rit.copads.p4.server;

import java.io.IOException;

import java.util.List;
import java.util.LinkedList;

import im.fuad.rit.copads.p4.C4ModelListener;
import im.fuad.rit.copads.p4.C4ViewListener;
import im.fuad.rit.copads.p4.C4Board;

class C4Model implements C4ViewListener {
    private C4Board board;
    private List<C4ModelListener> modelListeners;

    public C4Model() {
        this.board = new C4Board();
        this.modelListeners = new LinkedList<C4ModelListener>();
    }

    public void addModelListener(C4ModelListener listener) {
        modelListeners.add(listener);
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
        this.board.clear();

        for (C4ModelListener listener : modelListeners) {
            listener.cleared();
        }
    }
}
