package im.fuad.rit.copads.p3;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.io.IOException;

import im.fuad.rit.copads.p3.ui.C4UI;

public class C4Client {
    private InetSocketAddress address;
    private String playerName;

    public C4Client(String host, Short port, String playerName) {
        this.address = new InetSocketAddress(host, port);
        this.playerName = playerName;
    }

    public void call() {
        try {
            Socket socket = new Socket();

            socket.connect(this.address);

            C4Board board = new C4Board();
            C4ModelClone modelClone = new C4ModelClone(board);
            C4ModelProxy modelProxy = new C4ModelProxy(socket);
            C4UI ui = new C4UI(board, this.playerName);

            modelClone.setModelListener(ui);
            ui.setViewListener(modelProxy);
            modelProxy.setModelListener(modelClone);

            modelProxy.join(playerName);
        } catch(IOException e) {
            log("An IO error ocurred");
            System.exit(1);
        }
    }

    private void log(String message) { System.err.println(message); }
}
