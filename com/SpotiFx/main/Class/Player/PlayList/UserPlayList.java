package com.SpotiFx.main.Class.Player.PlayList;

public class UserPlayList extends playList {
    private playlistType type;
    public UserPlayList(int id) {
        super(id);
        this.type = playlistType.UserPlayList;
    }

    @Override
    public playlistType getPlayListType() {
        return this.type;
    }
}
