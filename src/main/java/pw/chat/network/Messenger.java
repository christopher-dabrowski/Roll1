package pw.chat.network;

import java.io.IOException;
import java.util.function.Consumer;

public interface Messenger {
    public void RegisterOnMessageReceived(Consumer<String> callback);

    public void RemoveOnMessageReceived(Consumer<String> callback);

    public void SendMessage(String message) throws IOException;
}
