import java.util.Arrays;
import java.util.List;

import im.fuad.rit.copads.p4.C4Server;

/**
 * Main class for the server program. Handles command line arguments doing necessary conversions
 * and passes these values to the internal logic classes.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class ConnectFourServer {
    /**
     * Main method.
     *
     * @param args list of arguments for the execution of this program. It is expected to contain 2
     * elements in the following order: the host in which the game server is to be run and the port on
     * which the server is to listen.
     */
    public static void main(String[] args) {
        List<String> arguments = Arrays.asList(args);

        if (arguments.size() != 2) {
            System.err.println("Wrong number of arguments: " + arguments.size() + " instead of 3");

            usage();

            return;
        }

        String host = arguments.get(0);
        Integer port = Integer.valueOf(arguments.get(1));

        new C4Server(host, port).call();
    }

    /**
     * Prints usage information to the STDERR.
     */
    private static void usage() {
        System.err.println("USAGE:");
        System.err.println("\tjava ConnectFourServer <HOST> <PORT>");
        System.err.println("\t\t<HOST>: the host name or IP address of the server");
        System.err.println("\t\t<PORT>: the port number of the server");
        System.err.println("\n\t\tExample: java ConnectFour localhost 5678");
    }
}
