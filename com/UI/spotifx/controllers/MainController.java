package com.UI.spotifx.controllers;

import com.UI.spotifx.utils.PlayerState;
import com.db.SpotiDB;
import com.structure.Class.Player.Controllers.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;


import java.io.IOException;
import java.util.Objects;

public class MainController {
    @FXML private Label sidebarsongTitle;
    @FXML private Label sidebarsongArtist;
    private final controller player = new controller();
    private final SpotiDB database = new SpotiDB();

    /* ===== SINGLETON ===== */
    private static MainController instance;

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        return instance;
    }



    /* ===== FXML ===== */
    @FXML private ImageView playerIcon;
    @FXML private StackPane contentArea;
    @FXML private Button playerBtn;
    @FXML private Button playlistBtn;
    @FXML private Button settingsBtn;


    /* ==== VIEW LOADER ===== */
    private void loadview(String fxmlFile) {
        try {
            System.out.println("Loading: " + fxmlFile);
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/UI/spotifx/fxml/" + fxmlFile)));
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


    /* ===== INIT ===== */
    @FXML
    private void initialize() {

        playerIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/UI/spotifx/assets/icons/player_icon.png")).toString()));
        if (playerBtn == null || contentArea == null) {
            System.err.println("FXML injection failed! Check your main.fxml file.");
            return;
        }
        System.out.println("MainController initialized successfully");
        PlayerState.getInstance().currentsongProperty().addListener((obs, old, song) -> {
            if (song != null) {
                sidebarsongTitle.setText(song.getTitle());
                sidebarsongArtist.setText(song.getArtist().getUsername());
            } else {
                sidebarsongTitle.setText("—");
                sidebarsongArtist.setText("");
            }
        });
        showPlayer();
    }


    /* ===== NAVIGATION ====== */
    @FXML
    public void showPlayer() {
        loadview("player.fxml");
        setActiveButton(playerBtn);
    }


    @FXML
    public void showPlaylist() {
        loadview("playlist.fxml");
        setActiveButton(playlistBtn);
    }


    @FXML
    public void showSettings() {
        loadview("settings.fxml");
        setActiveButton(settingsBtn);
    }



    /* ==== ACTIVE BUTTON ===== */
    private void setActiveButton(Button activeButton) {
        //reset
        if (playerBtn != null) playerBtn.getStyleClass().remove("active");
        if (playlistBtn != null) playlistBtn.getStyleClass().remove("active");
        if (settingsBtn != null) settingsBtn.getStyleClass().remove("active");

        if (activeButton != null) {
            activeButton.getStyleClass().add("active");
        }
    }

    protected controller getAppPlayer() {
        return this.player;
    }

    protected SpotiDB getAppConnection() {
        if(this.database.getConnection() == null) {
            this.database.connect(error -> {
                if(error != null) {
                    System.out.println("Error: Failed to establish connection to the MySQL database server\n" + error.getMessage());
                    System.exit(2); // Exit (DB error)
                } else {
                    System.out.println("Success: Connected to the MySQL database server");
                }
            });
        }
        return this.database;
    }
}


