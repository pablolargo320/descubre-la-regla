package poli.edu.co.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import poli.edu.co.App;
import poli.edu.co.modelo.Partida;

import java.io.IOException;

/**
 * Controlador de la pantalla de juego.
 *
 * Responsabilidades (MVC — capa Controlador):
 *  - Capturar acciones del usuario (botones, campos de texto).
 *  - Delegar la lógica al modelo ({@link Partida}).
 *  - Actualizar la vista con los resultados recibidos.
 *
 * Principio: NO contiene lógica de negocio ni cálculos.
 * Los mensajes de UI se construyen aquí a partir del estado del modelo.
 */
public class JuegoControlador {

    // ── Fase 1: Exploración ──────────────────────────────────────────────────
    @FXML private VBox      exploracionPane;
    @FXML private TextField inputUsuario;
    @FXML private Label     outputSistema;
    @FXML private Label     errorInputLabel;        // Validación: campo no numérico
    @FXML private Label     mensajeExploracionLabel;
    @FXML private Button    btnYaConozcoLaRegla;

    // ── Fase 2: Validación ───────────────────────────────────────────────────
    @FXML private VBox      validacionPane;
    @FXML private Label     intentosLabel;
    @FXML private Label     mensajeValidacionLabel;
    @FXML private Label     n1Label, n2Label, n3Label, n4Label;
    @FXML private TextField r1Field, r2Field, r3Field, r4Field;
    @FXML private TextField reglaField;
    @FXML private Button    btnVolverExplorar;

    // ── Global ───────────────────────────────────────────────────────────────
    @FXML private Button btnVolverMenu;

    private Partida partida;

    // ── Inicialización ───────────────────────────────────────────────────────

    @FXML
    private void initialize() {
        partida = App.getPartida();
        // Mensajes iniciales: responsabilidad del controlador, no del modelo
        outputSistema.setText("");
        mensajeExploracionLabel.setText(
            "Ingresa números enteros y observa la salida del sistema.");
        mensajeValidacionLabel.setText("");
        errorInputLabel.setVisible(false);
        errorInputLabel.setManaged(false);
        actualizarPaneles();
    }

    // ── Sincronización estructural de la vista ───────────────────────────────

    /**
     * Actualiza la visibilidad de paneles y etiquetas de estado
     * basándose en el estado del modelo.
     * No genera mensajes propios: eso lo hacen los métodos de evento.
     */
    private void actualizarPaneles() {
        boolean enValidacion = partida.isModoValidacion();

        exploracionPane.setVisible(!enValidacion);
        exploracionPane.setManaged(!enValidacion);
        validacionPane.setVisible(enValidacion);
        validacionPane.setManaged(enValidacion);

        btnYaConozcoLaRegla.setVisible(!enValidacion);
        btnYaConozcoLaRegla.setManaged(!enValidacion);
        btnVolverExplorar.setVisible(enValidacion);
        btnVolverExplorar.setManaged(enValidacion);

        if (enValidacion) {
            intentosLabel.setText("Intentos restantes: " + partida.getIntentosRestantes());
            int[] entradas = partida.getEntradasReto();
            n1Label.setText(String.valueOf(entradas[0]));
            n2Label.setText(String.valueOf(entradas[1]));
            n3Label.setText(String.valueOf(entradas[2]));
            n4Label.setText(String.valueOf(entradas[3]));
        }
    }

    // ── Eventos: Fase 1 ──────────────────────────────────────────────────────

    @FXML
    private void probarNumero() {
        String texto = inputUsuario.getText().trim();

        // Validación de entrada: campo vacío o no numérico
        if (texto.isEmpty()) {
            mostrarErrorInput("Debes ingresar un número.");
            return;
        }

        try {
            int n   = Integer.parseInt(texto);
            int res = partida.aplicarRegla(n);                        // lógica en modelo

            // Entrada válida: ocultar error, mostrar resultado
            ocultarErrorInput();
            outputSistema.setText("f(" + n + ")  =  " + res);        // texto en controlador
            mensajeExploracionLabel.setText(
                "Sigue probando o pasa a la fase de validación.");

        } catch (NumberFormatException e) {
            mostrarErrorInput("Debes ingresar un número entero válido.");
        }
    }

    /** Muestra el label de error de entrada con el mensaje indicado. */
    private void mostrarErrorInput(String mensaje) {
        errorInputLabel.setText(mensaje);
        errorInputLabel.setVisible(true);
        errorInputLabel.setManaged(true);
        outputSistema.setText("");
    }

    /** Oculta el label de error de entrada. */
    private void ocultarErrorInput() {
        errorInputLabel.setVisible(false);
        errorInputLabel.setManaged(false);
    }

    @FXML
    private void yaConozcoLaRegla() {
        partida.iniciarModoValidacion();                               // lógica en modelo
        actualizarPaneles();
        mensajeValidacionLabel.setText(
            "Da las 4 salidas correctas o escribe la regla. "
            + "Intentos: " + partida.getIntentosRestantes());
    }

    // ── Eventos: Fase 2 ──────────────────────────────────────────────────────

    @FXML
    private void validar() throws IOException {
        if (partida.isTerminada()) return;

        // Opción A: el jugador escribió la expresión de la regla
        if (partida.verificarExpresion(reglaField.getText())) {
            partida.ganar();                                           // lógica en modelo
            App.setRoot("Resultado");
            return;
        }

        // Opción B: el jugador ingresó las 4 salidas numéricas
        try {
            int[] respuestas = {
                Integer.parseInt(r1Field.getText().trim()),
                Integer.parseInt(r2Field.getText().trim()),
                Integer.parseInt(r3Field.getText().trim()),
                Integer.parseInt(r4Field.getText().trim())
            };

            if (partida.verificarSalidas(respuestas)) {
                partida.ganar();                                       // lógica en modelo
                App.setRoot("Resultado");
            } else {
                boolean perdio = partida.registrarIntentoFallido();    // lógica en modelo
                if (perdio) {
                    App.setRoot("Resultado");
                } else {
                    // Construye el mensaje en el controlador con datos del modelo
                    mensajeValidacionLabel.setText(
                        "Incorrecto. Intentos restantes: " + partida.getIntentosRestantes());
                    intentosLabel.setText(
                        "Intentos restantes: " + partida.getIntentosRestantes());
                }
            }
        } catch (NumberFormatException e) {
            mensajeValidacionLabel.setText(
                "⚠  Completa todos los campos con números enteros.");
        }
    }

    @FXML
    private void volverExplorar() {
        partida.volverExploracion();                                   // lógica en modelo
        ocultarErrorInput();
        actualizarPaneles();
        mensajeExploracionLabel.setText("Sigue probando para encontrar más pistas.");
    }

    // ── Eventos: Global ──────────────────────────────────────────────────────

    @FXML
    private void volverMenu() throws IOException {
        App.setRoot("primary");
    }
}
