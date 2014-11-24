package im.fuad.rit.copads.p4.server;

import java.net.DatagramSocket;

public class MailboxManager {
    private DatagramSocket socket;
    private HashMap<SocketAddress, ViewProxy> proxies;
    private byte[] payload;

    public MailboxManager(DatagramSocket socket) {
        this.socket = socket;
        this.proxies = new HashMap<SocketAddress, ViewProxy>();
        this.payload = new byte[128];
    }

    public receiveMessage() throws IOException {
        DatagramPacket
    }
}
