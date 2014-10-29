package im.fuad.rit.copads.p3;

import java.io.IOException;
import java.net.Socket;

class C4ModelProxy implements C4ViewListener {
    private Socket socket;
    private C4ModelListener modelListener;
    private Player me;
    private Player opponent;
    private Integer myNumber;
    private MessageDispatcher dispatcher;

    public C4ModelProxy(Socket socket) throws IOException {
        this.socket = socket;
        this.dispatcher = new MessageDispatcher(socket);
    }

    public void addMarker(Integer column) throws IOException {
        this.dispatcher.sendAddMarkerMessage(this.myNumber, column);
    }

    public void clear() throws IOException {
        this.dispatcher.sendClearMessage();
    }

    public void join(String playerName) throws IOException {
        if (this.me == null) {
            this.dispatcher.sendJoinMessage(playerName);

            new Thread(new ServerReader()).start();
        }
    }

    public Integer getPlayerNumber() { return this.myNumber; }

    public void setModelListener(C4ModelListener listener) {
        this.modelListener = listener;
    }

    private Player playerForNumber(Integer playerNumber) {
        return playerNumber == this.myNumber ? this.me : this.opponent;
    }

    private void terminate() { System.exit(1); }

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

        public void number(Integer playerNumber) {
            myNumber = playerNumber;
        }

        public void name(Integer playerNumber, String playerName) {
            if (playerNumber == myNumber) {
                me = new Player(playerNumber, playerName, true);
            } else {
                opponent = new Player(playerNumber, playerName, false);

                modelListener.gameStarted();
            }
        }

        public void turn(Integer playerNumber) {
            if (playerNumber == 0)
                modelListener.gameOver();
            else
                modelListener.turn(playerForNumber(playerNumber));
        }

        public void add(Integer playerNumber, Integer row, Integer col) {
            modelListener.markerAdded(playerForNumber(playerNumber), row, col);
        }

        public void clear() { modelListener.cleared(); }
    }
}
