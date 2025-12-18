package com.db;
import com.db.Classes.QueryResult;
import com.db.Classes.QueryResults;
import com.dotenv.envLoader;

import com.structure.Class.Player.PlayList.UserPlayList;
import org.json.JSONObject;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import com.security.PasswordUtil;

import com.structure.Class.Player.Song.song;
import com.structure.Class.Player.Song.songCredits;
import com.structure.Class.Player.Song.song.songBuilder;
import com.structure.Class.Player.Song.contributionType;
import com.structure.Class.Player.PlayList.playList;
import com.structure.Class.Player.PlayList.playlistType;
import com.structure.Class.Player.PlayList.Album;
import com.structure.Class.User.user;
import com.structure.Class.User.artist;
import com.structure.Class.User.listener;
import com.structure.Class.User.user.userBuilder;
import com.structure.Class.User.listener.listenerBuilder;
import com.structure.Class.User.artist.artistBuilder;
import com.structure.Class.User.userTypes;
import com.db.Classes.users;
import com.db.Classes.songs;
import com.db.Classes.playlists;


public class SpotiDB extends ConnectionDB {
    private users AppUsers;
    private songs Appsongs;
    private playlists AppPlaylists;

    public SpotiDB() {
        super(loadDBHost(),loadDBPort(),loadDBUser(),loadDBPassword(),loadDBName());
        this.AppUsers = new users();
        this.Appsongs = new songs();
        this.AppPlaylists = new playlists();
    }

    public users loadUsersFromDB() {
        this.AppUsers = new users();
        String SQL_QUERY = "SELECT * FROM users";
        QueryResults results = this.querySync(SQL_QUERY);
        int id;
        String username;
        String email;
        int type;
        listener listener;
        artist artist;
        int lastPlayedsong;
        int lastPlayedPlaylist;
        for(QueryResult result : results) {
            id = (int) result.get("id");
            username = (String) result.get("username");
            email = (String) result.get("email");
            type = (int) result.get("type");
            lastPlayedsong = (int) result.get("lastSongId");
            lastPlayedPlaylist = (int) result.get("lastPlaylistId");
            if(type == 0) {
                listener = new listenerBuilder(id,username).type().email(email).lastPlayedsong(lastPlayedsong).lastPlayedPlaylist(lastPlayedPlaylist).build();
                AppUsers.put(id,listener);
            } else {
                artist = new artistBuilder(id,username).type().email(email).build();
                AppUsers.put(id,artist);
            }
            listener = null;
            artist = null;
        }
        return AppUsers;
    }

