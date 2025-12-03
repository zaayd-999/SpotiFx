package com.example.spotifx2.controllers;



import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import com.example.spotifx2.models.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlaylistController {
    /*
    @FXML private ListView<Song> songsListView;
    @FXML private TextField searchField;

    private ObservableList<Song> songs;

    @FXML
    private void initialize() {
        // Sample data
        songs = FXCollections.observableArrayList(
                new Song("Midnight Dreams", "Aurora Sky", "Ethereal Nights", "4:05"),
                new Song("Ocean Waves", "Marine Blue", "Deep Sea", "3:45"),
                new Song("Mountain High", "Summit Peak", "Altitude", "5:20"),
                new Song("City Lights", "Urban Glow", "Metropolis", "3:30"),
                new Song("Desert Wind", "Sandy Dunes", "Oasis", "4:15")
        );

        songsListView.setItems(songs);
        //songsListView.setCellFactory(lv -> new SongListCell());

        // Search functionality
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filterSongs(newVal);
        });
    }

    private void filterSongs(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            songsListView.setItems(songs);
        } else {
            ObservableList<Song> filtered = FXCollections.observableArrayList();
            for (Song song : songs) {
                if (song.getTitle().toLowerCase().contains(searchText.toLowerCase()) ||
                        song.getArtist().toLowerCase().contains(searchText.toLowerCase())) {
                    filtered.add(song);
                }
            }
            songsListView.setItems(filtered);
        }
    }

    @FXML
    private void playSelectedSong() {
        Song selected = songsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Logic to play selected song
            System.out.println("Playing: " + selected.getTitle());
        }
    }

     */
}
