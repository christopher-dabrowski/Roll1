package pw.chat.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ChatClient implements Runnable, Messenger {
    private final Socket client;
    private final Set<Consumer<String>> registeredListeners = new HashSet<>();

    public ChatClient(String serverIp) throws IOException {
        client = new Socket(serverIp, Configuration.Port);
    }

    public void SendMessage(String message) throws IOException {
        var writer = new PrintWriter(client.getOutputStream());
        writer.write(message.trim() + "\n");
        writer.flush();
    }

    public void RegisterOnMessageReceived(Consumer<String> callback) {
        registeredListeners.add(callback);
    }

    public void RemoveOnMessageReceived(Consumer<String> callback) {
        registeredListeners.remove(callback);
    }


    private synchronized void MessageReceived(String message) {
        for (var listener : registeredListeners) {
            listener.accept(message);
        }
    }

    @Override
    public void run() {
        try {
            var tmp = new MessageReceivedHandler(client, this::MessageReceived);
            tmp.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
