package im.fuad.rit.copads.p4;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import im.fuad.rit.copads.p4.C4ModelListener;
import im.fuad.rit.copads.p4.C4ViewListener;

/**
 * Provides the network proxy for the view object in the client. It listen to events fired on the
 * model and report these actions to the game client.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public class C4ViewProxy implements C4ModelListener {
    private DatagramSocket socket;
    private SocketAddress clientAddress;
    private C4ViewListener viewListener;

    /**
     * Initializes the view proxy with a given socket connection and the client's address.
     *
     * @param socket the socket connection to the client.
     * @param clientAddress the client's address.
     */
    public C4ViewProxy(DatagramSocket socket, SocketAddress clientAddress) {
        this.socket = socket;
        this.clientAddress = clientAddress;
    }

    /**
     * Process an incoming message from the given packet.
     *
     * @param packet the DatagramPacket containing the message.
     *
     * @return whether this message should result in the client being diconnected.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public Boolean process(DatagramPacket packet) throws IOException {
        Boolean disconnect = false;

        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(
                    packet.getData(), 0, packet.getLength()));

        Character command = in.readChar();

        switch (command) {
            case 'A':
                Integer playerNumber = in.readInt();
                Integer column = in.readInt();

                this.viewListener.addMarker(playerNumber, column);

                break;
            case 'J':
                String playerName = in.readUTF();

                this.viewListener.join(C4ViewProxy.this, playerName);

                break;
            case 'C':
                this.viewListener.clear();

                break;
            case 'L':
                this.viewListener.leave();

                disconnect = true;
        }

        return disconnect;
    }

    /**
     * Reports the addition of a marker to the game board to the client.
     *
     * @param playerNumber the number of the player who's making the move.
     * @param row the row in which the marker is being added.
     * @param col the column in which the marker is being added.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void markerAdded(Integer playerNumber, Integer row, Integer col) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('A');
        dos.writeInt(playerNumber);
        dos.writeInt(row);
        dos.writeInt(col);

        dos.close();

        sendMessage(baos.toByteArray());
    }

    /**
     * Tells the client that the game board was cleared.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void cleared() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('C');

        dos.close();

        sendMessage(baos.toByteArray());
    }

    /**
     * Notifies the client that the game session has ended.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void left() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('L');

        dos.close();

        sendMessage(baos.toByteArray());
    }

    /**
     * Tells the client it's player number in the game session.
     *
     * @param playerNumber the player's number (1 or 2).
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void number(Integer playerNumber) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('#');
        dos.writeInt(playerNumber);

        dos.close();

        sendMessage(baos.toByteArray());
    }

    /**
     * Informs the client that a player joined the session.
     *
     * @param playerNumber the player's number (1 or 2).
     * @param playerName the player's name.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void name(Integer playerNumber, String playerName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('N');
        dos.writeInt(playerNumber);
        dos.writeUTF(playerName);

        dos.close();

        sendMessage(baos.toByteArray());
    }

    /**
     * Tells the client that a new game turn started.
     *
     * @param playerNumber the player's number (1 or 2).
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void turn(Integer playerNumber) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('T');
        dos.writeInt(playerNumber);

        dos.close();

        sendMessage(baos.toByteArray());
    }

    /**
     * Sets the view listener for this object.
     */
    public void setViewListener(C4ViewListener listener) {
        this.viewListener = listener;
    }


    /**
     * Sends a message to the client.
     *
     * @param payload the message payload.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    private void sendMessage(byte[] payload) throws IOException {
        socket.send(
                new DatagramPacket(
                    payload, payload.length, this.clientAddress));
    }
}
