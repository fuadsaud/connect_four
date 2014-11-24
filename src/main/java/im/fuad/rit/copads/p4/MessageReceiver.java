package im.fuad.rit.copads.p4;

import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

import java.util.List;
import java.util.Arrays;

import im.fuad.rit.copads.p4.C4ModelListener;

/**
 * This class is responsible for listening to incoming messages from the server through a socket
 * and based on these fire appropriate methods on a server listener object.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class MessageReceiver {
    private DatagramSocket socket;
    private C4ModelListener listener;

    /**
     * Initializes a receiver but don't start reading from the socket.
     *
     * @param socket the socket to be read from; it is expected to be already connected.
     * @param listener the listener on which to fire events when messages are received.
     */
    public MessageReceiver(DatagramSocket socket, C4ModelListener listener) throws IOException {
        this.socket = socket;
        this.listener = listener;
    }

    /**
     * Starts reading from the socket.
     */
    public void listen() throws IOException {
        byte[] payload = new byte[128];

        while (true) {
            DatagramPacket packet = new DatagramPacket(payload, payload.length);

            socket.receive(packet);

            DataInputStream in = new DataInputStream(
                    new ByteArrayInputStream(payload, 0, packet.getLength()));

            Character command = in.readChar();

            switch (command) {
                case '#':
                    System.out.println('#');
                    this.listener.number(in.readInt());

                    break;
                case 'N':
                    System.out.println('N');
                    this.listener.name(in.readInt(), in.readUTF());

                    break;
                case 'T':
                    System.out.println('T');
                    this.listener.turn(in.readInt());

                    break;
                case 'A':
                    System.out.println('A');
                    this.listener.markerAdded(in.readInt(), in.readInt(), in.readInt());

                    break;
                case 'C':
                    System.out.println('C');
                    this.listener.cleared();

                    break;
                case 'L':
                    System.out.println('L');
                    this.listener.left();

                    break;
            }
        }
    }
}
