package com.structure.Class.Player.Controllers;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.io.File;

public class controller {
    private boolean playing;
    private boolean paused;
    private final BasicPlayer player = new BasicPlayer();
    private String currentsong;

    public void load(String path) {
        this.currentsong = path;
    }

    public void play() {
        if (this.currentsong == null) {
            System.out.println("No song selected");
            return;
        }

        try {
            if(this.playing) {
                this.stop();
            }
            player.open(new File(currentsong));
            player.play();
            this.playing = true;
        } catch (BasicPlayerException e) {
            e.printStackTrace();
            this.playing = false;
        }
    }


    public void pause() {

        try {
            player.pause();

        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }

    }

    public void resume() {
        try {
            player.resume();
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            player.stop();
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }
}
