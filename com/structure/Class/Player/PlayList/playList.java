package com.structure.Class.Player.PlayList;
import com.structure.Class.Player.Song.song;
import com.structure.Class.User.user;

import java.util.HashMap;


public abstract class playList {
    private final playlist playList;
    private int totalsongs = 0;
    private int currentsong;
    private boolean playing;
    private final playlistType type;
    private final int id;
    private final user creator;
    private String title;
    private String description;

    public playList(int id , String title , String description , playlistType type , user creator) {
        this.playList = new playlist();
        this.currentsong = -1;
        this.totalsongs = 0;
        this.playing = false;
        this.id = id;
        this.type = type;
        this.creator = creator;
        this.title = title;
        this.description = description;
    }

    public int getPlayListId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public user getCreator() {
        return this.creator;
    }

    public playlistType getPlayListType() {
        return this.type;
    }

    public void addsong(song thissong) {
        if(this.playList.containsValue(thissong)) return;
        this.playList.put(this.totalsongs,thissong);
        this.totalsongs++;
    }

    public void addsongsToPlayList(song... thissongs) {
        for(song thissong : thissongs) {
            addsong(thissong);
        }
    }

    public void removesongFromPlayList(int position) {
        if(position < 0 || position >= this.totalsongs) return;
        playList.remove(position);
        for(int i = position+1 ; i < totalsongs ; i++) {
            song thissong = playList.get(i);
            playList.remove(i);
            playList.put(i-1,thissong);
        }

        this.totalsongs--;
        if(this.currentsong == position) {
            stop();
        } else if (this.currentsong > position) {
            this.currentsong--;
        }
    }

    public song getCurrentsong() {
        if (this.currentsong >= 0 && this.currentsong < this.totalsongs) {
            return playList.get(this.currentsong);
        }
        return null;
    }

    public int getCurrentsongIndex() {
        return this.currentsong;
    }

    public int getTotalsongs() {
        return this.totalsongs;
    }

    public playlist getPlayList() {
        return this.playList;
    }

    public void play(int position) {
        if (position < 0 || position >= totalsongs) return;
        this.currentsong = position;
        this.playing = true;
        System.out.println("Now playing: " + playList.get(position).getTitle());
    }

    public void play() {
        if(this.totalsongs > 0) play(0);
    }

    public void pause() {
        this.playing = false;
        if(currentsong>=0) {
            System.out.println("Paused: " + playList.get(currentsong).getTitle());
        }
    }

    public void resume() {
        if (this.currentsong >= 0) {
            this.playing = true;
            System.out.println("Resumed: " + playList.get(this.currentsong).getTitle());
        }
    }

    public void stop() {
        this.currentsong = -1;
        this.playing = false;
        System.out.println("Playback stopped");
    }

    public void playNextsong() {
        if (this.totalsongs == 0) return;

        this.currentsong++;
        if (this.currentsong >= this.totalsongs) {
            this.currentsong = 0;
        }
        this.playing = true;
        System.out.println("Now playing: " + playList.get(currentsong).getTitle());
    }

    public void playPrevsong() {
        if (this.totalsongs == 0) return;

        this.currentsong--;
        if (this.currentsong < 0) {
            this.currentsong = this.totalsongs - 1; // Loop to end
        }
        this.playing = true;
        System.out.println("Now playing: " + playList.get(currentsong).getTitle());
    }

    public void shuffle() {
        java.util.List<song> mysongList = this.playList.getOrderedsongs();
        java.util.Collections.shuffle(mysongList);
        this.playList.clear();
        for(int i = 0 ; i < mysongList.size() ; i++) {
            this.playList.put(i,mysongList.get(i));
        }
        this.currentsong = -1;
        this.playing = false;
    }

    public void clearPlayList() {
        this.totalsongs = 0;
        this.currentsong = -1;
        this.playing = false;
        this.playList.clear();
    }
    public class playlist extends  HashMap<Integer,song> {
        public playlist () {
            super();
        }

        public playlist(playlist original) {
            super(original);
        }

        public java.util.List<song> getOrderedsongs() {
            java.util.List<song> orderedsongs = new java.util.ArrayList<>();
            for (int i = 0; i < totalsongs; i++) {
                orderedsongs.add(this.get(i));
            }
            return orderedsongs;
        }
    }

    public String toJSON() {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        for (int i = 0 ; i < this.totalsongs ; i++) {
            if(this.playList.get(i) != null) {
                json.append("\"").append(i).append("\":\"").append(this.playList.get(i).getsongId()).append('"').append("\n");
            } else {
                break;
            }
        }
        json.append("}");
        return json.toString();
    }
}