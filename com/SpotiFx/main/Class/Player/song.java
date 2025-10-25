package com.SpotiFx.main.Class.Player;


public class song {
    private final String title;
    private final String album;
    private final String artist;
    private final int duration;
    private String filePath;
    private String lyricsPath;
    private final int year;
    private final String genre;
    private credits SongCredits;

    private song(songBuilder builder){
        this.title = builder.title;
        this.artist = builder.artist;
        this.duration = builder.duration;
        this.album = builder.album;
        this.SongCredits = builder.SongCredits;
        this.filePath = builder.filePath;
        this.lyricsPath = builder.lyricsPath;
        this.year = builder.year;
        this.genre = builder.genre;
    }

    // If you want to make a copy of the song

    public songBuilder toBuilder() {
        return song.builder()
                .title(this.title)
                .artist(this.artist)
                .credits(this.SongCredits)
                .lyricsPath(this.lyricsPath)
                .filePath(this.filePath)
                .album(this.album)
                .duration(this.duration)
                .year(this.year)
                .genre(this.genre);
    }
    // Getters
    public String getTitle() { return this.title; }
    public String getArtist() { return this.artist; }
    public String getAlbum() { return this.album; }
    public int getDuration() { return this.duration; }
    public int getYear() { return this.year; }
    public String getGenre() { return this.genre; }
    public String getFilePath() { return this.filePath; }
    public String getLyricsPath() {return this.lyricsPath; }
    // Setters
    public void setLyricsPath(String lyricsPath){ this.lyricsPath = lyricsPath; }
    public void setFilePath(String filePath){ this.filePath = filePath; }
    public void setSongCredits(credits SongCredits) { this.SongCredits = SongCredits; }

    // Start making a song
    public static songBuilder builder(){
        return new songBuilder();
    }

    public static songBuilder builder(String title , String artist) {
        return new songBuilder(title,artist);
    }

    public static songBuilder builder(String title) {
        return new songBuilder(title);
    }


    public static class songBuilder{
        private String title;
        private String album="Unknown Album";
        private String artist;
        private int duration=0;
        private String filePath;
        private String lyricsPath;
        private int year=2025;
        private String genre;
        private credits SongCredits;

        public  songBuilder(String title , String artist) {
            this.title = title;
            this.artist = artist;
        }

        public songBuilder() {}

        public songBuilder(String title) {
            this.title = title;
        }

        public songBuilder title(String title) {
            this.title = title;
            return this;
        }

        public songBuilder artist(String artist) {
            this.artist = artist;
            return this;
        }

        public songBuilder album(String album) {
            if(album == null) album = "Unknown Album";
            this.album = album;
            return this;
        }

        public songBuilder duration(int duration) {
            if(duration < 0) throw new IllegalArgumentException("Duration cannot be negative");
            this.duration = duration;
            return this;
        }

        public songBuilder filePath(String filePath) {
            this.filePath = filePath;
            return  this;
        }

        public songBuilder lyricsPath(String lyricsPath) {
            this.lyricsPath = lyricsPath;
            return this;
        }

        public songBuilder year(int year) {
            this.year = year;
            return this;
        }

        public songBuilder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public songBuilder credits(credits credit) {
            this.SongCredits = credit;
            return  this;
        }
        // Make a song
        public song build(){
            if(title==null) throw new IllegalStateException("Title is required");
            if(artist==null) throw new IllegalStateException("Artist is required");
            return new song(this);
        }
    }

    public String getFormattedDuration() {
        int dr = this.duration;
        int hours = dr/3600;
        dr = dr%3600;
        int mins = dr/60;
        int secs = dr%60;
        String returnText = "";
        if(hours > 0) {
            if(hours < 10) returnText+="0";
            returnText+=(hours+":");
        }
        if(mins > 0) {
            if(mins < 10) returnText+="0";
            returnText+=(mins+":");
        }
        if(mins==0) returnText+="00:";
        if(secs > 0) {
            if(secs < 10) returnText+="0";
            returnText+=(secs);
        }
        if(secs == 0) returnText+="00";
        return returnText;
    }

    public void playSound(){

    }

    public void pauseSound(){

    }

    public void stopSound(){

    }

    @Override
    public String toString() {
        return String.format("%s by %s - %s (%s)", this.title, this.artist, this.album, this.getFormattedDuration());
    }
}
