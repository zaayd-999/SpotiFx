package com.example.spotifx2.utils;

import javafx.beans.property.*;
import javafx.util.Duration;

public class MusicPlayerManager {
    private final BooleanProperty isPlaying = new SimpleBooleanProperty(false);
    private final DoubleProperty currentTime = new SimpleDoubleProperty(0);
    private final DoubleProperty volume = new SimpleDoubleProperty(0.75); // 75% default
    private final StringProperty currentSong = new SimpleStringProperty("No song selected");

    // Sample playlist
    private final String[] playlist = {
            "Midnight Dreams - Aurora Sky",
            "Ocean Waves - Marine Blue",
            "Mountain High - Summit Peak",
            "City Lights - Urban Glow",
            "Desert Wind - Sandy Dunes"
    };

    private int currentSongIndex = 0;

    public MusicPlayerManager() {
        currentSong.set(playlist[currentSongIndex]);
    }

    public void play() {
        isPlaying.set(true);
        System.out.println("Playing: " + getCurrentSong());
    }

    public void pause() {
        isPlaying.set(false);
        System.out.println("Paused: " + getCurrentSong());
    }

    public void next() {
        currentSongIndex = (currentSongIndex + 1) % playlist.length;
        currentSong.set(playlist[currentSongIndex]);
        currentTime.set(0);
        if (isPlaying.get()) {
            System.out.println("Now playing: " + getCurrentSong());
        }
    }

    public void previous() {
        currentSongIndex = (currentSongIndex - 1 + playlist.length) % playlist.length;
        currentSong.set(playlist[currentSongIndex]);
        currentTime.set(0);
        if (isPlaying.get()) {
            System.out.println("Now playing: " + getCurrentSong());
        }
    }

    public void seek(double time) {
        currentTime.set(time);
    }

    public void setVolume(double volume) {
        this.volume.set(Math.max(0, Math.min(1, volume))); // between 0-1
        System.out.println("Volume set to: " + (int)(this.volume.get() * 100) + "%");
    }

    // Property getters for data binding
    public BooleanProperty isPlayingProperty() {
        return isPlaying;
    }

    public DoubleProperty currentTimeProperty() {
        return currentTime;
    }

    public DoubleProperty volumeProperty() {
        return volume;
    }

    public StringProperty currentSongProperty() {
        return currentSong;
    }

    // Regular getters
    public boolean isPlaying() {
        return isPlaying.get();
    }

    public double getCurrentTime() {
        return currentTime.get();
    }

    public double getVolume() {
        return volume.get();
    }

    public String getCurrentSong() {
        return currentSong.get();
    }

    public String getCurrentSongTitle() {
        return playlist[currentSongIndex].split(" - ")[0];
    }

    public String getCurrentArtist() {
        return playlist[currentSongIndex].split(" - ")[1];
    }
}