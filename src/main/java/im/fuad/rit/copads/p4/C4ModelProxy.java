package im.fuad.rit.copads.p4;

import java.io.IOException;

import java.net.DatagramSocket;
import java.net.SocketAddress;

import im.fuad.rit.copads.p4.server.C4ViewProxy;

/**
 * Provides the network proxy for the model object in the server. It listen to events fired on the
 * view and report these actions to the game server.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class C4ModelProxy implements C4ViewListener {
    private DatagramSocket socket;
    private C4ModelListener modelListener;
    private Integer myNumber;
    private String myName;
    private MessageDispatcher dispatcher;

    /**
     * Initializes a model proxy.
     *
     * @param socket the socket used to communicate with the server.
     */
    public C4ModelProxy(DatagramSocket socket, SocketAddress serverAddress) throws IOException {
        this.socket = socket;
        this.dispatcher = new MessageDispatcher(socket, serverAddress);
    }

    /**
     * @see C4ViewListener.addMarker()
     */
    public void addMarker(Integer column) throws IOException {
        this.dispatcher.sendAddMarkerMessage(this.myNumber, column);
    }

    /**
     * @see C4ViewListener.clear()
     */
    public void clear() throws IOException {
        this.dispatcher.sendClearMessage();
    }

    /**
     * Informs the model that this player is joining the game session with the given name.
     */
    public void join(C4ModelListener modelListener, String playerName) throws IOException {
        if (this.myNumber == null) {
            this.dispatcher.sendJoinMessage(playerName);

            new Thread(new ServerReader()).start();
        }
    }

    /**
     * Gets this players' number.
     *
     * @return this player's number.
     */
    public Integer getMyNumber() { return this.myNumber; }

    /**
     * Sets this object's model listener.
     *
     * @param listener the model listener to be registered.
     */
    public void setModelListener(C4ModelListener listener) {
        this.modelListener = listener;
    }

    /**
     * Signals this client to terminate.
     */
    private void terminate() { C4Client.terminate(); }

    /**
     * Runnable task for reading data from the server. Implements a server listener interaface to
     * respond to server events and make modification on this model proxy object.
     *
     * @author Fuad Saud <ffs3415@rit.edu>
     */
    private class ServerReader implements Runnable, C4ModelListener {
        public void run() {
            try { new MessageReceiver(socket, this).listen(); }
            catch(IOException e) { }
            finally {
                 socket.close();
                 terminate();
            }
        }

        /**
         * @see C4ModelListener.number()
         */
        public void number(Integer playerNumber) throws IOException {
            myNumber = playerNumber;

            modelListener.number(playerNumber);
        }

        /**
         * @see C4ModelListener.name()
         */
        public void name(Integer playerNumber, String playerName) throws IOException {
            if (playerNumber == myNumber)
                myName = playerName;

            modelListener.name(playerNumber, playerName);
        }

        /**
         * @see C4ModelListener.turn()
         */
        public void turn(Integer playerNumber) throws IOException {
            modelListener.turn(playerNumber);
        }

        /**
         * @see C4ModelListener.markerAdded()
         */
        public void markerAdded(Integer playerNumber, Integer row, Integer col) throws IOException {
            modelListener.markerAdded(playerNumber, row, col);
        }

        /**
         * @see C4ModelListener.cleared()
         */
        public void cleared() throws IOException { modelListener.cleared(); }
    }
}
