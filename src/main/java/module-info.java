module game.rise_of_valor {
    requires javafx.controls;
    requires javafx.fxml;
<<<<<<< HEAD
    requires java.desktop;
    requires javafx.swing;
=======
    requires javafx.media;
>>>>>>> b00ba38 (deleted code re written for the game logo scene, login-registration page image changed.)

    opens game.rise_of_valor.controllers to javafx.fxml;
    exports game.rise_of_valor;
    exports game.rise_of_valor.game_engine;
    opens game.rise_of_valor.game_engine to javafx.fxml;
}