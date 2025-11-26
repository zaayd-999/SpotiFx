package com.SpotiFx.main.Class.User;

import com.SpotiFx.main.Class.Player.PlayList.playList;
import java.util.List;
import java.util.ArrayList;



public class listener extends user {
    private final List<playList> playlists = new ArrayList<>();

    public listener(int id, String username, String email, String password) {
        super(id, username, email, password, userTypes.LISTENER);
    }

    public List<playList> getPlaylists() {
        return playlists;
    }

    public void addPlaylist(playList playlist) {
        playlists.add(playlist);
    }

}
