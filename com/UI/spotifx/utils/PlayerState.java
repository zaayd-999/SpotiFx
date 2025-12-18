package com.UI.spotifx.utils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import com.structure.Class.Player.Song.song;
import com.structure.Class.Player.Song.contributionType;
import com.structure.Class.Player.Song.songCredits;

public class PlayerState {

    private static final PlayerState instance = new PlayerState();

    private final ObjectProperty<song> currentsong = new SimpleObjectProperty<>();

    private PlayerState() {}

    public static PlayerState getInstance() {
        return instance;
    }

    public song getCurrentsong() {
        return currentsong.get();
    }

    public void setCurrentsong(song song) {
        currentsong.set(song);
    }

    public ObjectProperty<song> currentsongProperty() {
        return currentsong;
    }
}

