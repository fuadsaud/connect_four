package im.fuad.rit.copads.p3;

import java.io.IOException;
import java.net.Socket;

/**
 * Provides the network proxy for the model object in the server. It listen to events fired on the
 * view and report these actions to the game server.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class C4ModelProxy implements C4ViewListener {
    private Socket socket;
    private C4ModelListener modelListener;
    private Player me;
    private Player opponent;
    private Integer myNumber;
    private MessageDispatcher dispatcher;

    /**
     * Initializes a model proxy.
     *
     * @param socket the socket used to communicate with the server.
     */
    public C4ModelProxy(Socket socket) throws IOException {
        this.socket = socket;
        this.dispatcher = new MessageDispatcher(socket);
    }

    /**
     * Informs the model that a marker should be added for this player in the given column.
     *
     * @param column the column in which the marker is to be added.
     */
    public void addMarker(Integer column) throws IOException {
        this.dispatcher.sendAddMarkerMessage(this.myNumber, column);
    }

    /**
     * Informs the model to clear tha game board.
     */
    public void clear() throws IOException {
        this.dispatcher.sendClearMessage();
    }

    /**
     * Informs the model that this player is joining the game session with the given name.
     */
    public void join(String playerName) throws IOException {
        if (this.me == null) {
            this.dispatcher.sendJoinMessage(playerName);

            new Thread(new ServerReader()).start();
        }
    }

    /**
     * Gets this players' number.
     *
     * @return this player's number.
     */
    public Integer getPlayerNumber() { return this.myNumber; }

    /**
     * Sets this object's model listener.
     *
     * @param listener the model listener to be registered.
     */
    public void setModelListener(C4ModelListener listener) {
        this.modelListener = listener;
    }

    private Player playerForNumber(Integer playerNumber) {
        return playerNumber == this.myNumber ? this.me : this.opponent;
    }

    private void terminate() { C4Client.terminate(); }

    /**
     * Runnable task for reading data from the server. Implements a server listener interaface to
     * respond to server events and make modification on this model proxy object.
     *
     * @author Fuad Saud <ffs3415@rit.edu>
     */
    private class ServerReader implements Runnable, C4ServerListener {
        public void run() {
            try { new MessageReceiver(socket, this).listen(); }
            catch(IOException e) { }
            finally {
                try { socket.close(); }
                catch (IOException e) {}
                finally { terminate(); }
            }
        }

        /**
         * Sets this client's player number.
         *
         * @param playerNumber the player's number (1 or 2).
         */
        public void number(Integer playerNumber) {
            myNumber = playerNumber;
        }

        /**
         * Registers a player in the session. If it is the opponent that's being registered it also
         * fires the start event in the model listener.
         *
         * @param playerNumber the player's number (1 or 2).
         * @param playerName the player's name.
         */
        public void name(Integer playerNumber, String playerName) {
            if (playerNumber == myNumber) {
                me = new Player(playerNumber, playerName, true);
            } else {
                opponent = new Player(playerNumber, playerName, false);

                modelListener.gameStarted();
            }
        }

        /**
         * Initiates a new turn in the game for the given player.
         *
         * @param playerNumber the player's number (1 or 2).
         */
        public void turn(Integer playerNumber) {
            if (playerNumber == 0)
                modelListener.gameOver();
            else
                modelListener.turn(playerForNumber(playerNumber));
        }

        /**
         * Add a marker for the given player in the given position.
         *
         * @param playerNumber the player's number (1 or 2).
         * @param row the row in which to add the marker.
         * @param column the column in which to add the marker.
         */
        public void add(Integer playerNumber, Integer row, Integer col) {
            modelListener.markerAdded(playerForNumber(playerNumber), row, col);
        }

        /**
         * Clear the game board.`
         */
        public void clear() { modelListener.cleared(); }
    }
}
