package im.fuad.rit.copads.p3;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Arrays;

import im.fuad.rit.copads.p3.Player;

class C4ModelProxy implements C4ViewListener {
    private Socket socket;
    private C4ModelListener modelListener;
    private BufferedWriter writer;
    private Player me;
    private Player opponent;
    private Integer myNumber;

    public C4ModelProxy(Socket socket) throws IOException {
        this.socket = socket;
        this.writer =
            new BufferedWriter(
                   new OutputStreamWriter(this.socket.getOutputStream()));
    }

    public void addMarker(Integer column) throws IOException {
        sendAddMarkerMessage(this.myNumber, column);
    }

    public void clear() throws IOException { sendClearMessage(); }

    public void join(String playerName) throws IOException {
        if (this.me == null) {
            sendJoinMessage(playerName);

            new Thread(new ServerReader()).start();
        }
    }

    public Integer getPlayerNumber() { return this.myNumber; }

    public void setModelListener(C4ModelListener listener) {
        this.modelListener = listener;
    }

    private void sendAddMarkerMessage(Integer player, Integer column) throws IOException {
        sendMessage(String.format("add %d %d", player, column));
    }

    private void sendJoinMessage(String playerName) throws IOException {
        sendMessage(String.format("join %s", playerName));
    }

    private void sendClearMessage() throws IOException {
        sendMessage("clear");
    }

    private void sendMessage(String message) throws IOException {
        System.err.println("SENDING: " + message);
        this.writer.write(message);
        this.writer.newLine();
        this.writer.flush();
    }

    private Player playerForNumber(Integer playerNumber) {
        return playerNumber == myNumber ? me : opponent;
    }

    private class ServerReader implements Runnable {
        public void run() {
            try {
                BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

                String line;

                while ((line = reader.readLine()) != null) {
                    System.err.println("RECEIVED: " + line);

                    processCommand(Arrays.asList(line.split(" ")));
                }

            } catch(IOException e) {
            } finally {
                try { socket.close(); }
                catch (IOException e) {}
                finally { System.exit(0); }
            }
        }

        private void processCommand(List<String> commandParts) {
            String commandName = commandParts.get(0);

            if (commandName.equals("number"))
                number(Integer.valueOf(commandParts.get(1)));
            else if (commandName.equals("name"))
                name(Integer.valueOf(commandParts.get(1)), commandParts.get(2));
            else if (commandName.equals("turn"))
                turn(Integer.valueOf(commandParts.get(1)));
            else if (commandName.equals("add"))
                add(
                        Integer.valueOf(commandParts.get(1)),
                        Integer.valueOf(commandParts.get(2)),
                        Integer.valueOf(commandParts.get(3)));
            else if (commandName.equals("clear"))
                clear();
        }

        private void number(Integer playerNumber) {
            myNumber = playerNumber;
        }

        private void name(Integer playerNumber, String playerName) {
            if (playerNumber == myNumber) {
                me = new Player(playerNumber, playerName, true);
            } else {
                opponent = new Player(playerNumber, playerName, false);

                modelListener.gameStarted();
            }
        }

        private void turn(Integer playerNumber) {
            if (playerNumber == 0)
                modelListener.gameOver();
            else
                modelListener.turn(playerForNumber(playerNumber));
        }

        private void add(Integer playerNumber, Integer row, Integer col) {
            modelListener.markerAdded(playerForNumber(playerNumber), row, col);
        }

        private void clear() { modelListener.cleared(); }
    }
}
