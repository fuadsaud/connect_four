package im.fuad.rit.copads.p4;

import java.io.IOException;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import im.fuad.rit.copads.p4.ui.C4UI;

/** This class is responsible for setting up objects and connections necessary for the client to
 * communicate with the server.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public class C4Client {
    /** An aplication-wide method to terminate the currently running client with an error status.
     * This is a workaround for the fact that the Runnable interface was used to model tasks to be
     * executed in separate threads and those cannot throw exceptions (exception handling is a
     * resposibility of the Runnable object). Ideally the code would be refactored to use the
     * Callable interface, which allows for tasks to "throw" exceptions, so handling could be
     * delegated to the calling code.
     */

    public static void terminateWithError() {
        System.err.println("An error occurred\n");
        System.exit(1);
    }

    /**
     * An aplication-wide method to terminate the currently running client. This is a workaround
     * for the fact that the Runnable interface was used to model tasks to be executed in separate
     * threads and those cannot throw exceptions (exception handling is a resposibility of the
     * Runnable object). Ideally the code would be refactored to use the Callable interface, which
     * allows for tasks to "throw" exceptions, so handling could be delegated to the calling code.
     */
    public static void terminate() { System.exit(0); }

    private InetSocketAddress serverAddress;
    private InetSocketAddress clientAddress;
    private String playerName;

    /**
     * Initializes a client.
     *
     * @param serverHost the game server host address.
     * @param serverPort the game server port.
     * @param clientHost the game client host address.
     * @param clientPort the game client port.
     * @param playerName this client's player name.
     */
    public C4Client(String serverHost, Integer serverPort, String clientHost, Integer clientPort, String playerName) {
        this.serverAddress = new InetSocketAddress(serverHost, serverPort);
        this.clientAddress = new InetSocketAddress(clientHost, clientPort);
        this.playerName = playerName;
    }

    /**
     * Fires the execution of this client's object, wiring up necessary listeners, socket
     * connections and displaying the UI.
     */
    public void call() {
        try {
            DatagramSocket socket = new DatagramSocket(this.clientAddress);

            C4Board board = new C4Board();
            C4ModelClone modelClone = new C4ModelClone(board);
            C4ModelProxy modelProxy = new C4ModelProxy(socket, this.serverAddress);
            C4UI ui = new C4UI(board, this.playerName);

            modelClone.setModelListener(ui);
            ui.setViewListener(modelProxy);
            modelProxy.setModelListener(modelClone);

            modelProxy.join(null, playerName);
        } catch(IOException e) {
            C4Client.terminateWithError();
        }
    }
}
