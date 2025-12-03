package com.example.spotifx2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/spotifx2/fxml/main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 500);
        //scene.getStylesheets().add(getClass().getResource("/com/example/spotifx2/css/styles.css").toExternalForm());
        String css = getClass().getResource("/com/example/spotifx2/css/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("SpotiFx");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
