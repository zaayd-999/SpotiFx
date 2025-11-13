package com.SpotiFx.db;
import com.SpotiFx.dotenv.envLoader;

import java.io.IOException;

public class SpotiDB extends ConnectionDB {
    public SpotiDB() {

        super(loadDBHost(),loadDBPort(),loadDBUser(),loadDBPassword(),loadDBName());

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
