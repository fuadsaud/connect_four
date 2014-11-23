package im.fuad.rit.copads.p4;

import java.net.Socket;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;

/**
 * This class offers methods to write commands that are part of this application communication
 * protocol to a given socket.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class MessageDispatcher {
    private BufferedWriter writer;

    /**
     * Initializes a dispatcher.
     *
     * @param socket the socket to be write commands to.
     */
    public MessageDispatcher(Socket socket) throws IOException {
        this.writer =
            new BufferedWriter(
                   new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Sends an 'add' message to the game server.
     *
     * @param player the player number for which to make the move.
     * @param column the column selected to add the marker.
     */
    public void sendAddMarkerMessage(Integer player, Integer column) throws IOException {
        sendMessage(String.format("add %d %d", player, column));
    }

    /**
     * Sends a 'join' message to the game server.
     *
     * @param playerName the name of the player who's joining the session.
     */
    public void sendJoinMessage(String playerName) throws IOException {
        sendMessage(String.format("join %s", playerName));
    }

    /**
     * Sends a 'clear' message to the game server.
     */
    public void sendClearMessage() throws IOException {
        sendMessage("clear");
    }

    /**
     * Writes a string to the socket.
     *
     * @param message the message to be written to the socket.
     */
    private void sendMessage(String message) throws IOException {
        this.writer.write(message);
        this.writer.newLine();
        this.writer.flush();
    }
}
