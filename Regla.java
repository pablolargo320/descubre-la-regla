package poli.edu.co.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import poli.edu.co.modelo.Jugador;
import poli.edu.co.modelo.Partida;
import poli.edu.co.vista.App;

import java.io.IOException;

/**
 * Controlador de la pantalla de resultado ({@code Resultado.fxml}).
 *
 * <p>Responsabilidades:</p>
 * <ul>
 *   <li>Mostrar el resultado final (ganó/perdió), el nivel y el puntaje.</li>
 *   <li>Validar el nombre ingresado por el jugador.</li>
 *   <li>Construir el objeto {@link Jugador} (el {@code new} ocurre aquí)
 *       y delegarlo a {@code Partida#guardarJugador(Jugador)}.</li>
 *   <li>Gestionar la navegación de fin de partida.</li>
 * </ul>
 *
 * <p>El controlador no accede al DAO directamente: la persistencia
 * se delega al modelo ({@code Partida}) que a su vez usa el DAO inyectado.</p>
 */
public class ResultadoControlador {

    // ── Vista ─────────────────────────────────────────────────────────────────

    /** Label que muestra "¡GANASTE!" o "PERDISTE" con color. */
    @FXML private Label     resultadoLabel;

    /** Label con el nombre del nivel jugado. */
    @FXML private Label     nivelLabel;

    /** Label con el puntaje obtenido. */
    @FXML private Label     puntajeLabel;

    /** Campo de texto para que el jugador ingrese su nombre. */
    @FXML private TextField nombreField;

    /** Label de error cuando el nombre está vacío o hay error al guardar. */
    @FXML private Label     errorNombreLabel;

    /** Botón para guardar el puntaje; se deshabilita tras el primer guardado. */
    @FXML private Button    btnGuardar;

    /** Label de confirmación visible tras guardar exitosamente. */
    @FXML private Label     confirmacionLabel;

    /** Referencia al modelo compartido de la partida. */
    private Partida partida;

    // ── Inicialización ────────────────────────────────────────────────────────

    /**
     * Inicializa el controlador cuando JavaFX carga el FXML.
     * Obtiene la partida compartida y muestra los datos del resultado.
     */
    @FXML
    private void initialize() {
        partida = App.getPartida();
        mostrarResultado();
    }

    // ── Sincronización de vista ───────────────────────────────────────────────

    /**
     * Muestra el resultado final, nivel y puntaje obtenidos.
     * Oculta los labels de error y confirmación inicialmente.
     */
    private void mostrarResultado() {
        boolean gano = partida.isGanada();

        if (gano) {
            resultadoLabel.setText("¡GANASTE!");
            resultadoLabel.setStyle(
                "-fx-text-fill: #3fb950; -fx-font-size: 30px; -fx-font-weight: bold;"
                + "-fx-font-family: 'Impact', 'Arial Black';");
        } else {
            resultadoLabel.setText("PERDISTE");
            resultadoLabel.setStyle(
                "-fx-text-fill: #f85149; -fx-font-size: 30px; -fx-font-weight: bold;"
                + "-fx-font-family: 'Impact', 'Arial Black';");
        }

        nivelLabel.setText("Nivel jugado: " + partida.getNivel().name());
        puntajeLabel.setText(partida.getPuntaje().toString());

        errorNombreLabel.setVisible(false);
        errorNombreLabel.setManaged(false);
        confirmacionLabel.setVisible(false);
        confirmacionLabel.setManaged(false);
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    /**
     * Valida el nombre, crea el {@link Jugador} y lo persiste a través del modelo.
     *
     * <p>El {@code new Jugador(...)} ocurre aquí (en el controlador),
     * no en el modelo, cumpliendo con el principio de inyección de dependencias.</p>
     */
    @FXML
    private void guardar() {
        String nombre = nombreField.getText().trim();

        if (nombre.isBlank()) {
            mostrarError("Debe ingresar un nombre.");
            return;
        }

        ocultarError();

        try {
            Jugador jugador = new Jugador(
                nombre,
                partida.getPuntaje().getValor(),
                partida.getNivel().name()
            );
            partida.guardarJugador(jugador);

            confirmacionLabel.setVisible(true);
            confirmacionLabel.setManaged(true);
            btnGuardar.setDisable(true);
            nombreField.setDisable(true);

        } catch (RuntimeException e) {
            mostrarError("Error al guardar. Intente de nuevo.");
        }
    }

    private void mostrarError(String mensaje) {
        errorNombreLabel.setText(mensaje);
        errorNombreLabel.setVisible(true);
        errorNombreLabel.setManaged(true);
    }

    private void ocultarError() {
        errorNombreLabel.setVisible(false);
        errorNombreLabel.setManaged(false);
    }

    /**
     * Navega a la pantalla de selección de nivel para una nueva partida.
     *
     * @throws IOException si no se puede cargar {@code Nivel.fxml}.
     */
    @FXML
    private void nuevaPartida() throws IOException {
        App.setRoot("Nivel");
    }

    /**
     * Navega al menú principal.
     *
     * @throws IOException si no se puede cargar {@code primary.fxml}.
     */
    @FXML
    private void volverMenu() throws IOException {
        App.setRoot("primary");
    }
}
