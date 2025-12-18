package com.structure.Class.User;


import java.util.List;
import java.util.ArrayList;
import com.structure.Class.Player.Song.song;

public class artist extends user {

    private final List<song> uploadedsongs = new ArrayList<>();

    private artist (artistBuilder builder) {
        super(builder);
        this.type = userTypes.ARTIST;
    }

    private artist ( userBuilder builder ) {
        super(builder);
        this.type = userTypes.ARTIST;
    }

    public List<song> getUploadedsongs() {
        return uploadedsongs;
    }

    public void addsong(song s) {
        uploadedsongs.add(s);
    }

    public static class artistBuilder extends  userBuilder {

        public artistBuilder() {};

        public artistBuilder(int id , String username) {
            super(id,username);
        }

        public artistBuilder(String username , int id) {
            this(id,username);
        }

        public artistBuilder(int id) {
            this(id,null);
        }

        public artistBuilder(String username) {
            this(-1,username);
        }

        public artistBuilder id(int id) {
            this.id = id;
            return this;
        }

        public artistBuilder email(String email) {
            this.email = email;
            return this;
        }

        public artistBuilder type() {
            this.type = userTypes.ARTIST;
            return this;
        }

        public artistBuilder type(userTypes type) {
            return artistBuilder.this.type();
        }

        public artistBuilder username(String username) {
            this.username = username;
            return this;
        }

        public artistBuilder lastPlayedsong(int lastPlayedsong) {
            this.lastPlayedsong = lastPlayedsong;
            return this;
        }

        public artistBuilder lastPlayedPlaylist(int lastPlayerPlaylist) {
            this.lastPlayerPlaylist = lastPlayerPlaylist;
            return this;
        }

        public artist build() {
            if(this.username==null) throw new IllegalStateException("username is required");
            if(this.id == -1) throw new IllegalStateException("Id is required");
            if(this.email == null) throw new IllegalStateException("Email is required");
            this.type = userTypes.ARTIST;
            return new artist(this);
        }
    }
}