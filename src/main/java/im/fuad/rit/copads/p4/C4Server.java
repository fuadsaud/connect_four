package im.fuad.rit.copads.p4;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.DatagramSocket;
import java.net.SocketException;

import im.fuad.rit.copads.p4.server.MailboxManager;

public class C4Server {
    private InetSocketAddress address;

    /**
     * Initializes the game server.
     *
     * @param host the game server host adress.
     * @param port the game server port.
     */
    public C4Server(String host, Integer port) {
        this.address = new InetSocketAddress(host, port);
    }

    /**
     * Fires the execution of this server, wiring up necessary listeners and socket
     * connections.
     */
    public void call() {
        try {
            DatagramSocket socket = new DatagramSocket(this.address);

            MailboxManager manager = new MailboxManager(socket);

            while (true) {
                manager.receiveMessage();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
