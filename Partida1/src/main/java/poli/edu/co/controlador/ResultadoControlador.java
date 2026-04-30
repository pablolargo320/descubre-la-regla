package poli.edu.co.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import poli.edu.co.App;
import poli.edu.co.modelo.Jugador;
import poli.edu.co.modelo.Partida;

import java.io.IOException;

/**
 * Controlador de la pantalla de resultado.
 * Muestra el resultado final, solicita el nombre del jugador y gestiona el guardado.
 *
 * Responsabilidades (MVC):
 *  - Construir los mensajes visuales a partir del estado del modelo.
 *  - Validar la entrada del usuario antes de pasar datos al modelo.
 *  - Crear la entidad Jugador en el controlador (new aquí, no en el modelo).
 *  - Delegar el guardado al modelo vía inyección del objeto ya construido.
 *
 * Escenarios BDD cubiertos:
 *  - Escenario 1: muestra campo de nombre al cargar la pantalla.
 *  - Escenario 2: guarda el puntaje al presionar "Guardar".
 *  - Escenario 3: muestra "Debe ingresar un nombre" si el campo está vacío.
 *  - Escenario 4: permite el flujo normal con nombre válido.
 */
public class ResultadoControlador {

    @FXML private Label     resultadoLabel;
    @FXML private Label     nivelLabel;
    @FXML private Label     puntajeLabel;

    @FXML private TextField nombreField;       // Escenario 1
    @FXML private Label     errorNombreLabel;  // Escenario 3
    @FXML private Button    btnGuardar;        // Escenario 2
    @FXML private Label     confirmacionLabel;

    private Partida partida;

    // ── Inicialización ───────────────────────────────────────────────────────

    @FXML
    private void initialize() {
        partida = App.getPartida();
        mostrarResultado();
    }

    // ── Actualización de vista ───────────────────────────────────────────────

    private void mostrarResultado() {
        boolean gano = partida.isGanada();

        // Texto y estilo visual: construidos en el controlador
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

        // Datos provenientes del modelo (sin formato propio)
        nivelLabel.setText("Nivel jugado: " + partida.getNivel().name());
        puntajeLabel.setText(partida.getPuntaje().toString());

        errorNombreLabel.setVisible(false);
        errorNombreLabel.setManaged(false);
        confirmacionLabel.setVisible(false);
        confirmacionLabel.setManaged(false);
    }

    // ── Eventos ──────────────────────────────────────────────────────────────

    /**
     * Escenarios 2, 3 y 4: valida el nombre, crea la entidad Jugador
     * en el controlador (new aquí) y delega el guardado al modelo.
     *
     * Nota de arquitectura:
     *  'new Jugador(...)' está AQUÍ (en el controlador), no en Partida.
     *  El modelo recibe el objeto ya construido mediante guardarJugador(Jugador).
     */
    @FXML
    private void guardar() {
        String nombre = nombreField.getText().trim();

        // Escenario 3: nombre vacío
        if (nombre.isBlank()) {
            errorNombreLabel.setText("Debe ingresar un nombre");
            errorNombreLabel.setVisible(true);
            errorNombreLabel.setManaged(true);
            return;
        }

        errorNombreLabel.setVisible(false);
        errorNombreLabel.setManaged(false);

        try {
            // Creación de la entidad en el controlador (new aquí, no en el modelo)
            Jugador jugador = new Jugador(               // ← new en el controlador
                nombre,
                partida.getPuntaje().getValor(),
                partida.getNivel().name()
            );
            partida.guardarJugador(jugador);             // modelo recibe el objeto ya construido

            confirmacionLabel.setVisible(true);
            confirmacionLabel.setManaged(true);
            btnGuardar.setDisable(true);                 // evitar doble guardado
            nombreField.setDisable(true);
        } catch (RuntimeException e) {
            errorNombreLabel.setText("Error al guardar. Intente de nuevo.");
            errorNombreLabel.setVisible(true);
            errorNombreLabel.setManaged(true);
        }
    }

    @FXML
    private void nuevaPartida() throws IOException {
        App.setRoot("Nivel");
    }

    @FXML
    private void volverMenu() throws IOException {
        App.setRoot("primary");
    }
}