    public int[] stringToJsonArray(String str) {
        if (str == null || str.isEmpty()) {
            return new int[0];
        }

        // Formatting the string
        if (str.startsWith("[")) {
            str = str.substring(1);
        }
        if (str.endsWith("]")) {
            str = str.substring(0, str.length() - 1);
        }

        // Split the string
        String[] splitedString = Arrays.stream(str.split(","))
                .map(String::trim)
                .filter(x -> !x.isEmpty())
                .toArray(String[]::new);

        List<Integer> resultList = new ArrayList<>();

        for (String txt : splitedString) {
            if (txt.equals("\"\"")) {
                continue;
            }
            if (txt.startsWith("\"")) {
                txt = txt.substring(1);
            }
            if (txt.endsWith("\"")) {
                txt = txt.substring(0, txt.length() - 1);
            }

            try {
                int id = Integer.parseInt(txt);
                resultList.add(id);
            } catch (NumberFormatException e) {
                System.err.println("Skipping invalid number: " + txt);
            }
        }

        int[] results = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            results[i] = resultList.get(i);
        }
        return results;
    }

    public playlists loadPlaylistsFromDB() {
        this.AppPlaylists = new playlists();
        if(this.AppUsers == null) loadUsersFromDB();
        if(this.AppUsers == null) throw new RuntimeException("No users to load"); // We need users to load all playlists
        if(this.Appsongs == null) loadsongsFromDB();
        if(this.Appsongs == null) throw new RuntimeException("No songs to load");
        String SQL_QUERY = "SELECT * FROM playlists";
        QueryResults resuls = this.querySync(SQL_QUERY);
        int id;
        String name;
        String description;
        int type;
        int size;
        int createdBy;
        Album album;
        UserPlayList userPlayList;
        String songs;
        JSONObject songsJson;
        for(QueryResult result : resuls) {
            id = (int) result.get("id");
            name = (String) result.get("name");
            description = (String) result.get("description");
            type = (int) result.get("type");
            size = (int) result.get("size");
            createdBy = (int) result.get("created_by");
            user creator = AppUsers.get(createdBy);
            songs = (String) result.get("songs");
            songsJson = new JSONObject(songs);



            if(AppUsers.get(createdBy) == null) continue;
            if(type == 0) {
                album = new Album(id , name , description , creator);
                for(String key : songsJson.keySet()) {
                    int songID = Integer.parseInt((String) songsJson.get(key));
                    album.addsongsToPlayList(this.Appsongs.get(songID));
                }
                AppPlaylists.put(id , album);
            } else {
                userPlayList = new UserPlayList(id , name , description ,creator);
                for(String key : songsJson.keySet()) {
                    int songID = Integer.parseInt((String) songsJson.get(key));
                    userPlayList.addsongsToPlayList(this.Appsongs.get(songID));
                }
                AppPlaylists.put(id , userPlayList);
            }
            album = null;
            userPlayList = null;
        }
        return AppPlaylists;
    }

    public songs loadsongsFromDB() {
        this.Appsongs = new songs();

        if (this.AppUsers == null) loadUsersFromDB();

        String SQL_QUERY = "SELECT * , JSON_EXTRACT(credits,\"$.artist\") As artists , JSON_EXTRACT(credits,\"$.publisher\") As publishers , JSON_EXTRACT(credits,\"$.producer\") As producers FROM songs;";
        QueryResults rows = this.querySync(SQL_QUERY);
        for (QueryResult row : rows) {

            int id = (int) row.get("id");
            String title = (String) row.get("title");
            int artistId = (int) row.get("artist_id");
            int albumId = row.get("album_id") != null ? (int) row.get("album_id") : -1;
            int duration = row.get("duration") != null ? (int) row.get("duration") : 0;
            int year = row.get("year") != null ? (int) row.get("year") : 2025;
            String genre = row.get("genre") != null ? (String) row.get("genre") : null;
            user user = this.AppUsers.get(artistId);

            if (!(user instanceof artist artist)) {
                System.err.println("Skipping song id=" + id + " : artist_id=" + artistId + " not found");
                continue;
            }

            int[] artists_id = stringToJsonArray((String) row.get("artists"));
            int[] publishers_id = stringToJsonArray((String) row.get("publishers"));
            int[] producers_id = stringToJsonArray((String) row.get("producers"));

            songCredits credits = new songCredits();
            for (int aid : artists_id) {
                user user1 = this.AppUsers.get(aid);
                if (user1 instanceof artist) {
                    credits.addContributor(contributionType.Artist, (artist) user1);
                }
            }

            for (int pid : publishers_id) {
                user user1 = this.AppUsers.get(pid);
                if (user1 instanceof artist) {
                    credits.addContributor(contributionType.Publisher, (artist) user1);
                }
            }

            for (int prid : producers_id) {
                user user1 = this.AppUsers.get(prid);
                if (user1 instanceof artist) {
                    credits.addContributor(contributionType.Producer, (artist) user1);
                }
            }

            song.songBuilder builder = song.builder()
                    .id(id)
                    .title(title)
                    .artist(artist)
                    .album(albumId)
                    .duration(duration)
                    .genre(genre)
                    .credits(credits)
                    .year(year);

            this.Appsongs.put(id, builder.build());
        }
        return this.Appsongs;
    }

    public users getAppUsers() {
        loadUsersFromDB();
        return this.AppUsers;
    }

    public songs getAppsongs() {
        loadsongsFromDB();
        return this.Appsongs;
    }

    public playlists getAppPlaylists() {
        return this.AppPlaylists;
    }

    public user addUserToDB(user user , String plainPassword) {
        if (user == null) throw new IllegalArgumentException("Expected User instance, got: null");
        String username = user.getUsername();
        String email = user.getEmail();
        String password = PasswordUtil.hash(plainPassword);
        int type = user.getType().getValue();
        String SQL_EXECUTE = "INSERT INTO users(username,password,email,type) VALUES (?,?,?,?)";
        Object[] params = new Object[]{ username , password , email , type };
        int affectedRows = this.executeSync(SQL_EXECUTE,params);
        userBuilder oldBuilder = user.toBuilder();
        QueryResult result = this.firstQuerySync("SELECT * FROM users ORDER BY id DESC LIMIT 1");
        if(type == 0) {
            listenerBuilder thisBuilder = new listenerBuilder((int) result.get("id"),username).email(email).type(userTypes.LISTENER);
            this.AppUsers.putIfAbsent((int) result.get("id"), thisBuilder.build());
            return thisBuilder.build();
        } else if (type == 1) {
            artistBuilder thisBuilder = new artistBuilder((int) result.get("id"),username).email(email).type(userTypes.ARTIST);
            this.AppUsers.putIfAbsent((int) result.get("id"), thisBuilder.build());
            return thisBuilder.build();
        }
        user = oldBuilder.id((int) result.get("id")).build();
        this.AppUsers.putIfAbsent((int) result.get("id"), user);
        return user;
    }

    public user changeUserInfo(int userId , user newUser , String plainPassword , user oldUser) {
        String username = newUser.getUsername();
        String email = newUser.getEmail();
        //String password = user.getPassword();
        String password = "";
        if(!plainPassword.isEmpty()) password = PasswordUtil.hash(plainPassword);
        String SQL_EXECUTE = "UPDATE users SET username=?,email=?,lastsongId=?,lastPlaylistId=? WHERE id=?";
        Object[] params = new Object[]{newUser.getId(),newUser.getEmail(),newUser.getLastPlayedsong(),newUser.getLastPlayedsong(),newUser.getId()};
        int affectedRows = this.executeSync(SQL_EXECUTE,params);
        if(affectedRows == 0) return oldUser;
        if(!password.isEmpty()) {
            SQL_EXECUTE = "UPDATE users SET password=? WHERE id=?";
            params = new Object[]{password,newUser.getId()};
            this.executeSync(SQL_EXECUTE,params);
        }
        return newUser;
    }

    public user changeUserInfo(int userId , user newUser , user oldUser ) {
        return changeUserInfo(userId,newUser,"",oldUser);
    }

    public user changeUserInfo(int userId , user newUser , String password ) {
        return changeUserInfo(userId,newUser,password,null);
    }

    public user changeUserInfo(int userId , user newUser ) {
        return changeUserInfo(userId,newUser,"",null);
    }

    public user deleteUserFromDB(user rmUser) {
        String SQL_EXECUTE = "DELETE FROM users WHERE id=?";
        int id = rmUser.getId();
        Object[] params = new Object[]{id};
        int affectedRows = this.executeSync(SQL_EXECUTE,params);
        this.AppUsers.remove(id);
        if(affectedRows == 0) return rmUser;
        return null;
    }

    public song addsongToDB(song newsong) {
        //if(!(newsong instanceof song)) throw new IllegalArgumentException("The song must be an instance of song ");
        String title = newsong.getTitle();
        artist artist = newsong.getArtist();
        int album = newsong.getAlbum();
        String genre = newsong.getGenre();
        int year = newsong.getYear();
        songCredits credits = newsong.getsongCredits();
        String SQL_EXECUTE = "INSERT INTO songs(title,artist_id,album_id,year,genre,credits) VALUES (?,?,?,?,?,?)";
        Object[] params = {title,artist.getId(),album,year,genre,credits.stringify()};

        int affectedRows = this.executeSync(SQL_EXECUTE,params);

        songBuilder oldBuider = newsong.toBuilder();
        QueryResult result = this.firstQuerySync("SELECT * FROM songs ORDER BY id DESC LIMIT 1");

        newsong = oldBuider.id((int) result.get("id")).build();
        this.Appsongs.put((int) result.get("id") , newsong);
        return newsong;
    }

    public song changesongInfo(int songId , song newsong , song oldsong ) {
        String SQL_EXECUTE = "UPDATE songs SET title=?,artist_id=?,album_id=?,duration=?,year=?,genre=?,credits=? WHERE id=?";
        String title = newsong.getTitle();
        artist artist = newsong.getArtist();
        int album = newsong.getAlbum();
        String genre = newsong.getGenre();
        int duration = newsong.getDuration();
        int year = newsong.getYear();
        int id = newsong.getsongId();
        songCredits credits = newsong.getsongCredits();
        Object[] params = { title , artist.getId() , album , duration , year , genre , credits.stringify() , id };
        int affectedRows = this.executeSync(SQL_EXECUTE,params);
        if(affectedRows == 0) return oldsong;
        this.Appsongs.put(songId , newsong);
        return newsong;
    }

    public song changesongInfo(int songId , songBuilder builder , song oldsong ) {
        return this.changesongInfo(songId , builder.build() , oldsong );
    }

    public song changesongInfo(int songId , song newsong) {
        return this.changesongInfo(songId , newsong , null);
    }

    public song changesongInfo(int songId , songBuilder builder) {
        return this.changesongInfo(songId , builder , null);
    }

    public song removesongFromDB(song rmsong)  {
        String SQL_QUERY = "DELETE FROM songs WHERE id=?";
        int id = rmsong.getsongId();
        Object[] params = { id };
        this.Appsongs.remove(id);
        int affectedRows = this.executeSync(SQL_QUERY,params);
        if(affectedRows == 0) return rmsong;
        return null;
    }

    private playList addPlayListToDB(playList newPlaylist) {
        String SQL_EXECUTE = "INSERT INTO playlists(name,description,created_by,songs,type,size) VALUES (?,?,?,?,?,?)";
        Object[] params = new Object[]{ newPlaylist.getTitle() , newPlaylist.getDescription() , newPlaylist.getCreator().getId() , newPlaylist.toJSON() , newPlaylist.getPlayListType().getValue() , newPlaylist.getPlayList().size() };
        int affectedRows = this.executeSync(SQL_EXECUTE,params);
        if(affectedRows == 0) return null;
        QueryResult result = this.firstQuerySync("SELECT id FROM playlists ORDER BY id DESC LIMIT 1");
        return newPlaylist;
    }

    public playList changePlaylistInfo(int playlist_id , playList newPlaylist , playList oldPlaylist) {
        String SQL_EXECUTE = "UPDATE playlists SET name=? , description = ? , songs = ? , size = ? WHERE id=?";
        Object[] params = new Object[]{newPlaylist.getTitle() , newPlaylist.getDescription() , newPlaylist.toJSON() , newPlaylist.getTotalsongs() , newPlaylist.getPlayListId()};
        int affectedRows = this.executeSync(SQL_EXECUTE,params);
        if(affectedRows == 0) return oldPlaylist;
        this.AppPlaylists.put(playlist_id,newPlaylist);
        return newPlaylist;
    }

    private void deletePlaylist(playList rmPlaylist) {
        String SQL_EXECUTE = "DELETE FROM playlists WHERE id=?";
        int id = rmPlaylist.getPlayListId();
        Object[] params = new Object[]{ id };
        this.AppPlaylists.remove(id);
        this.executeSync(SQL_EXECUTE,params);
    }



    public playList changePlaylistInfo(int playlist_id , playList newPlaylist) {
        return changePlaylistInfo(playlist_id,newPlaylist,null);
    }

    public user login(String username , String plainPassword ) {
        if (username == null || username.isEmpty() || plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Username or Password is null or empty");
        }
        String SQL_QUERY = "SELECT id,password from users WHERE username=?";
        Object[] params = new Object[]{ username };
        QueryResult result = this.firstQuerySync(SQL_QUERY,params);
        int id = (int) result.get("id");
        String hashedPassword = (String) result.get("password");
        if(PasswordUtil.checkHash(plainPassword,hashedPassword)) {
            if(this.AppUsers.get(id) == null) {
                this.loadUsersFromDB();
            }
            return this.AppUsers.get(id);
        };
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
            e.fillInStackTrace();
            return null;
        }
    }




}
