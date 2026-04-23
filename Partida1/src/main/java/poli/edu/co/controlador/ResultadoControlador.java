package poli.edu.co.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import poli.edu.co.App;
import poli.edu.co.modelo.JuegoModelo;

import java.io.IOException;

/**
 * Controlador de la pantalla de resultado.
 * Muestra puntaje y nivel, solicita el nombre del jugador y gestiona el guardado.
 * No contiene lógica de negocio: delega todo a JuegoModelo.
 *
 * Escenarios BDD cubiertos:
 *  - Escenario 1: muestra campo de nombre al cargar la pantalla.
 *  - Escenario 2: guarda el puntaje al presionar "Guardar".
 *  - Escenario 3: muestra "Debe ingresar un nombre" si el campo está vacío.
 *  - Escenario 4: permite el flujo normal con nombre válido.
 */
public class ResultadoControlador {

    // ── Vista ────────────────────────────────────────────────────────────────
    @FXML private Label     resultadoLabel;
    @FXML private Label     nivelLabel;
    @FXML private Label     puntajeLabel;

    @FXML private TextField nombreField;          // Escenario 1
    @FXML private Label     errorNombreLabel;     // Escenario 3
    @FXML private Button    btnGuardar;           // Escenario 2
    @FXML private Label     confirmacionLabel;    // feedback post-guardado

    private JuegoModelo modelo;

    // ── Inicialización ───────────────────────────────────────────────────────

    @FXML
    private void initialize() {
        modelo = App.getModelo();
        mostrarResultado();
    }

    // ── Sincronización de vista ──────────────────────────────────────────────

    private void mostrarResultado() {
        boolean gano = modelo.isUltimaPartidaGanada();

        // Resultado textual y color según victoria/derrota
        if (gano) {
            resultadoLabel.setText("¡GANASTE!");
            resultadoLabel.setStyle(
                "-fx-text-fill: #4caf50; -fx-font-size: 28px; -fx-font-weight: bold;");
        } else {
            resultadoLabel.setText("PERDISTE");
            resultadoLabel.setStyle(
                "-fx-text-fill: #ef5350; -fx-font-size: 28px; -fx-font-weight: bold;");
        }

        nivelLabel.setText("Nivel jugado: " + modelo.getNivelActual().name());
        puntajeLabel.setText("Puntaje: " + modelo.getPuntaje());   // usa Puntaje.toString()

        errorNombreLabel.setVisible(false);
        errorNombreLabel.setManaged(false);
        confirmacionLabel.setVisible(false);
        confirmacionLabel.setManaged(false);
    }

    // ── Eventos ──────────────────────────────────────────────────────────────

    /**
     * Escenario 2 y 3: valida el nombre e intenta guardar.
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

        // Escenario 4 y 2: nombre válido → guardar
        errorNombreLabel.setVisible(false);
        errorNombreLabel.setManaged(false);

        try {
            modelo.guardarJugador(nombre);           // lógica en el modelo
            confirmacionLabel.setVisible(true);
            confirmacionLabel.setManaged(true);
            btnGuardar.setDisable(true);             // evitar doble guardado
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
