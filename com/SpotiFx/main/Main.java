package com.SpotiFx.main;
/*
import com.SpotiFx.main.Class.Player.Song.contributionType;
import com.SpotiFx.main.Class.Player.Song.songCredits;
import com.SpotiFx.main.Class.Player.Song.song;
import com.SpotiFx.main.Class.Player.PlayList.playList;
*/

import com.SpotiFx.db.SpotiDB;

public class Main {
    public static void main(String[] args) {
        /*
        songCredits s = new songCredits();
        s.addContributors(contributionType.Artist,"Ada Wong" , "Luis Sera");
        s.addContributors(contributionType.Producer,"Leon S Kennedy");
        song RE4S = song.builder().artist("Ada Wong").id(1).title("Resident Evil 4 remake - Theme Song").credits(s).build();
        song RE3S = song.builder().artist("Ada Wong").id(2).title("Resident Evil 3 remake - Theme Song").credits(s).build();
        song RE2S = song.builder().artist("Ada Wong").id(2).title("Resident Evil 2 remake - Theme Song").credits(s).build();
        song RE1S = song.builder().artist("Ada Wong").id(2).title("Resident Evil 1 remake - Theme Song").credits(s).build();
        song RE0S = song.builder().artist("Ada Wong").id(2).title("Resident Evil 0 remake - Theme Song").credits(s).build();
        playList thisPlayList = new playList();
        thisPlayList.addSong(RE4S);
        thisPlayList.addSong(RE3S);
        thisPlayList.addSong(RE2S);
        thisPlayList.addSong(RE1S);
        thisPlayList.addSong(RE0S);
        */


    SpotiDB database = new SpotiDB();

    database.connect(error -> {
        if(error == null) {
            System.out.println("Connected Successfully");
        } else {
            System.out.println("Error");
        }
    });



    database.disconnect();


    }
}