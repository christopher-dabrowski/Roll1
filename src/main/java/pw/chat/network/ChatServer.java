package pw.chat.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ChatServer implements Runnable{
    private final ServerSocket server;
    private boolean shouldRun = true;
    private final Set<Consumer<String>> registeredListeners = new HashSet<>();

    public ChatServer() throws IOException {
        server = new ServerSocket(Configuration.Port);
    }

    @Override
    public void run() {
        while (shouldRun) {
            try {
                var clientSocket = server.accept();
                var thread = new Thread(new MessageReceivedHandler(clientSocket));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
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

    private synchronized void MessageReceived(String message) {
        for (var listener : registeredListeners) {
            listener.accept(message);
        }
    }

    private class MessageReceivedHandler implements Runnable {
        private final InputStream inputStream;

        private MessageReceivedHandler(Socket socket) throws IOException {
            inputStream = socket.getInputStream();
        }

        @Override
        public void run() {
            var reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    MessageReceived(line);
                }

                cleanup();
            } catch (IOException e) {
                try {
                    cleanup();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void cleanup() throws IOException {
            inputStream.close();
        }
    }
}
