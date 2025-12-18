package com.UI.spotifx;

import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.db.SpotiDB;

import java.util.Objects;

public class Main extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    private static final String CSS_PATH =
            "/com/UI/spotifx/css/styles.css";

    @Override
    public void start(Stage stage) throws Exception {

        primaryStage = stage;

        Parent root = loadFXML("login.fxml");

        scene = new Scene(root, 800, 600);

        scene.getStylesheets().add(
                Objects.requireNonNull(
                        getClass().getResource(CSS_PATH)
                ).toExternalForm()
        );

        stage.setTitle("SpotiFX");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }


    public static void setRoot(String fxml) {
        try {
            Parent root = loadFXML(fxml);

            scene.setRoot(root);

            scene.getStylesheets().removeAll(scene.getStylesheets());
            scene.getStylesheets().add(
                    Objects.requireNonNull(
                            Main.class.getResource(CSS_PATH)
                    ).toExternalForm()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws Exception {
        return FXMLLoader.load(
                Objects.requireNonNull(
                        Main.class.getResource(
                                "/com/UI/spotifx/fxml/" + fxml
                        )
                )
        );
    }

    public static Stage getStage() {

        return primaryStage;
    }

    public static void main(String[] args) {

        launch(args);
    }
}