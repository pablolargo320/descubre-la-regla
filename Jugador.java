package poli.edu.co.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import poli.edu.co.modelo.Partida;
import poli.edu.co.vista.App;

import java.io.IOException;

/**
 * Controlador de la pantalla principal de juego ({@code Juego.fxml}).
 *
 * <p>Gestiona las dos fases del juego:</p>
 * <ul>
 *   <li><strong>Fase 1 — Exploración:</strong> el jugador ingresa números
 *       y observa la salida del sistema para descubrir el patrón.</li>
 *   <li><strong>Fase 2 — Validación:</strong> el jugador propone las
 *       4 salidas del reto o escribe la regla directamente (máx. 3 intentos).</li>
 * </ul>
 *
 * <p>Principio de diseño: este controlador <strong>nunca</strong> contiene
 * lógica de negocio; solo traduce eventos de UI en llamadas al modelo
 * ({@link Partida}) y actualiza la vista con el estado resultante.</p>
 */
public class JuegoControlador {

    // ── Fase 1: Exploración ───────────────────────────────────────────────────

    /** Panel contenedor de la fase de exploración. */
    @FXML private VBox      exploracionPane;

    /** Campo donde el jugador escribe el número a probar. */
    @FXML private TextField inputUsuario;

    /** Label que muestra el resultado de la función (ej.: "f(3) = 6"). */
    @FXML private Label     outputSistema;

    /** Label de error cuando el input no es un número válido. */
    @FXML private Label     errorInputLabel;

    /** Label informativo sobre la fase de exploración. */
    @FXML private Label     mensajeExploracionLabel;

    /** Botón para pasar a la fase de validación. */
    @FXML private Button    btnYaConozcoLaRegla;

    // ── Fase 2: Validación ────────────────────────────────────────────────────

    /** Panel contenedor de la fase de validación. */
    @FXML private VBox      validacionPane;

    /** Label que muestra los intentos restantes. */
    @FXML private Label     intentosLabel;

    /** Label con mensajes de resultado de cada intento. */
    @FXML private Label     mensajeValidacionLabel;

    /** Labels con los 4 números de entrada del reto. */
    @FXML private Label     n1Label, n2Label, n3Label, n4Label;

    /** Campos donde el jugador escribe las 4 salidas propuestas. */
    @FXML private TextField r1Field, r2Field, r3Field, r4Field;

    /** Campo para escribir la regla directamente (ej.: "x * 2"). */
    @FXML private TextField reglaField;

    /** Botón para volver a la fase de exploración. */
    @FXML private Button    btnVolverExplorar;

    // ── Global ────────────────────────────────────────────────────────────────

    /** Botón para abandonar y volver al menú principal. */
    @FXML private Button    btnVolverMenu;

    /** Referencia al modelo compartido de la partida activa. */
    private Partida partida;

    // ── Inicialización ────────────────────────────────────────────────────────

    /**
     * Inicializa el controlador cuando JavaFX carga el FXML.
     * Obtiene la partida compartida, oculta mensajes de error
     * y actualiza los paneles según el modo actual.
     */
    @FXML
    private void initialize() {
        partida = App.getPartida();

        outputSistema.setText("");
        mensajeExploracionLabel.setText(
            "Ingresa números enteros y observa la salida del sistema.");
        mensajeValidacionLabel.setText("");

        errorInputLabel.setVisible(false);
        errorInputLabel.setManaged(false);

        actualizarPaneles();
    }

    // ── Sincronización de vista ───────────────────────────────────────────────

    /**
     * Muestra u oculta los paneles de exploración y validación
     * según el estado actual de la partida.
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

    // ── Eventos: Fase 1 ───────────────────────────────────────────────────────

    /**
     * Aplica la regla al número ingresado y muestra el resultado.
     * Muestra un mensaje de error si el input no es un entero válido.
     */
    @FXML
    private void probarNumero() {
        String texto = inputUsuario.getText().trim();

        if (texto.isEmpty()) {
            mostrarErrorInput("Debes ingresar un número.");
            return;
        }

        try {
            int n   = Integer.parseInt(texto);
            int res = partida.aplicarRegla(n);

            ocultarErrorInput();
            outputSistema.setText("f(" + n + ")  =  " + res);
            mensajeExploracionLabel.setText("Sigue probando o pasa a la fase de validación.");

        } catch (NumberFormatException e) {
            mostrarErrorInput("Debes ingresar un número entero válido.");
        }
    }

    /** Muestra un mensaje de error bajo el campo de entrada. */
    private void mostrarErrorInput(String mensaje) {
        errorInputLabel.setText(mensaje);
        errorInputLabel.setVisible(true);
        errorInputLabel.setManaged(true);
        outputSistema.setText("");
    }

    /** Oculta el label de error del campo de entrada. */
    private void ocultarErrorInput() {
        errorInputLabel.setVisible(false);
        errorInputLabel.setManaged(false);
    }

    /**
     * Pasa al modo validación e inicializa el reto de 4 números.
     */
    @FXML
    private void yaConozcoLaRegla() {
        partida.iniciarModoValidacion();
        actualizarPaneles();
        mensajeValidacionLabel.setText(
            "Da las 4 salidas correctas o escribe la regla. "
            + "Intentos: " + partida.getIntentosRestantes());
    }

    // ── Eventos: Fase 2 ───────────────────────────────────────────────────────

    /**
     * Valida la respuesta del jugador (regla en texto o 4 salidas numéricas).
     *
     * <ol>
     *   <li>Si el campo de regla no está vacío y es correcto → victoria.</li>
     *   <li>Si las 4 salidas son correctas → victoria.</li>
     *   <li>Si son incorrectas → registra intento fallido;
     *       si los intentos se agotan → navega a Resultado.</li>
     * </ol>
     *
     * @throws IOException si no se puede cargar {@code Resultado.fxml}.
     */
    @FXML
    private void validar() throws IOException {
        if (partida.isTerminada()) return;

        if (partida.verificarExpresion(reglaField.getText())) {
            partida.ganar();
            App.setRoot("Resultado");
            return;
        }

        try {
            int[] respuestas = {
                Integer.parseInt(r1Field.getText().trim()),
                Integer.parseInt(r2Field.getText().trim()),
                Integer.parseInt(r3Field.getText().trim()),
                Integer.parseInt(r4Field.getText().trim())
            };

            if (partida.verificarSalidas(respuestas)) {
                partida.ganar();
                App.setRoot("Resultado");
            } else {
                boolean perdio = partida.registrarIntentoFallido();
                if (perdio) {
                    App.setRoot("Resultado");
                } else {
                    mensajeValidacionLabel.setText(
                        "Incorrecto. Intentos restantes: " + partida.getIntentosRestantes());
                    intentosLabel.setText(
                        "Intentos restantes: " + partida.getIntentosRestantes());
                }
            }

        } catch (NumberFormatException e) {
            mensajeValidacionLabel.setText("⚠  Completa todos los campos con números enteros.");
        }
    }

    /**
     * Regresa al modo exploración sin reiniciar los intentos.
     */
    @FXML
    private void volverExplorar() {
        partida.volverExploracion();
        ocultarErrorInput();
        actualizarPaneles();
        mensajeExploracionLabel.setText("Sigue probando para encontrar más pistas.");
    }

    // ── Eventos: Global ───────────────────────────────────────────────────────

    /**
     * Abandona la partida y regresa al menú principal.
     *
     * @throws IOException si no se puede cargar {@code primary.fxml}.
     */
    @FXML
    private void volverMenu() throws IOException {
        App.setRoot("primary");
    }
}
