package pw.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Roll1Application extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Roll 1");

        var loader = new FXMLLoader(Roll1Application.class.getResource("main-screen.fxml"));
        var scene = new Scene(loader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}