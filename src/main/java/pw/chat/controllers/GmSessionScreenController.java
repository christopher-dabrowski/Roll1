package pw.chat.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pw.chat.network.ChatServer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GmSessionScreenController implements Initializable {
    private ChatServer chatServer;

    public TextArea globalChatHistory;
    public TextField globalChatInput;
    public Button globalChatSubmit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            chatServer = new ChatServer();
            chatServer.RegisterOnMessageReceived((message) -> {
                globalChatHistory.appendText(message + "\n");
            });

            new Thread(chatServer).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
