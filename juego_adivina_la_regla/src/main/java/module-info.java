module com.mycompany.partida1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.partida1 to javafx.fxml;
    exports com.mycompany.partida1;
}
