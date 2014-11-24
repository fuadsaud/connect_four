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

class C4ViewProxy implements C4ModelListener {
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

        switch(command) {
            case 'A':
                Byte playerNumber = in.readByte();
                Byte column = in.readByte();

                this.viewListener.join(this, );

                System.out.println(String.format("%c %d %d", command, playerNumber, column));

                break;
            case 'J':
                String playerName = in.readUTF();

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
    public void markerAdded(Player player, Integer row, Integer col) {
        sendMessage(new int[] { (int) 'A', (int) player.getNumber(), row, col });
    }

    /**
     * @see C4ModelListener.cleared()
     */
    public void cleared() {
        sendMessage(new int[] { (int) 'C' });
    }

    /**
     * @see C4ModelListener.gameStarted()
     */
    public void gameStarted() {}

    /**
     * @see C4ModelListener.gameOver()
     */
    public void gameOver() {}

    /**
     * @see C4ModelListener.turn()
     */
    public void turn(Player player) {
        sendMessage(new int[] { (int) 'T', player.getNumber() });
    }

    /**
     * Sets this object's view listener.
     *
     * @param listener the view listener to be registered.
     */
    public void setViewListener(C4ViewListener listener) {
        this.viewListener = listener;
    }

    private void sendMessage(int[] data) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);

            for (int b : data) { dos.writeByte(b); }

            dos.close();

            byte[] payload = baos.toByteArray();

            socket.send(
                    new DatagramPacket(
                        payload, payload.length, this.clientAddress));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
