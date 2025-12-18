package com.structure.Class.Player.PlayList;

import com.structure.Class.User.user;

public class Album extends playList {
    private playlistType type;
    public Album(int id , String title , String description , user creator) {
        super(id , title , description , playlistType.Album , creator);
    }
}
