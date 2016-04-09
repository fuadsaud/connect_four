package im.fuad.rit.copads.p4;

import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import java.util.HashMap;

/**
 * This class manages incoming datagram packets and forward them to the appropriate view proxy
 * object to handle.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public class MailboxManager {
    private DatagramSocket socket;
    private HashMap<SocketAddress, C4ViewProxy> proxies;
    private SessionManager sessionManager;
    private byte[] payload;

    /**
     * Initializes the MailboxManage with the socket connection.
     *
     * @param socket the socket connection.
     */
    public MailboxManager(DatagramSocket socket) {
        this.socket = socket;
        this.proxies = new HashMap<SocketAddress, C4ViewProxy>();
        this.payload = new byte[128];
        this.sessionManager = new SessionManager();
    }

    /**
     * Reads an incoming packet from the socket connection, find (or creates) and tells the
     * appropriate view proxy to handle it.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void receiveMessage() throws IOException {
        DatagramPacket packet = new DatagramPacket(payload, payload.length);

        socket.receive(packet);

        SocketAddress clientAddress = packet.getSocketAddress();

        C4ViewProxy proxy = proxies.get(clientAddress);

        if (proxy == null) {
            proxy = new C4ViewProxy(socket, clientAddress);

            proxy.setViewListener(sessionManager);

            proxies.put(clientAddress, proxy);
        }

        if (proxy.process(packet))
            proxies.remove(clientAddress);
    }
}
