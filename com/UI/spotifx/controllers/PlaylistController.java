package com.UI.spotifx.controllers;

import com.db.SpotiDB;
import com.structure.Class.Player.Song.song;// import the song class here

import com.UI.spotifx.controllers.MainController;
import com.db.Classes.songs;
import com.structure.Main;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import com.UI.spotifx.utils.PlayerState;
import com.UI.spotifx.controllers.MainController;
import javafx.scene.shape.SVGPath;


import java.time.LocalDate;

public class PlaylistController {

    /* ===== HEADER ===== */
    @FXML private Label songCountLabel;
    @FXML private Label playlistDurationLabel;
    @FXML private Label releaseDateLabel;
    @FXML private TextField searchField;

    /* ===== TABLE ===== */
    @FXML private TableView<song> table;
    @FXML private TableColumn<song, Number> colIndex;
    @FXML private TableColumn<song, String> colTitle;
    @FXML private TableColumn<song, String> colAlbum;
    @FXML private TableColumn<song, String> colDuration;
    @FXML private TableColumn<song, Void> colActions;

    /* ===== DATA ===== */
    private final ObservableList<song> songs = FXCollections.observableArrayList();
    private FilteredList<song> filteredsongs;

    /* ===== INIT ===== */
    @FXML
    public void initialize() {
        SpotiDB connection = MainController.getInstance().getAppConnection();
        connection.getAppUsers();
        songs AppSongs = connection.getAppsongs();
        /* --- change this to real playlist!!! */
        songs.addAll(AppSongs.values());

        filteredsongs = new FilteredList<>(songs, p -> true);
        table.setItems(filteredsongs);
        // double click to play
        table.setRowFactory(tv -> {
            TableRow<song> row = new TableRow<>();

            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    playsong(row.getItem());
                }
            });

            return row;
        });


        setupColumns();
        setupSearch();
        updateStats();

        releaseDateLabel.setText("Released · " + LocalDate.now().getYear());
    }

    /* ===== COLUMNS ===== */
    private void setupColumns() {

        /* # INDEX / PLAY */
        colIndex.setCellFactory(col -> new TableCell<>() {

            private final SVGPath playIcon = new SVGPath();
            private final Button playBtn = new Button();

            {
                playIcon.setContent("M5 5a2 2 0 0 1 3.008-1.728l11.997 6.998a2 2 0 0 1 .003 3.458l-12 7A2 2 0 0 1 5 19z");
                playBtn.getStyleClass().add("svg-icon");

                playBtn.setGraphic(playIcon);
                playBtn.getStyleClass().add("play-hover-btn");


                playBtn.setOnAction(e -> playsong(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                    return;
                }

                Label indexLabel = new Label(String.valueOf(getIndex() + 1));
                indexLabel.getStyleClass().add("index-label");

                setGraphic(indexLabel);

                setOnMouseEntered(e -> setGraphic(playBtn));
                setOnMouseExited(e -> setGraphic(indexLabel));
            }
        });

        /* TITLE + ARTIST */
        colTitle.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));
        colTitle.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String title, boolean empty) {
                super.updateItem(title, empty);

                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                    return;
                }

                song song = getTableRow().getItem();

                Label titleLabel = new Label(song.getTitle());
                titleLabel.getStyleClass().add("title-text");

                Label artistLabel = new Label(song.getArtist().getUsername());
                artistLabel.getStyleClass().add("artist-text");

                VBox box = new VBox(titleLabel, artistLabel);
                box.setSpacing(2);

                setGraphic(box);
            }
        });

        /* ALBUM */
        colAlbum.setCellValueFactory(cell -> new SimpleStringProperty("Album 1"));

        /* DURATION */
        colDuration.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFormattedDuration()));

        /* ACTIONS (DELETE) */
        colActions.setCellFactory(col -> new TableCell<>() {

            private final SVGPath trashIcon = new SVGPath();
            private final Button deleteBtn = new Button();

            {
                trashIcon.setContent(
                        "M3 6h18 M8 6v12 M16 6v12 M5 6l1 14h12l1-14 M9 6V4h6v2"
                );

                trashIcon.getStyleClass().add("svg-icon");

                deleteBtn.setGraphic(trashIcon);
                deleteBtn.getStyleClass().add("delete-btn");


                deleteBtn.setOnAction(e -> {
                    song song = getTableRow().getItem();
                    if (song != null) {
                        table.getItems().remove(song);
                        updateStats();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteBtn);
            }
        });
    }

    /* ===== SEARCH ===== */
    private void setupSearch() {
        searchField.textProperty().addListener((obs, oldVal, q) -> {
            String query = q.toLowerCase();

            filteredsongs.setPredicate(song ->
                    song.getTitle().toLowerCase().contains(query) ||
                            song.getArtist().getUsername().toLowerCase().contains(query) ||
                            "Playlist#1".contains(query)
            );

            updateStats();
        });
    }

    /* ===== STATS ===== */
    private void updateStats() {
        songCountLabel.setText(filteredsongs.size() + " songs");

        int totalSeconds = filteredsongs.stream()
                .mapToInt(song::getDuration)
                .sum();

        playlistDurationLabel.setText(formatTime(totalSeconds) + " total");
    }

    private String formatTime(int sec) {
        return (sec / 60) + ":" + String.format("%02d", sec % 60);
    }

    /* ===== PLAY song ===== */
    private void playsong(song song) {
        System.out.println("Playing: " + song.getTitle());
        PlayerState.getInstance().setCurrentsong(song);
        MainController.getInstance().showPlayer();
    }
}
