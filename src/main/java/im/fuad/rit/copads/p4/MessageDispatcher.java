package im.fuad.rit.copads.p4;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketAddress;

/**
 * This class offers methods to write commands that are part of this application communication
 * protocol to a given socket.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class MessageDispatcher {
    private DatagramSocket socket;
    private SocketAddress serverAddress;

    /**
     * Initializes a dispatcher.
     *
     * @param socket the socket to be write commands to.
     */
    public MessageDispatcher(DatagramSocket socket, SocketAddress serverAddress)
        throws IOException {
        this.socket = socket;
        this.serverAddress = serverAddress;
    }

    /**
     * Sends an 'add' message to the game server.
     *
     * @param player the player number for which to make the move.
     * @param column the column selected to add the marker.
     */
    public void sendAddMarkerMessage(Integer player, Integer column) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('A');
        dos.writeInt(player);
        dos.writeInt(column);

        dos.close();

        sendMessage(baos.toByteArray());
    }

    /**
     * Sends a 'join' message to the game server.
     *
     * @param playerName the name of the player who's joining the session.
     */
    public void sendJoinMessage(String playerName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('J');
        dos.writeUTF(playerName);

        dos.close();

        sendMessage(baos.toByteArray());
    }

    /**
     * Sends a 'clear' message to the game server.
     */
    public void sendClearMessage() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('C');

        dos.close();

        sendMessage(baos.toByteArray());

    }

    /**
     * Writes a byte array to the socket.
     *
     * @param message the message to be written to the socket.
     */
    private void sendMessage(byte[] payload) throws IOException {
        this.socket.send(
                new DatagramPacket(payload, payload.length, serverAddress));
    }
}
