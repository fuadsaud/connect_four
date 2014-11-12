import java.util.Arrays;
import java.util.List;

import im.fuad.rit.copads.p3.C4Client;

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

        if (arguments.size() != 3) {
            usage();

            return;
        }

        String host = arguments.get(0);
        Short port = Short.valueOf(arguments.get(1));
        String playerName = arguments.get(2);

        new C4Client(host, port, playerName).call();
    }

    /**
     * Prints usage information to the standard output.
     */
    private static void usage() {
        System.out.println("USAGE:");
        System.out.println("\tjava ConnectFour <HOST> <PORT> <PLAYER_NAME>");
        System.out.println("\t\t<HOST>: the host name or IP address of the server");
        System.out.println("\t\t<PORT>: the port number of the server");
        System.out.println("\t\t<PLAYER_NAME>: the name of the player (must not include any whitespace)");
        System.out.println("\n\t\tExample: java ConnectFour localhost 5678 Alex");
    }
}
