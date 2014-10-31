package im.fuad.rit.copads.p3;

import java.net.Socket;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;

class MessageDispatcher {
    private BufferedWriter writer;

    public MessageDispatcher(Socket socket) throws IOException {
        this.writer =
            new BufferedWriter(
                   new OutputStreamWriter(socket.getOutputStream()));
    }

    public void sendAddMarkerMessage(Integer player, Integer column) throws IOException {
        sendMessage(String.format("add %d %d", player, column));
    }

    public void sendJoinMessage(String playerName) throws IOException {
        sendMessage(String.format("join %s", playerName));
    }

    public void sendClearMessage() throws IOException {
        sendMessage("clear");
    }

    private void sendMessage(String message) throws IOException {
        this.writer.write(message);
        this.writer.newLine();
        this.writer.flush();
    }
}
