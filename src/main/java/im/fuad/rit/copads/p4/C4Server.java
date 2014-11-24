package im.fuad.rit.copads.p4;

import java.net.InetSocketAddress;
import java.net.DatagramSocket;

import im.fuad.rit.copads.p4.server.MailboxManager;

public class C4Server {
    private InetSocketAddress address;

    /**
     * Initializes the game server.
     *
     * @param host the game server host adress.
     * @param port the game server port.
     */
    public C4Server(String host, Short port) {
        this.address = new InetSocketAddress(host, port);
    }

    /**
     * Fires the execution of this server, wiring up necessary listeners and socket
     * connections.
     */
    public void call() {
        DatagramSocket socket = new DatagramSocket(this.address);

        MailboxManager manager = new MailboxManager(socket);
    }
}
