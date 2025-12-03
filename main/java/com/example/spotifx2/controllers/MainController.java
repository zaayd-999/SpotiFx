package com.example.spotifx2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import java.io.IOException;

public class MainController {
    @FXML private StackPane contentArea;
    @FXML private Button playerBtn;
    @FXML private Button playlistBtn;
    @FXML private Button settingsBtn;


    private void loadview(String fxmlFile) {
        try {
            System.out.println("Loading: " + fxmlFile);
            Parent view = FXMLLoader.load(getClass().getResource("/com/example/spotifx2/fxml/" + fxmlFile));
            contentArea.getChildren().setAll(view);
            System.out.println("Successfully loaded: " + fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load: " + fxmlFile);
            createFallbackView(fxmlFile);
        }
    }

    private void createFallbackView(String fxmlFile) {
        // when FXML fails to load
        javafx.scene.control.Label errorLabel = new javafx.scene.control.Label(
                "Could not load: " + fxmlFile + "\nCheck console for errors."
        );
        errorLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-alignment: center;");
        contentArea.getChildren().setAll(errorLabel);
    }



    @FXML
    private void initialize() {
        if (playerBtn == null || contentArea == null) {
            System.err.println("FXML injection failed! Check your main.fxml file.");
            return;
        }
        System.out.println("MainController initialized successfully");
        loadview("player.fxml");
        setActiveButton(playerBtn);
    }

    @FXML
    private void showPlayer() {
        loadview("player.fxml");
        setActiveButton(playerBtn);
    }


    @FXML
    private void showPlaylist() {
        loadview("playlist.fxml");
        setActiveButton(playlistBtn);
    }

    @FXML
    private void showSettings() {
        loadview("settings.fxml");
        setActiveButton(settingsBtn);
    }

    private void setActiveButton(Button activeButton) {
        //reset
        if (playerBtn != null) playerBtn.getStyleClass().remove("active");
        if (playlistBtn != null) playlistBtn.getStyleClass().remove("active");
        if (settingsBtn != null) settingsBtn.getStyleClass().remove("active");

        if (activeButton != null) {
            activeButton.getStyleClass().add("active");
        }
    }
}


