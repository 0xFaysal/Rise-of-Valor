module game.rise_of_valor {
    requires javafx.controls;
    requires javafx.fxml;


    opens game.rise_of_valor to javafx.fxml;
    exports game.rise_of_valor;
}