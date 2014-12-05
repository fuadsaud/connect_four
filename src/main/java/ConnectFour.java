import java.util.Arrays;
import java.util.List;

import im.fuad.rit.copads.p4.C4Client;

/**
 * Main class for the client program. Handles command line arguments doing necessary conversions
 * and passes these values to the internal logic classes.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class ConnectFour {
    /**
     * Main method.
     *
     * @param args list of arguments for the execution of this program. It is expected to contain 3
     * elements in the following order: the host of the game server to connect to, the port on
     * which the server is listening and the this client's player's name.
     */
    public static void main(String[] args) { List<String> arguments = Arrays.asList(args);

        if (arguments.size() != 5) {
            System.err.println("Wrong number of arguments: " + arguments.size() + " instead of 3");

            usage();

            return;
        }

        String serverHost = arguments.get(0);
        Integer serverPort = Integer.valueOf(arguments.get(1));
        String clientHost = arguments.get(2);
        Integer clientPort = Integer.valueOf(arguments.get(3));
        String playerName = arguments.get(4);

        new C4Client(serverHost, serverPort, clientHost, clientPort, playerName).call();
    }

    /**
     * Prints usage information to the STDERR.
     */
    private static void usage() {
        System.err.println("USAGE:");
        System.err.println("\tjava ConnectFour <HOST> <PORT> <PLAYER_NAME>");
        System.err.println("\t\t<SERVER_HOST>: the host name or IP address of the server");
        System.err.println("\t\t<SERVER_PORT>: the port number of the server");
        System.err.println("\t\t<CLIENT_HOST>: the host name or IP address of the client");
        System.err.println("\t\t<CLIENT_PORT>: the port number of the client");
        System.err.println("\t\t<PLAYER_NAME>: the name of the player (must not include any whitespace)");
        System.err.println("\n\t\tExample: java ConnectFour localhost 6789 localhost 5678 Alex");
    }
}
