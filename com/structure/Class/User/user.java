package com.structure.Class.User;

public class user {
    protected int id;
    protected String username;
    protected String email;
    protected userTypes type;
    protected int lastPlayedsong = -1;
    protected int lastPlayerPlaylist = -1;

    protected user(userBuilder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.type = builder.type;
        this.username = builder.username;
        this.lastPlayedsong = builder.lastPlayedsong;
        this.lastPlayerPlaylist = builder.lastPlayerPlaylist;
    }


    public void setLastPlayedsong(int lastPlayedsong) {
        this.lastPlayedsong = lastPlayedsong;
    }

    public int getLastPlayedsong() {
        return this.lastPlayedsong;
    }

    public void setLastPlayerPlaylist(int lastPlayerPlaylist) {
        this.lastPlayerPlaylist = lastPlayerPlaylist;
    }

    public int getLastPlayerPlaylist() {
        return this.lastPlayerPlaylist;
    }

    public userBuilder toBuilder() {
        return new userBuilder(this.id,this.username).type(this.type).email(this.email);
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public userTypes getType() { return type; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }

    public boolean isArtist() {
        return this.type == userTypes.ARTIST;
    }


    public static class userBuilder {
        protected String username;
        protected int id=-1;
        protected String email;
        protected userTypes type;
        protected int lastPlayedsong = -1;
        protected int lastPlayerPlaylist = -1;

        public userBuilder() {};

        public userBuilder(int id , String username) {
            this.id = id;
            this.username = username;
        }

        public userBuilder(String username , int id) {
            this(id,username);
        }

        public userBuilder(int id) {
            this(id,null);
        }

        public userBuilder(String username) {
            this(-1,username);
        }

        public userBuilder id(int id) {
            this.id = id;
            return this;
        }

        public userBuilder email(String email) {
            this.email = email;
            return this;
        }

        public userBuilder type(userTypes type) {
            this.type = type;
            return this;
        }

        public userBuilder username(String username) {
            this.username = username;
            return this;
        }

        public userBuilder lastPlayedsong(int lastPlayedsong) {
            this.lastPlayedsong = lastPlayedsong;
            return this;
        }

        public userBuilder lastPlayedPlaylist(int lastPlayerPlaylist) {
            this.lastPlayerPlaylist = lastPlayerPlaylist;
            return this;
        }


        public user build() {
            if(this.username==null) throw new IllegalStateException("username is required");
            if(this.id == -1) throw new IllegalStateException("Id is required");
            if(this.email == null) throw new IllegalStateException("Email is required");
            return new user(this);
        }
    }
}
