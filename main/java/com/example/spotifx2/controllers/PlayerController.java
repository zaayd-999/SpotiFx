package com.example.spotifx2.controllers;

import com.example.spotifx2.utils.MusicPlayerManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

import java.awt.*;
import java.util.Objects;


public class PlayerController {

    @FXML private ImageView AlbumImage;

    @FXML private Label songTitle;
    @FXML private Label songArtist;
    @FXML private Label songAlbum;


    @FXML private ImageView albumImage;

    // change this
    @FXML Label currentTime;
    @FXML Label totalTime;
    @FXML Label volumePercent;

    @FXML private Slider progressSlider;
    @FXML private Slider volumeSlider;

    @FXML Button shufButton;
    @FXML Button prevButton;
    @FXML Button playButton;
    @FXML Button nextButton;
    @FXML Button repButton;

    @FXML private TextField searchField;
    /*@FXML private ListView<String> playlistView;*/

    @FXML private Label nowPlayingLabel;
    @FXML private Label titleLabel;
    @FXML private Label artistLabel;

    private final MusicPlayerManager player = new MusicPlayerManager();

    /*private final ObservableList<String> songs = FXCollections.observableArrayList(
            "Dreams - Aurora",
            "Midnight Ride - NightCity",
            "Eclipse - SynthWaves"
    );*/

    @FXML
    public void initialize() {

        albumImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/example/spotifx2/assets/images/album.jpg")).toString()));

        volumeSlider.valueProperty().addListener((obs, oldV, newV) -> {
            volumePercent.setText((int)(newV.doubleValue()) + "%");
        });
    }

    @FXML
    private void onPlayClicked() {
        player.play();
        nowPlayingLabel.setText("Now Playing");
        titleLabel.setText("Dreams");
        artistLabel.setText("Aurora");
    }

    @FXML
    private void onPauseClicked() {
        player.pause();
    }

    @FXML
    private void onPrevClicked() {
        System.out.println("Previous song");
    }

    @FXML
    private void onNextClicked() {
        player.next();
    }

    @FXML
    private void onSearchClicked() {
        String keyword = searchField.getText().toLowerCase();
        /*playlistView.setItems(songs.filtered(s -> s.toLowerCase().contains(keyword)));*/
    }

    public void onShuffleClicked(ActionEvent actionEvent) {
    }

    public void onRepeatClicked(ActionEvent actionEvent) {
    }
}
