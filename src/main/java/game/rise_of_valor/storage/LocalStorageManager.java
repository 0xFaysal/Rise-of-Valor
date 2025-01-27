package game.rise_of_valor.storage;

import game.rise_of_valor.controllers.registrationController;

import javax.imageio.IIOException;
import java.io.*;

public class LocalStorageManager {
    public static void signupData(String name, String pass) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reg.txt", true))) {
            bw.write(name + ":" + pass);
            bw.write("\n");

        } catch (IOException e) {
            System.out.println("Saving Issue :" + e.getMessage());
        }
    }

}