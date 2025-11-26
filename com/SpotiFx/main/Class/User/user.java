package com.SpotiFx.main.Class.User;

public class user {
    protected int id;
    protected String username;
    protected String email;
    protected String password;
    protected userTypes type;

    public user(int id, String username, String email, String password, userTypes type) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.type = type;
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

}
