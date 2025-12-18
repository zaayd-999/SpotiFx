package com.UI.spotifx.controllers;


import com.structure.Class.Player.Song.song;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

//import com.UI.spotifx.utils.MusicPlayerManager;
import com.db.SpotiDB;
import com.structure.Class.Player.Controllers.controller;
import com.UI.spotifx.controllers.MainController;
import com.UI.spotifx.utils.PlayerState;
import com.structure.Class.Player.PlayList.playList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;

import com.db.Classes.songs;
import com.db.Classes.users;
import com.db.Classes.playlists;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;


public class PlayerController {

    private Timeline simpleProgressTimeline;
    private song currentS;
    @FXML private SVGPath playPauseIcon;
    @FXML private Line muteLine2;
    @FXML private Line muteLine1;

    private static final String PLAY_ICON =
            "M5 5a2 2 0 0 1 3.008-1.728l11.997 6.998a2 2 0 0 1 .003 3.458l-12 7A2 2 0 0 1 5 19z";

    private static final String PAUSE_ICON =
            "M6 5h4v14H6z M14 5h4v14h-4z";


    /* ==== song INFO ===== */
    @FXML private Label songTitle;
    @FXML private Label songArtist;
    @FXML private Label songAlbum;
    @FXML private ImageView albumImage;

    /* ==== TIME ===== */
    @FXML Label currentTime;
    @FXML Label totalTime;

    private int currentSong = 0;

    /* ===== SLIDERS ===== */
    @FXML private Slider progressSlider;
    @FXML private Slider volumeSlider;
    @FXML private SVGPath volumeIcon;
    @FXML Label volumePercent;


    /* ==== CONTROLS ==== */
    @FXML Button shufButton;
    @FXML Button prevButton;
    @FXML Button playButton;
    @FXML Button nextButton;
    @FXML Button repButton;

    private boolean shuffleOn = false;
    private boolean repeatOn = false;

    controller player = MainController.getInstance().getAppPlayer();
    SpotiDB database = MainController.getInstance().getAppConnection();



    /* ==== SEARCH ==== */
    @FXML private TextField searchField;

    @FXML private Label nowPlayingLabel;
    @FXML private Label titleLabel;
    @FXML private Label artistLabel;
    @FXML private Label albumLabel;




    /* ==== STATE ===== */
    private int totalsongSeconds = 0;
    private boolean isPlaying = false;
    private boolean isPaused = false;

