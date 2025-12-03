package com.SpotiFx.main.Class.User;


import java.util.List;
import java.util.ArrayList;
import com.SpotiFx.main.Class.Player.Song.song;

public class artist extends user {

    private final List<song> uploadedSongs = new ArrayList<>();

    private artist (artistBuilder builder) {
        super(builder);
        this.type = userTypes.ARTIST;
    }

    private artist ( userBuilder builder ) {
        super(builder);
        this.type = userTypes.ARTIST;
    }

    public List<song> getUploadedSongs() {
        return uploadedSongs;
    }

    public void addSong(song s) {
        uploadedSongs.add(s);
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

        public artistBuilder password(String password) {
            this.password = password;
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

        public artist build() {
            if(this.username==null) throw new IllegalStateException("username is required");
            if(this.id == -1) throw new IllegalStateException("Id is required");
            return new artist(this);
        }
    }
}