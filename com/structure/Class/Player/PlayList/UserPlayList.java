package com.structure.Class.Player.PlayList;
import com.structure.Class.User.user;
public class UserPlayList extends playList {
    private playlistType type;
    public UserPlayList(int id, String title, String description, user creator) {
        super(id, title, description, playlistType.UserPlayList,creator);
    }
}
