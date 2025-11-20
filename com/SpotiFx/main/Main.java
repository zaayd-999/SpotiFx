package com.SpotiFx.main;

import com.SpotiFx.db.Callbacks.QueryCallback;
import com.SpotiFx.main.Class.Player.PlayList.UserPlayList;
import com.SpotiFx.main.Class.Player.Song.contributionType;
import com.SpotiFx.main.Class.Player.Song.songCredits;
import com.SpotiFx.main.Class.Player.Song.song;
import com.SpotiFx.main.Class.Player.PlayList.playList;
import com.SpotiFx.db.Classes.QueryResult;


import com.SpotiFx.db.SpotiDB;

public class Main {
    public static void main(String[] args) {
    songCredits s = new songCredits();
    s.addContributors(contributionType.Artist,"Ada Wong" , "Luis Sera");
    s.addContributors(contributionType.Producer,"Leon S Kennedy");
    song RE4S = song.builder().artist("Ada Wong").id(1).title("Resident Evil 4 remake - Theme Song").credits(s).build();

    SpotiDB database = new SpotiDB();

    }
}