package pw.chat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import pw.chat.network.ChatClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerSessionScreenController implements Initializable {
    private ChatClient chatClient;

    public TextField globalChatInput;
    public Button globalChatSubmit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            chatClient = new ChatClient("127.0.0.1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        globalChatInput.textProperty().addListener((observable, oldText, newText) -> {
            globalChatSubmit.setDisable(newText == null || newText.isBlank());
        });
    }

    public void handleGlobalMessageSubmit(ActionEvent actionEvent) {
        try {
            chatClient.SendMessage(globalChatInput.getText());
            globalChatInput.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