    @FXML
    public void initialize() {

        PlayerState.getInstance().currentsongProperty().addListener((obs, old, song) -> {
            if (song != null) {
                Platform.runLater(() -> loadsong(song));
            }
        });

        /* --- If song already exists (opened from playlist) --- */
        song current = PlayerState.getInstance().getCurrentsong();
        if (current != null) {
            Platform.runLater(() -> loadsong(current));
        }

        /* ---- PROGRESS SLIDER ---- */
        /*progressSlider.valueProperty().addListener((obs, o, n) -> {
            if (totalsongSeconds == 0) return;

            double ratio = n.doubleValue() / progressSlider.getMax();
            updateSliderFill(progressSlider, ratio);

            int currentSec = (int) (ratio * totalsongSeconds);
            currentTime.setText(formatTime(currentSec));
        });*/

        progressSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (totalsongSeconds == 0) return;

            double ratio = newValue.doubleValue() / progressSlider.getMax();
            updateSliderFill(progressSlider, ratio);

            int currentSec = (int) (ratio * totalsongSeconds);
            currentTime.setText(formatTime(currentSec));
        });
        startSimpleProgress();


        /* --- VOLUME SLIDER */

        volumeSlider.valueProperty().addListener((obs, o, n) -> {
            updateSliderFill(volumeSlider, 1);
            volumePercent.setText( 100 + "%");
            updateVolumeIcon(n.doubleValue());
        });


        Platform.runLater(() -> {
            updateSliderFill(volumeSlider, volumeSlider.getValue() / 100.0);
            updateVolumeIcon(volumeSlider.getValue());
        });

        albumImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/UI/spotifx/assets/images/album.jpg")).toString()));

        playPauseIcon.setContent(PLAY_ICON);
    }

    private void startSimpleProgress() {
        simpleProgressTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> incrementSlider())
        );
        simpleProgressTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void incrementSlider() {
        if (isPlaying && totalsongSeconds > 0) {
            double currentValue = progressSlider.getValue();
            double maxValue = progressSlider.getMax();

            // Calculate how much to increment each second
            double increment = maxValue / totalsongSeconds;

            if (currentValue + increment <= maxValue) {
                progressSlider.setValue(currentValue + increment);
            } else {
                progressSlider.setValue(maxValue);
                // Song finished
                if(repeatOn) {
                    progressSlider.setValue(0);
                    player.stop();
                    player.load("songs/music"+currentS.getsongId()+".wav");
                    player.play();
                } else {
                    onNextClicked();
                }
            }
        }
    }

    /* ===== LOAD song ===== */
    private void loadsong(song song) {
        currentS = song;
        if (songTitle != null) songTitle.setText(song.getTitle());
        if (songArtist != null) songArtist.setText(song.getArtist().getUsername());

        if (songArtist != null) songArtist.setText(song.getArtist().getUsername());
        if (songAlbum != null) songAlbum.setText("Album#1");

        if (titleLabel != null) titleLabel.setText(song.getTitle());
        if (artistLabel != null) artistLabel.setText(song.getArtist().getUsername());
        if (albumLabel != null) albumLabel.setText("Album#1");

        totalsongSeconds = song.getDuration();
        totalTime.setText(formatTime(totalsongSeconds));
        currentTime.setText("0:00");

        progressSlider.setValue(0);
        volumeSlider.setValue(100);

        isPlaying = false;
        playPauseIcon.setContent(PLAY_ICON);
        player.stop();
        player.load("songs/music"+song.getsongId()+".wav");

        }

    /* ===== SLIDER FILL ===== */
    private void updateSliderFill(Slider slider, double percent) {
        Node track = slider.lookup(".track");
        if (track == null) return;

        double p = percent * 100;

        track.setStyle(String.format(
                "-fx-background-color: linear-gradient(to right, white %.2f%%, #444 %.2f%%);",
                p, p
        ));
    }

    /* ===== TIME FORMAT ===== */
    private String formatTime(int sec) {
        return (sec / 60) + ":" + String.format("%02d", sec % 60);
    }

    /* ===== VOLUME ICON ===== */
    private void updateVolumeIcon(double v) {
        if (v == 0) {
            muteLine1.setVisible(true);
            muteLine2.setVisible(true);
            volumeIcon.setContent("M3 9h4l5-4v14l-5-4H3z");
        } else if (v < 40) {
            muteLine1.setVisible(false);
            muteLine2.setVisible(false);
            volumeIcon.setContent("M3 9h4l5-4v14l-5-4H3z M14 10a3 3 0 0 1 0 4");
        } else {
            muteLine1.setVisible(false);
            muteLine2.setVisible(false);
            volumeIcon.setContent("M3 9h4l5-4v14l-5-4H3z M14 8a5 5 0 0 1 0 8 M16 6a7 7 0 0 1 0 12");
        }
    }





    // change the control methods here!!!


    @FXML
    private void onPrevClicked() {
        List<song> songsList = new ArrayList<>(database.getAppsongs().values());
        currentSong = ((currentSong - 1) % songsList.size() + songsList.size()) % songsList.size();
        loadsong(songsList.get(currentSong));
    }

    @FXML
    private void onPlayPauseClicked() {

        if (isPlaying) {
            player.pause();
            isPaused = true;
            isPlaying = false;
            playPauseIcon.setContent(PLAY_ICON);
            simpleProgressTimeline.pause();
        } else if (!isPlaying && !isPaused) {
            player.play();
            playPauseIcon.setContent(PAUSE_ICON);
            isPlaying = true;
            isPaused = false;
            simpleProgressTimeline.play();
        } else if (isPaused) {
            player.resume();
            playPauseIcon.setContent(PAUSE_ICON);
            isPlaying = true;
            isPaused = false;
            simpleProgressTimeline.play();
        }
    }

    @FXML
    private void onNextClicked() {
        //controller.next();
        List<song> songsList = new ArrayList<>(database.getAppsongs().values());
        currentSong=(currentSong+1)%songsList.size();
        System.out.println(songsList.size());
        System.out.println(currentSong);
        loadsong(songsList.get(currentSong));
        System.out.println("Next song");

    }




    @FXML
    private void onShuffleClicked() {

        shuffleOn = !shuffleOn;
        toggleButton(shufButton, shuffleOn);
    }

    @FXML
    private void onRepeatClicked() {
        repeatOn = !repeatOn;
        toggleButton(repButton, repeatOn);
    }

    private void toggleButton(Button btn, boolean active) {

        if (active) {
            btn.getStyleClass().add("active");
        } else {
            btn.getStyleClass().remove("active");
        }
    }
}
