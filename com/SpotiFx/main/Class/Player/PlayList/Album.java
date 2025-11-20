package com.SpotiFx.main.Class.Player.PlayList;

public class Album extends playList {
    private playlistType type;
    public Album(int id) {
        super(id);
        this.type = playlistType.Album;
    }

    @Override
    public playlistType getPlayListType() {
        return this.type;
    }
}
