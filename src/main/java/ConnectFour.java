import java.util.Arrays;
import java.util.List;

import im.fuad.rit.copads.p3.C4Client;

class ConnectFour {
    public static void main(String[] args) {
        List<String> arguments = Arrays.asList(args);

        if (arguments.size() != 3) {
            usage();

            return;
        }

        String host = arguments.get(0);
        Short port = Short.valueOf(arguments.get(1));
        String playerName = arguments.get(2);

        new C4Client(host, port, playerName).call();
    }

    public static void processArguments(List<String> arguments) {
    }

    public static void usage() {
        System.out.println("USAGE:");
        System.out.println("\tjava ConnectFour <HOST> <PORT> <PLAYER_NAME>");
        System.out.println("\t\t<HOST>: the host name or IP address of the server");
        System.out.println("\t\t<PORT>: the port number of the server");
        System.out.println("\t\t<PLAYER_NAME>: the name of the player (must not include any whitespace)");
        System.out.println("\n\t\tExample: java ConnectFour localhost 5678 Alex");
    }
}
