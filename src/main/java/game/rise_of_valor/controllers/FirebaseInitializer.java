//package game.rise_of_valor.controllers;
//
//import java.io.FileInputStream;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.auth.FirebaseCredentials;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//
//public class FirebaseInitializer {
//    public static void initializeFirebase(String serviceAccountKeyPath) {
//        try {
//            FileInputStream serviceAccount = new FileInputStream(serviceAccountKeyPath);
//            FirebaseOptions options = FirebaseOptions.builder()
//                    .setCredentials(FirebaseCredentials.fromStream(serviceAccount))
//                    .build();
//            FirebaseApp.initializeApp(options);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
