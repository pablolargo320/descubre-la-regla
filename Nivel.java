package poli.edu.co.controlador;

import java.io.IOException;
import javafx.fxml.FXML;
import poli.edu.co.vista.App;

/**
 * Controlador del menú principal ({@code primary.fxml}).
 *
 * <p>Responsabilidad única: gestionar la navegación entre la pantalla
 * de inicio y las pantallas de nivel o reglas.
 * No contiene lógica de negocio.</p>
 */
public class MenuControlador {

    /**
     * Navega a la pantalla de selección de nivel.
     *
     * @throws IOException si no se puede cargar {@code Nivel.fxml}.
     */
    @FXML
    private void iniciarPartida() throws IOException {
        App.setRoot("Nivel");
    }

    /**
     * Navega a la pantalla de reglas del juego.
     *
     * @throws IOException si no se puede cargar {@code secondary.fxml}.
     */
    @FXML
    private void abrirReglas() throws IOException {
        App.setRoot("secondary");
    }
}
