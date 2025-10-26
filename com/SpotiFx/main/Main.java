package com.SpotiFx.main;
import com.SpotiFx.main.Class.Player.contributionType;
import com.SpotiFx.main.Class.Player.songCredits;
import com.SpotiFx.main.Class.Player.song;

public class Main {
    public static void main(String[] args) {
        songCredits s = new songCredits();
        s.addContributors(contributionType.Artist,"Ada Wong" , "Luis Sera");
        s.addContributors(contributionType.Producer,"Leon S Kennedy");

        song RE4S = song.builder().artist("Ada Wong").title("Resident Evil 4 remake - Theme Song").credits(s).build();

        RE4S.getSongCredits().displayCredits();
    }
}