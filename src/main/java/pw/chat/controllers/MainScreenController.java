package pw.chat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import pw.chat.Roll1Application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController {
    @FXML
    public AnchorPane rootPane;

    private Stage getStage() {
        return (Stage) rootPane.getScene().getWindow();
    }

    public void startDmSession(ActionEvent actionEvent) throws IOException {
        var loader = new FXMLLoader(Roll1Application.class.getResource("gm-session-screen.fxml"));
        var scene = new Scene(loader.load(), getStage().getWidth(), getStage().getHeight());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        getStage().setScene(scene);
    }

    public void startPlayerSession(ActionEvent actionEvent) throws IOException {
        var loader = new FXMLLoader(Roll1Application.class.getResource("player-session-screen.fxml"));
        var scene = new Scene(loader.load(), getStage().getWidth(), getStage().getHeight());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        getStage().setScene(scene);
    }
}
