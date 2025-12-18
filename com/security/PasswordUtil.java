package com.security;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hash(String plainPassword , int salt_size) {
        return BCrypt.hashpw(plainPassword , BCrypt.gensalt(salt_size));
    }

    public static String hash(String plainPassword) {
        return hash(plainPassword,12);
    }
    public static boolean checkHash(String plainPassword, String hashedPassword) {
        if (hashedPassword == null) return false;
        return BCrypt.checkpw(plainPassword,hashedPassword);
    }
}
