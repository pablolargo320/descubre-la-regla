package poli.educo;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private void abrirVentanaReglas() {

        // Crear nueva ventana
        Stage ventana = new Stage();
        ventana.setTitle("Reglas del juego");

        // Ventana vacía por ahora
        Pane root = new Pane();
        Scene scene = new Scene(root, 400, 300);

        ventana.setScene(scene);
        ventana.show();
    }
}