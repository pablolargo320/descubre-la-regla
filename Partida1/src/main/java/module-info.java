module poli.edu.co {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.desktop;
    requires java.logging;
    requires java.sql;          // JDBC API para la capa DAO

    opens poli.edu.co             to javafx.fxml;
    opens poli.edu.co.controlador to javafx.fxml;
    opens poli.edu.co.modelo      to javafx.fxml;

    exports poli.edu.co;
    exports poli.edu.co.modelo;
    exports poli.edu.co.modelo.dao;
    exports poli.edu.co.controlador;
}
