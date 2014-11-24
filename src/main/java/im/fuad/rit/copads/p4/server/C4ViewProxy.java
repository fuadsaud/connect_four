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

public class C4ViewProxy implements C4ModelListener {
    private DatagramSocket socket;
    private SocketAddress clientAddress;
    private C4ViewListener viewListener;

    public C4ViewProxy(DatagramSocket socket, SocketAddress clientAddress) {
        this.socket = socket;
        this.clientAddress = clientAddress;
    }

    public Boolean process(DatagramPacket packet) throws IOException {
        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(
                    packet.getData(), 0, packet.getLength()));

        Character command = in.readChar();

        System.out.println(String.format("COMMAND: %s", command));

        switch (command) {
            case 'A':
                Integer playerNumber = in.readInt();
                Integer column = in.readInt();

                this.viewListener.addMarker(playerNumber, column);

                System.out.println(String.format("%c %d %d", command, playerNumber, column));

                break;
            case 'J':
                String playerName = in.readUTF();

                this.viewListener.join(C4ViewProxy.this, playerName);

                System.out.println(String.format("%c %s", command, playerName));

                break;
            case 'C':
                this.viewListener.clear();

                System.out.println(command);

                break;
        }

        return false;
    }

    /**
     * @see C4ModelListener.markerAdded()
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
     * @see C4ModelListener.cleared()
     */
    public void cleared() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeChar('C');

        dos.close();

        sendMessage(baos.toByteArray());
    }

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

    public void setViewListener(C4ViewListener listener) {
        this.viewListener = listener;
    }

    private void sendMessage(byte[] payload) throws IOException {
        socket.send(
                new DatagramPacket(
                    payload, payload.length, this.clientAddress));
    }
}
