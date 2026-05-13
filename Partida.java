package poli.edu.co.controlador;

import javafx.fxml.FXML;
import java.io.IOException;
import poli.edu.co.modelo.Nivel;
import poli.edu.co.modelo.Regla;
import poli.edu.co.vista.App;

/**
 * Controlador de la pantalla de selección de nivel ({@code Nivel.fxml}).
 *
 * <p>Responsabilidades:</p>
 * <ul>
 *   <li>Capturar el nivel elegido por el jugador.</li>
 *   <li>Crear la instancia de {@link Regla} asociada al nivel
 *       (el {@code new} ocurre aquí, no en el modelo).</li>
 *   <li>Inicializar la partida compartida con el nivel y la regla.</li>
 *   <li>Navegar a la pantalla de juego.</li>
 * </ul>
 *
 * <p>No contiene lógica de negocio: delega todo en el modelo.</p>
 */
public class NivelControlador {

    /**
     * Selecciona el nivel Fácil y comienza la partida.
     *
     * @throws IOException si no se puede cargar {@code Juego.fxml}.
     */
    @FXML
    private void seleccionarFacil() throws IOException {
        seleccionar(Nivel.FACIL);
    }

    /**
     * Selecciona el nivel Intermedio y comienza la partida.
     *
     * @throws IOException si no se puede cargar {@code Juego.fxml}.
     */
    @FXML
    private void seleccionarIntermedio() throws IOException {
        seleccionar(Nivel.INTERMEDIO);
    }

    /**
     * Selecciona el nivel Difícil y comienza la partida.
     *
     * @throws IOException si no se puede cargar {@code Juego.fxml}.
     */
    @FXML
    private void seleccionarDificil() throws IOException {
        seleccionar(Nivel.DIFICIL);
    }

    /**
     * Navega de regreso al menú principal.
     *
     * @throws IOException si no se puede cargar {@code primary.fxml}.
     */
    @FXML
    private void volverMenu() throws IOException {
        App.setRoot("primary");
    }

    /**
     * Crea la {@link Regla} para el nivel dado, inicializa la partida
     * compartida y navega a la pantalla de juego.
     *
     * @param nivel nivel de dificultad elegido por el jugador.
     * @throws IOException si no se puede cargar {@code Juego.fxml}.
     */
    private void seleccionar(Nivel nivel) throws IOException {
        Regla regla = new Regla(nivel);
        App.getPartida().iniciarConNivel(nivel, regla);
        App.setRoot("Juego");
    }
}
