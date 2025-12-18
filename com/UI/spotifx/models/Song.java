package com.UI.spotifx.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {

    private final StringProperty title;
    private final StringProperty artist;
    private final StringProperty album;
    private final StringProperty duration;

    public Song(String title, String artist, String album, String duration) {
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty(artist);
        this.album = new SimpleStringProperty(album);
        this.duration = new SimpleStringProperty(duration);
    }

    /* PROPERTIES */
    public StringProperty titleProperty() { return title; }
    public StringProperty artistProperty() { return artist; }
    public StringProperty albumProperty() { return album; }
    public StringProperty durationProperty() { return duration; }

    /* GETTERS */
    public String getTitle() { return title.get(); }
    public String getArtist() { return artist.get(); }
    public String getAlbum() { return album.get(); }
    public String getDuration() { return duration.get(); }

    /* HELPERS */
    public int getDurationSeconds() {
        String[] parts = getDuration().split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }
}








//package com.example.spotifx2.models;
//
//
//public class song {
//    private String title;
//    private String artist;
//    private String album;
//    private String duration;
//    private String filePath;
//
//    public song(String title, String artist, String album, String duration) {
//        this.title = title;
//        this.artist = artist;
//        this.album = album;
//        this.duration = duration;
//    }
//
//    // Getters and setters
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//
//    public String getArtist() { return artist; }
//    public void setArtist(String artist) { this.artist = artist; }
//
//    public String getAlbum() { return album; }
//    public void setAlbum(String album) { this.album = album; }
//
//    public String getDuration() { return duration; }
//    public void setDuration(String duration) { this.duration = duration; }
//
//    public String getFilePath() { return filePath; }
//    public void setFilePath(String filePath) { this.filePath = filePath; }
//
//    @Override
//    public String toString() {
//        return title + " - " + artist;
//    }
//}
