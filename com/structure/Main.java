package com.structure;

// Database Class (extended from ConnectionDB)
import com.db.SpotiDB;
import com.db.Classes.users;
import com.db.Classes.songs;
import com.db.Classes.playlists;

// User Class


public class Main {
    public static void main(String[] args) {

        SpotiDB database = new SpotiDB();
        database.connect(error -> {
            if(error != null) {
                System.out.println("Error: Failed to establish connection to the MySQL database server\n" + error.getMessage());
                System.exit(2); // Exit (DB error)
            } else {
                System.out.println("Success: Connected to the MySQL database server");
            }
        });

        /*
        controller AppController = new controller();
        AppController.load("src/songs/music1.wav");
        AppController.play();
         */


        database.disconnect();
    }
}