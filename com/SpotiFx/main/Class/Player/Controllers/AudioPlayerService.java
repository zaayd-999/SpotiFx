package com.SpotiFx.main.Class.Player.Controllers;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.io.File;

public class AudioPlayerService {

    private final BasicPlayer player = new BasicPlayer
    private String currentSong;

    public void load(String path) {
        this.currentPath = path;
    }

    public void play() {
            try{
                player.open(new File(currentSong));
                player.play();
            } catch (BasicPlayerExecption) {
                e.printStackTrace();
            }
    }

    public void pause() {

        try {
            player.pause();
        } catch (BasicPlayerException e){
            e.printStackTrace();
        }

    }

    public void resume(){
        try {
            player.resume();
        }catch (BasicPlayerException e){
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            player.stop();
    }catch (BasicPlayerException e) {
        e.printStackTrace();
        }
    }
