package com.SpotiFx.main.Class.User;

public class user {
    protected int id;
    protected String username;
    protected String email;
    protected String password;
    protected userTypes type;

    protected user(userBuilder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.type = builder.type;
        this.password = builder.password;
        this.username = builder.username;
    }


    public userBuilder toBuilder() {
        return new userBuilder(this.id,this.username).type(this.type).email(this.email).password(this.password);
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public userTypes getType() { return type; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public boolean isArtist() {
        return this.type == userTypes.ARTIST;
    }


    public static class userBuilder {
        protected String username;
        protected int id=-1;
        protected String email;
        protected String password;
        protected userTypes type;

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

        public userBuilder password(String password) {
            this.password = password;
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

        public user build() {
            if(this.username==null) throw new IllegalStateException("username is required");
            if(this.id == -1) throw new IllegalStateException("Id is required");
            return new user(this);
        }
    }
}
