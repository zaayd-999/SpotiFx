package com.SpotiFx.main.Class.User;


import java.util.List;
import java.util.ArrayList;
import com.SpotiFx.main.Class.Player.Song.song;

public class artist extends user {

    private final List<song> uploadedSongs = new ArrayList<>();

    public artist(int id, String username, String email, String password) {
        super(id, username, email, password, userTypes.ARTIST);
    }

    public List<song> getUploadedSongs() {
        return uploadedSongs;
    }

    public void addSong(song s) {
        uploadedSongs.add(s);
    }
}