package com.SpotiFx.main;
/*
// Music Class
import com.SpotiFx.main.Class.Player.PlayList.playList;
import com.SpotiFx.main.Class.Player.PlayList.UserPlayList;
import com.SpotiFx.main.Class.Player.Song.contributionType;
import com.SpotiFx.main.Class.Player.Song.songCredits;
import com.SpotiFx.main.Class.Player.Song.song;
*/
// Database Class (extended from ConnectionDB)
import com.SpotiFx.db.SpotiDB;

// User Class

import com.SpotiFx.main.Class.User.*;

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
        songCredits s = new songCredits();
        s.addContributors(contributionType.Artist,"Ada Wong" , "Luis Sera");
        s.addContributors(contributionType.Producer,"Leon S Kennedy");
        song RE4S = song.builder().artist("Ada Wong").id(1).title("Resident Evil 4 remake - Theme Song").credits(s).build();
        song RE3S = song.builder().artist("Ada Wong").id(2).title("Resident Evil 3 remake - Theme Song").credits(s).build();
        song RE2S = song.builder().artist("Ada Wong").id(3).title("Resident Evil 2 remake - Theme Song").credits(s).build();
        playList myPlaylist = new UserPlayList(1);
        myPlaylist.addSong(RE4S);
        myPlaylist.addSong(RE3S);
        myPlaylist.addSong(RE2S);
        //RE4S = database.addSongToDB(RE4S);
        //RE3S = database.addSongToDB(RE3S);
        //RE2S = database.addSongToDB(RE2S);
        */


        artist sahmoudi = new artist(1,"l" , "sahmoudi@gmail.com" , "password1234");
        database.addUserToDB(sahmoudi);
        //database.deleteUserFromDB(sahmoudi);
        database.disconnect();
    }
}