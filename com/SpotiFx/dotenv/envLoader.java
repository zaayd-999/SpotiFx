package com.SpotiFx.dotenv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class envLoader {
    private final HashMap<Object,Object> envMap = new HashMap<>();

    public envLoader() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/com/SpotiFx/dotenv/.env"))) {
            String line;
            while((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    envMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
    }


    public Object get(Object key) {
        return envMap.get(key);
    }
}
