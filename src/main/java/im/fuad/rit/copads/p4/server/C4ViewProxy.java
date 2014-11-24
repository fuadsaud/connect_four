package im.fuad.rit.copads.p4.server;

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
import im.fuad.rit.copads.p4.Player;

public class C4ViewProxy implements C4ModelListener {
    private DatagramSocket socket;
    private SocketAddress clientAddress;
    private C4ViewListener viewListener;
    private SessionManager sessionManager;

    public C4ViewProxy(DatagramSocket socket, SocketAddress clientAddress,
            SessionManager sessionManager) {
        this.socket = socket;
        this.clientAddress = clientAddress;
        this.sessionManager = sessionManager;
    }

    public Boolean process(DatagramPacket packet) throws IOException {
        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(
                    packet.getData(), 0, packet.getLength()));

        Character command = in.readChar();

        System.out.println(String.format("COMMAND: %s", command));

        switch(command) {
            case 'A':
                Byte playerNumber = in.readByte();
                Byte column = in.readByte();

                // this.viewListener.addMarker(column);

                System.out.println(String.format("%c %d %d", command, playerNumber, column));

                break;
            case 'J':
                String playerName = in.readUTF();

                this.viewListener = this.sessionManager.joinSession(this);

                this.viewListener.join(this, playerName);

                System.out.println(String.format("%c %s", command, playerName));

                break;
            case 'C':
                System.out.println(command);

                break;
        }

        return false;
    }

    /**
     * @see C4ModelListener.markerAdded()
     */
    public void markerAdded(Player player, Integer row, Integer col) throws IOException { }

    /**
     * @see C4ModelListener.cleared()
     */
    public void cleared() throws IOException { }

    /**
     * @see C4ModelListener.gameStarted()
     */
    public void gameStarted() throws IOException {}

    /**
     * @see C4ModelListener.gameOver()
     */
    public void gameOver() throws IOException {}

    /**
     * @see C4ModelListener.turn()
     */
    public void turn(Player player) throws IOException { }

    public void number(Integer playerNumber) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('#');
        dos.writeInt(playerNumber);

        dos.close();

        sendMessage(baos.toByteArray());
    }

    public void name(Integer playerNumber, String playerName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('N');
        dos.writeInt(playerNumber);
        dos.writeUTF(playerName);

        dos.close();

        sendMessage(baos.toByteArray());
    }

    public void turn(Integer playerNumber) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('T');
        dos.writeInt(playerNumber);

        dos.close();

        sendMessage(baos.toByteArray());
    }

    public void add(Integer playerNumber, Integer row, Integer col) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('A');
        dos.writeInt(playerNumber);
        dos.writeInt(row);
        dos.writeInt(col);

        dos.close();

        sendMessage(baos.toByteArray());
    }

    public void clear() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('C');

        dos.close();

        sendMessage(baos.toByteArray());
    }

    private void sendMessage(byte[] payload) throws IOException {
        socket.send(
                new DatagramPacket(
                    payload, payload.length, this.clientAddress));
    }
}
