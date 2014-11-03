package im.fuad.rit.copads.p3;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import im.fuad.rit.copads.p3.C4ServerListener;

/**
 * This class is responsible for listening to incoming messages from the server through a socket
 * and based on these fire appropriate methods on a server listener object.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class MessageReceiver {
    private C4ServerListener listener;
    private BufferedReader reader;

    /**
     * Initializes a receiver but don't start reading from the socket.
     *
     * @param socket the socket to be read from; it is expected to be already connected.
     * @param listener the listener on which to fire events when messages are received.
     */
    public MessageReceiver(Socket socket, C4ServerListener listener) throws IOException {
        this.listener = listener;
        this.reader =
            new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Starts reading from the socket.
     */
    public void listen() throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            processCommand(Arrays.asList(line.split(" ")));
        }
    }

    private void processCommand(List<String> commandParts) {
        String commandName = commandParts.get(0);

        if (commandName.equals("number"))      number(commandParts);
        else if (commandName.equals("name"))   name(commandParts);
        else if (commandName.equals("turn"))   turn(commandParts);
        else if (commandName.equals("add"))    add(commandParts);
        else if (commandName.equals("clear"))  clear(commandParts);
    }

    private void number(List<String> commandParts) {
        this.listener.number(Integer.valueOf(commandParts.get(1)));
    }

    private void name(List<String> commandParts) {
        this.listener.name(
                Integer.valueOf(commandParts.get(1)),
                commandParts.get(2));
    }

    private void turn(List<String> commandParts) {
        this.listener.turn(Integer.valueOf(commandParts.get(1)));
    }

    private void add(List<String> commandParts) {
        this.listener.add(
                Integer.valueOf(commandParts.get(1)),
                Integer.valueOf(commandParts.get(2)),
                Integer.valueOf(commandParts.get(3)));
    }

    private void clear(List<String> commandParts) {
        this.listener.clear();
    }
}
