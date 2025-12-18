package com.structure.Class.User;

import com.structure.Class.Player.PlayList.playList;

import java.util.HashMap;


public class listener extends user {
    private final HashMap<Integer,playList> playlists = new HashMap<>();

    private listener(listenerBuilder builder) {
        super(builder);
        this.type = userTypes.LISTENER;
    }

    private listener(userBuilder builder) {
        super(builder);
        this.type = userTypes.LISTENER;
    }

    public HashMap<Integer,playList> getPlaylists() {
        return playlists;
    }

    public void addPlaylist(playList playlist) {
        playlists.put(playlist.getPlayListId(),playlist);
    }

    public static class listenerBuilder extends  userBuilder {

        public listenerBuilder() {};

        public listenerBuilder(int id , String username) {
            super(id,username);
        }

        public listenerBuilder(String username , int id) {
            this(id,username);
        }

        public listenerBuilder(int id) {
            this(id,null);
        }

        public listenerBuilder(String username) {
            this(-1,username);
        }

        public listenerBuilder id(int id) {
            this.id = id;
            return this;
        }

        public listenerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public listenerBuilder type() {
            this.type = userTypes.LISTENER;
            return this;
        }

        public listenerBuilder type(userTypes type) {
            return listenerBuilder.this.type();
        }

        public listenerBuilder username(String username) {
            this.username = username;
            return this;
        }

        public listenerBuilder lastPlayedsong(int lastPlayedsong) {
            this.lastPlayedsong = lastPlayedsong;
            return this;
        }

        public listenerBuilder lastPlayedPlaylist(int lastPlayerPlaylist) {
            this.lastPlayerPlaylist = lastPlayerPlaylist;
            return this;
        }

        public listener build() {
            if(this.username==null) throw new IllegalStateException("username is required");
            if(this.id == -1) throw new IllegalStateException("Id is required");
            if(this.email == null) throw new IllegalStateException("Email is required");
            this.type = userTypes.LISTENER;
            return new listener(this);
        }
    }

}
