package pw.chat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
        globalChatInput.textProperty().addListener((observable, oldText, newText) -> {
            globalChatSubmit.setDisable(newText == null || newText.isBlank());
        });

        try {
            chatServer = new ChatServer();
            chatServer.RegisterOnMessageReceived((message) -> {
                globalChatHistory.appendText(message + "\n");
            });

            new Thread(chatServer).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        globalChatInput.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                handleGlobalChatMessageSubmit(null);
            }
        });
    }

    public void handleGlobalChatMessageSubmit(ActionEvent actionEvent) {
        chatServer.SendMessage("GM: " + globalChatInput.getText());
        globalChatInput.clear();
    }
}
