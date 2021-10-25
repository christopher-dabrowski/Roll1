package pw.chat.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient implements Runnable {
    private final Socket client;

    public ChatClient(String serverIp) throws IOException {
        client = new Socket(serverIp, Configuration.Port);
    }

    public void SendMessage(String message) throws IOException {
        var writer = new PrintWriter(client.getOutputStream());
        writer.write(message + "\n");
        writer.flush();
    }

    @Override
    public void run() {

    }
}
