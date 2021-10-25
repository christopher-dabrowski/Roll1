package pw.chat.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;

class MessageReceivedHandler implements Runnable {
    private final InputStream inputStream;
    private final Consumer<String> onMessageReceived;

    MessageReceivedHandler(Socket socket, Consumer<String> onMessageReceived) throws IOException {
        inputStream = socket.getInputStream();
        this.onMessageReceived = onMessageReceived;
    }

    @Override
    public void run() {
        var reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                onMessageReceived.accept(line);
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
