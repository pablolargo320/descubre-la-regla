package poli.edu.co.controlador;

import java.io.IOException;
import javafx.fxml.FXML;
import poli.edu.co.vista.App;

/**
 * Controlador de la pantalla de reglas del juego ({@code secondary.fxml}).
 *
 * <p>Responsabilidad única: permitir volver al menú principal.
 * No contiene lógica de negocio ni acceso al modelo.</p>
 */
public class ReglasControlador {

    /**
     * Navega de regreso al menú principal.
     *
     * @throws IOException si no se puede cargar {@code primary.fxml}.
     */
    @FXML
    private void volverMenu() throws IOException {
        App.setRoot("primary");
    }
}
