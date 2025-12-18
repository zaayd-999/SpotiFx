package com.dotenv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class envLoader {
    private final HashMap<Object,Object> envMap = new HashMap<>();

    public envLoader() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("java/com/dotenv/.env"))) { // Read .env FILE if exists
            String line;
            while((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Check if line is empty
                if (line.startsWith("#")) continue; // COMMENT
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    if(parts[1].trim().isEmpty() || parts[1] == "") continue;
                    envMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
    }


    public Object get(Object key) {
        return envMap.get(key) == null ? "None." : envMap.get(key);
    }
}
