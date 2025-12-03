package com.SpotiFx.main;

// Database Class (extended from ConnectionDB)
import com.SpotiFx.db.SpotiDB;

// User Class
import com.SpotiFx.main.Class.User.artist;
import com.SpotiFx.main.Class.User.artist.artistBuilder;
import com.SpotiFx.main.Class.User.listener;
import com.SpotiFx.main.Class.User.listener.listenerBuilder;

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

        artistBuilder JuiceWRLDBuilder = new artistBuilder(1,"Juice WRLD");
        artist JuiceWRLD = JuiceWRLDBuilder.build();
        JuiceWRLD = (artist) database.addUserToDB(JuiceWRLD);
        System.out.println(JuiceWRLD.getId());


        database.disconnect();
    }
}