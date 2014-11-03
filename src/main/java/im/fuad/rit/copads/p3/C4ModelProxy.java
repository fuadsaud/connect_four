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
         * @see C4ServerListener.number()
         */
        public void number(Integer playerNumber) {
            myNumber = playerNumber;
        }

        /**
         * @see C4ServerListener.name()
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
         * @see C4ServerListener.turn()
         */
        public void turn(Integer playerNumber) {
            if (playerNumber == 0)
                modelListener.gameOver();
            else
                modelListener.turn(playerForNumber(playerNumber));
        }

        /**
         * @see C4ServerListener.add()
         */
        public void add(Integer playerNumber, Integer row, Integer col) {
            modelListener.markerAdded(playerForNumber(playerNumber), row, col);
        }

        /**
         * @see C4ServerListener.clear()
         */
        public void clear() { modelListener.cleared(); }
    }
}
