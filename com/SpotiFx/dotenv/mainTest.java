package com.SpotiFx.dotenv;

import java.io.IOException;
import java.io.*;
import java.util.*;

public class mainTest {
    public static void main(String[] args) {
        try {
            envLoader loader = new envLoader();
            System.out.println(loader.get("TOKEN"));
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
