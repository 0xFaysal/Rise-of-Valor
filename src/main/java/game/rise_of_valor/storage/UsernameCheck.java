package game.rise_of_valor.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class UsernameCheck {
    public static boolean isExist(String name) {

        Set<String> usernames = new HashSet<>();

        // Read the file and store usernames in a set
        try (BufferedReader br = new BufferedReader(new FileReader("reg.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length > 0) {
                    usernames.add(parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String newUsername = name; // Replace with the username to check
        if (usernames.contains(newUsername)) {
            return true;
        } else {
            return false;
        }
    }
}