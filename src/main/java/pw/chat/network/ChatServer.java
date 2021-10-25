package pw.chat.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ChatServer implements Runnable, Messenger{
    private final ServerSocket server;
    private boolean shouldRun = true;
    private final Set<Socket> clients = new HashSet<>();
    private final Set<Consumer<String>> registeredListeners = new HashSet<>();

    public ChatServer() throws IOException {
        server = new ServerSocket(Configuration.Port);
    }

    @Override
    public void run() {
        while (shouldRun) {
            try {
                var clientSocket = server.accept();
                clients.add(clientSocket);
                var thread = new Thread(new MessageReceivedHandler(clientSocket, this::MessageReceived));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            for (var client: clients) {
                client.close();
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        shouldRun = false;
    }

    public void RegisterOnMessageReceived(Consumer<String> callback) {
        registeredListeners.add(callback);
    }

    public void RemoveOnMessageReceived(Consumer<String> callback) {
        registeredListeners.remove(callback);
    }

    public void SendMessage(String message) {
        MessageReceived(message);
    }

    private synchronized void MessageReceived(String message) {
        for (var listener : registeredListeners) {
            listener.accept(message);
        }

        for (var client : clients) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(client.getOutputStream());
                writer.write(message + "\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
