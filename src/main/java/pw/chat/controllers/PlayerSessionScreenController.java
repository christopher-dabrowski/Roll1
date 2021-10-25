package pw.chat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import pw.chat.network.ChatClient;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class PlayerSessionScreenController implements Initializable {
    private ChatClient chatClient;
    private String playerName;

    public TextArea globalChatHistory;
    public TextField globalChatInput;
    public Button globalChatSubmit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var random = new Random();
        playerName = "Player %d".formatted(random.nextInt(10));

        try {
            chatClient = new ChatClient("127.0.0.1");
            chatClient.RegisterOnMessageReceived(m -> globalChatHistory.appendText(m + "\n"));
            new Thread(chatClient).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        globalChatInput.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                handleGlobalMessageSubmit(null);
            }
        });

        globalChatInput.textProperty().addListener((observable, oldText, newText) -> {
            globalChatSubmit.setDisable(newText == null || newText.isBlank());
        });
    }

    public void handleGlobalMessageSubmit(ActionEvent actionEvent) {
        try {
            chatClient.SendMessage(playerName + ": " + globalChatInput.getText());
            globalChatInput.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
