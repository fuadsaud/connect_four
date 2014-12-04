package im.fuad.rit.copads.p4;

import java.io.IOException;

import java.net.DatagramSocket;
import java.net.SocketAddress;

import im.fuad.rit.copads.p4.C4ViewProxy;

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
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public C4ModelProxy(DatagramSocket socket, SocketAddress serverAddress) throws IOException {
        this.socket = socket;
        this.dispatcher = new MessageDispatcher(socket, serverAddress);
    }

    /**
     * Informs the model that a marker should be added for the current player in the given column.
     *
     * @param playerNumber the player number for which the marker is to be added.;
     * @param column the column in which the marker is to be added.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void addMarker(Integer playerNumber, Integer column) throws IOException {
        this.dispatcher.sendAddMarkerMessage(this.myNumber, column);
    }

    /**
     * Joins this game model.
     *
     * @param listener the object to listen for model events for this new player.
     * @param playerName this new player's name.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void join(C4ModelListener modelListener, String playerName) throws IOException {
        if (this.myNumber == null) {
            this.dispatcher.sendJoinMessage(playerName);

            new Thread(new ServerReader()).start();
        }
    }

    /**
     * Informs the model to clear the game board.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void clear() throws IOException {
        this.dispatcher.sendClearMessage();
    }

    /**
     * Leave the game model.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void leave() throws IOException {
        this.dispatcher.sendLeaveMessage();
    }

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
        /**
         * Main thread execution method. Starts Message receiver and tell it to listen for new
         * messsages from the server.
         */
        public void run() {
            try { new MessageReceiver(socket, this).listen(); }
            catch(IOException e) { }
            finally {
                 socket.close();
                 terminate();
            }
        }

    /**
     * Sets this client's player number.
     *
     * @param playerNumber the player's number (1 or 2).
     *
     * @exception IOException thrown if an I/O error occurred.
     */
        public void number(Integer playerNumber) throws IOException {
            myNumber = playerNumber;

            modelListener.number(playerNumber);
        }

    /**
     * Registers a player in the session. If it is the opponent that's being registered it also
     * fires the start event in the model listener.
     *
     * @param playerNumber the player's number (1 or 2).
     * @param playerName the player's name.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
        public void name(Integer playerNumber, String playerName) throws IOException {
            if (playerNumber == myNumber)
                myName = playerName;

            modelListener.name(playerNumber, playerName);
        }

        /**
         * Initiates a new turn in the game for the given player.
         *
         * @param playerNumber the player's number (1 or 2).
         *
         * @exception IOException thrown if an I/O error occurred.
         */
        public void turn(Integer playerNumber) throws IOException {
            modelListener.turn(playerNumber);
        }

        /**
         * Hook to be called when a marker is added to the board.
         *
         * @param playerNumber the number of the player who's making the move.
         * @param row the row in which the marker is being added.
         * @param col the column in which the marker is being added.
         *
         * @exception IOException thrown if an I/O error occurred.
         */
        public void markerAdded(Integer playerNumber, Integer row, Integer col) throws IOException {
            modelListener.markerAdded(playerNumber, row, col);
        }

        /**
         * Hook to be called when the board is cleared.
         *
         * @exception IOException thrown if an I/O error occurred.
         */
        public void cleared() throws IOException { modelListener.cleared(); }

        /**
         * Notifies that the game session was terminated because a player left it.
         *
         * @exception IOException thrown if an I/O error occurred.
         */
        public void left() throws IOException { modelListener.left(); }
    }
}
