package com.SpotiFx.main.Class.Player.PlayList;
import com.SpotiFx.main.Class.Player.Song.song;

import java.util.HashMap;


public class playList {
    private playlist playList;
    private int totalSongs;
    private int currentSong;
    private boolean playing;

    public playList() {
        this.playList = new playlist();
        this.currentSong = -1;
        this.totalSongs = 0;
        this.playing = false;
    }

    public void addSong(song thisSong) {
        if(this.playList.containsValue(thisSong)) return;
        this.playList.put(this.totalSongs,thisSong);
        this.totalSongs++;
    }

    public void addSongsToPlayList(song... thisSongs) {
        for(song thisSong : thisSongs) {
            addSong(thisSong);
        }
    }

    public void removeSongFromPlayList(int position) {
        if(position < 0 || position >= this.totalSongs) return;
        playList.remove(position);
        for(int i = position+1 ; i < totalSongs ; i++) {
            song thisSong = playList.get(i);
            playList.remove(i);
            playList.put(i-1,thisSong);
        }

        this.totalSongs--;
        if(this.currentSong == position) {
            stop();
        } else if (this.currentSong > position) {
            this.currentSong--;
        }
    }

    public song getCurrentSong() {
        if (this.currentSong >= 0 && this.currentSong < this.totalSongs) {
            return playList.get(this.currentSong);
        }
        return null;
    }

    public int getCurrentSongIndex() {
        return this.currentSong;
    }

    public int getTotalSongs() {
        return this.totalSongs;
    }

    public boolean isPlaying() {
        return this.playing;
    }

    public playlist getPlayList() {
        return this.playList;
    }

    public void play(int position) {
        if (position < 0 || position >= totalSongs) return;
        this.currentSong = position;
        this.playing = true;
        System.out.println("Now playing: " + playList.get(position).getTitle());
    }

    public void play() {
        if(this.totalSongs > 0) play(0);
    }

    public void pause() {
        this.playing = false;
        if(currentSong>=0) {
            System.out.println("Paused: " + playList.get(currentSong).getTitle());
        }
    }

    public void resume() {
        if (this.currentSong >= 0) {
            this.playing = true;
            System.out.println("Resumed: " + playList.get(this.currentSong).getTitle());
        }
    }

    public void stop() {
        this.currentSong = -1;
        this.playing = false;
        System.out.println("Playback stopped");
    }

    public void playNextSong() {
        if (this.totalSongs == 0) return;

        this.currentSong++;
        if (this.currentSong >= this.totalSongs) {
            this.currentSong = 0;
        }
        this.playing = true;
        System.out.println("Now playing: " + playList.get(currentSong).getTitle());
    }

    public void playPrevSong() {
        if (this.totalSongs == 0) return;

        this.currentSong--;
        if (this.currentSong < 0) {
            this.currentSong = this.totalSongs - 1; // Loop to end
        }
        this.playing = true;
        System.out.println("Now playing: " + playList.get(currentSong).getTitle());
    }

    public void shuffle() {
        java.util.List<song> mySongList = this.playList.getOrderedSongs();
        java.util.Collections.shuffle(mySongList);
        this.playList.clear();
        for(int i = 0 ; i < mySongList.size() ; i++) {
            this.playList.put(i,mySongList.get(i));
        }
        this.currentSong = -1;
        this.playing = false;
    }

    public void clearPlayList() {
        this.totalSongs = 0;
        this.currentSong = -1;
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

        public java.util.List<song> getOrderedSongs() {
            java.util.List<song> orderedSongs = new java.util.ArrayList<>();
            for (int i = 0; i < totalSongs; i++) {
                orderedSongs.add(this.get(i));
            }
            return orderedSongs;
        }
    }
}