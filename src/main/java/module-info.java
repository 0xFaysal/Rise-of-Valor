module game.rise_of_valor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires javafx.swing;



    opens game.rise_of_valor.controllers to javafx.fxml;
    exports game.rise_of_valor;
    exports game.rise_of_valor.game_engine;
    opens game.rise_of_valor.game_engine to javafx.fxml;
}