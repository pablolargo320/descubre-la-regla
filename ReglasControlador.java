/**
 * Módulo principal del juego "Adivina la Regla".
 *
 * <p>Requiere JavaFX para la interfaz gráfica y java.sql para la
 * persistencia con SQLite. Los paquetes de vista, controlador y modelo
 * se abren a javafx.fxml para permitir la reflexión del FXMLLoader.</p>
 */
module poli.edu.co {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens poli.edu.co.vista       to javafx.fxml, javafx.graphics;
    opens poli.edu.co.controlador to javafx.fxml;
    opens poli.edu.co.modelo      to javafx.fxml;

    exports poli.edu.co.vista;
    exports poli.edu.co.modelo;
    exports poli.edu.co.modelo.dao;
    exports poli.edu.co.controlador;
}
