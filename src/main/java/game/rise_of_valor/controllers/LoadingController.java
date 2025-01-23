package game.rise_of_valor.controllers;

import game.rise_of_valor.shareData.DataManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    private ProgressBar progressbar;
    public static DataManager dataManager;
    private boolean dataManagerInitialized = false;
    private Timeline[] dataManagerTimeline = {null};
    private boolean serverConnectionOpened = false;
    Thread dataLoaderThread;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create a new Timeline object
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressbar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(progressbar.progressProperty(), 0.25))
        );
        timeline.setCycleCount(1); // Ensure it fills only once
        timeline.play();
        // Add a listener to the progress property
        progressbar.progressProperty().addListener((observable, oldValue, newValue) -> {
            double roundedValue = BigDecimal.valueOf(newValue.doubleValue())
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            if (roundedValue == 0.25 && !dataManagerInitialized) {
                dataManagerInitialized = true; // Set the flag to true
                // Run DataManager initialization in a separate thread
                dataLoaderThread = new  Thread(() -> {
                    dataManager = new DataManager();
                    Platform.runLater(() -> {
                        // Update progress bar to 100% after DataManager initialization
                        dataManagerTimeline[0] = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(progressbar.progressProperty(), 0.26)),
                                new KeyFrame(Duration.seconds(1), new KeyValue(progressbar.progressProperty(), 1))
                        );
                        dataManagerTimeline[0].setCycleCount(1);
                        dataManagerTimeline[0].play();
                    });
                });
                dataLoaderThread.start();
                dataLoaderThread.setName("DataManagerThread");
            }

            if (roundedValue == 0.3) {
                System.out.println("Internet connection status: " + InternetConnectionChecker.isInternetAvailable());
            }

            if (roundedValue >= 0.4 && roundedValue <= 0.9 && !serverConnectionOpened) {
                serverConnection();
                dataManagerTimeline[0].pause();
                serverConnectionOpened = true;
            }


            if (newValue.doubleValue() == 1.0 && serverConnectionOpened) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/login-registration.fxml"));
                    mainPane.getChildren().clear();
                    mainPane.getChildren().add(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void serverConnection() {
        Platform.runLater(() -> {
            try {
                FXMLLoader serverConnectionFxml = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/server-connect.fxml"));
                Parent root = serverConnectionFxml.load();
                ServerConnectController controller = serverConnectionFxml.getController();

                Stage mainStage = (Stage) mainPane.getScene().getWindow();
                // Add the server connection content on top of the existing children
                mainPane.getChildren().add(root);
                controller.setMainPane(mainPane);
                controller.setLoadingController(this); // Pass the LoadingController instance

                // Center the content in the mainPane
                root.setLayoutX((mainPane.getWidth() - root.prefWidth(-1)) / 2);
                root.setLayoutY((mainPane.getHeight() - root.prefHeight(-1)) / 2);

                // Set the close request handler on the primary stage
//                mainStage.setOnCloseRequest(event -> {
//                    System.exit(0);
//                    System.out.println("Shutting down server...");
//                    dataLoaderThread.interrupt();
//                    printActiveThreads();
//                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void playDataManagerTimeline() {
        if (dataManagerTimeline[0] != null) {
            dataManagerTimeline[0].play();
        }
    }


    public void printActiveThreads() {
        // Get the current thread group
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        // Get the number of active threads in the current thread group
        int noThreads = currentGroup.activeCount();
        // Create an array to hold the active threads
        Thread[] lstThreads = new Thread[noThreads];
        // Populate the array with the active threads
        currentGroup.enumerate(lstThreads);

        // Print the active threads and their states
        System.out.println("Active threads:");
        for (Thread thread : lstThreads) {
            if (thread != null) {
                System.out.println("Thread name: " + thread.getName() + ", State: " + thread.getState());
            }
        }
    }




    static class InternetConnectionChecker {
        public static boolean isInternetAvailable() {
            try {
                InetAddress address = InetAddress.getByName("google.com");
                return address.isReachable(2000); // Timeout in milliseconds
            } catch (IOException e) {
                return false;
            }
        }
    }
}