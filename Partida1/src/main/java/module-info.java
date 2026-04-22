module poli.edu.co {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.desktop;
    requires java.logging;

    opens poli.edu.co to javafx.fxml;
    opens poli.edu.co.controlador to javafx.fxml;
    opens poli.edu.co.modelo to javafx.fxml;

    exports poli.edu.co;
    exports poli.edu.co.modelo;
    exports poli.edu.co.controlador;
}
