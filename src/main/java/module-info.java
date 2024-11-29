module game.rise_of_valor {
    requires javafx.controls;
    requires javafx.fxml;

    opens game.rise_of_valor.controllers to javafx.fxml;
    exports game.rise_of_valor;
}