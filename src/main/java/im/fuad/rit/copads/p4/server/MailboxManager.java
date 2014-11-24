package im.fuad.rit.copads.p4.server;

import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import java.util.HashMap;

public class MailboxManager {
    private DatagramSocket socket;
    private HashMap<SocketAddress, C4ViewProxy> proxies;
    private SessionManager sessionManager;
    private byte[] payload;

    public MailboxManager(DatagramSocket socket) {
        this.socket = socket;
        this.proxies = new HashMap<SocketAddress, C4ViewProxy>();
        this.payload = new byte[128];
        this.sessionManager = new SessionManager();
    }

    public void receiveMessage() throws IOException {
        DatagramPacket packet = new DatagramPacket(payload, payload.length);

        socket.receive(packet);

        SocketAddress clientAddress = packet.getSocketAddress();

        C4ViewProxy proxy = proxies.get(clientAddress);

        if (proxy == null) {
            proxy = new C4ViewProxy(socket, clientAddress, this.sessionManager);

            proxies.put(clientAddress, proxy);
        }

        if (proxy.process(packet))
            proxies.remove(clientAddress);
    }
}
