package com.SpotiFx.db;
import com.SpotiFx.db.Classes.QueryResult;
import com.SpotiFx.dotenv.envLoader;

import java.io.IOException;

import com.SpotiFx.main.Class.Player.Song.song;
import com.SpotiFx.main.Class.Player.Song.songCredits;
import com.SpotiFx.main.Class.Player.Song.song.songBuilder;
import com.SpotiFx.main.Class.Player.PlayList.playList;
import com.SpotiFx.main.Class.Player.PlayList.Album;
import com.SpotiFx.main.Class.User.artist;
import com.SpotiFx.main.Class.User.user;

public class SpotiDB extends ConnectionDB {
    public SpotiDB() {
        super(loadDBHost(),loadDBPort(),loadDBUser(),loadDBPassword(),loadDBName());
    }


    public user addUserToDB(user user) {
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword(); // Add hash password
        String SQL_EXECUTE = "INSERT INTO users(username,password,email) VALUES (?,?,?)";
        Object[] params = new Object[]{ username , password , email };
        int affectedRows = this.executeSync(SQL_EXECUTE,params);

        return null;
    }

    public void deleteUserFromDB(user rmUser) {
        String SQL_EXECUTE = "DELETE FROM users WHERE id=?";
        Object[] params = new Object[]{rmUser.getId()};

        this.executeSync(SQL_EXECUTE,params);
    }


    public song addSongToDB(song newSong) {
        String title = newSong.getTitle();
        artist artist = newSong.getArtist();
        Album album = newSong.getAlbum();
        String genre = newSong.getGenre();
        int year = newSong.getYear();
        songCredits credits = newSong.getSongCredits();
        String SQL_EXECUTE = "INSERT INTO songs(title,artist_id,album_id,year,genre,credits) VALUES (?,?,?,?,?,?)";
        Object[] params = {title,artist.getId(),album.getPlayListId(),year,genre,credits.stringify()};

        int affectedRows = this.executeSync(SQL_EXECUTE,params);

        songBuilder oldBuider = newSong.toBuilder();
        QueryResult result = this.firstQuerySync("SELECT * FROM songs ORDER BY id DESC LIMIT 1");

        newSong = oldBuider.id((int) result.get("id")).build();
        return newSong;
    }

    public song changeSongInfo(int songId , song newSong , song oldSong ) {
        String SQL_EXECUTE = "UPDATE songs SET title=?,artist_id=?,album_id=?,duration=?,year=?,genre=?,credits=? WHERE id=?";
        String title = newSong.getTitle();
        artist artist = newSong.getArtist();
        Album album = newSong.getAlbum();
        String genre = newSong.getGenre();
        int duration = newSong.getDuration();
        int year = newSong.getYear();
        int id = newSong.getSongId();
        songCredits credits = newSong.getSongCredits();
        Object[] params = { title , artist.getId() , album.getPlayListId() , duration , year , genre , credits.stringify() , id };
        int affectedRows = this.executeSync(SQL_EXECUTE,params);
        if(affectedRows == 0) return oldSong;
        return newSong;
    }

    public song changeSongInfo(int songId , songBuilder builder , song oldSong ) {
        return this.changeSongInfo(songId , builder.build() , oldSong );
    }

    public song changeSongInfo(int songId , song newSong) {
        return this.changeSongInfo(songId , newSong , null);
    }

    public song changeSongInfo(int songId , songBuilder builder) {
        return this.changeSongInfo(songId , builder , null);
    }

    public song removeSongFromDB(song rmSong)  {
        String SQL_QUERY = "DELETE FROM songs WHERE id=?";
        Object[] params = { rmSong.getSongId() };
        int affectedRows = this.executeSync(SQL_QUERY,params);
        if(affectedRows == 0) return rmSong;
        return null;
    }

    public playList addPlayListToDB(playList newPlaylist) {
        return null;
    }

    private static String loadDBHost() { return getEnv("DB_HOST"); }
    private static String loadDBPort() { return getEnv("DB_PORT"); }
    private static String loadDBUser() { return getEnv("DB_USER"); }
    private static String loadDBPassword() { return getEnv("DB_PASSWORD"); }
    private static String loadDBName() { return getEnv("DB_NAME"); }

    private static String getEnv(String key) {
        try {
            envLoader env = new envLoader(); // your loader
            return (String )env.get(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




}
