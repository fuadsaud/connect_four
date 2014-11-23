package im.fuad.rit.copads.p4;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.io.IOException;

import im.fuad.rit.copads.p4.ui.C4UI;

/**
 * This class is responsible for setting up objects and connections necessary for the client to
 * communicate with the server.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public class C4Client {
    /**
     * An aplication-wide method to terminate the currently running client. This is a workaround
     * for the fact that the Runnable interface was used to model tasks to be executed in separate
     * threads and those cannot throw exceptions (exception handling is a resposibility of the
     * Runnable object). Ideally the code would be refactored to use the Callable interface, which
     * allows for tasks to "throw" exceptions, so handling could be delegated to the calling code.
     */
    public static void terminate() {
        System.err.println("An error occurred\n");
        System.exit(1);
    }

    private InetSocketAddress address;
    private String playerName;

    /**
     * Initializes a client.
     *
     * @param host the game server host adress.
     * @param port the game server port.
     * @param playerName this client's player name.
     */
    public C4Client(String host, Short port, String playerName) {
        this.address = new InetSocketAddress(host, port);
        this.playerName = playerName;
    }

    /**
     * Fires the execution of this client's object, wiring up necessary listeners, socket
     * connections and displaying the UI.
     */
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
            C4Client.terminate();
        }
    }
}
