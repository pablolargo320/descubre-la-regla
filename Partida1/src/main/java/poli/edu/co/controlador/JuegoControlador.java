package poli.edu.co.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import poli.edu.co.App;
import poli.edu.co.modelo.JuegoModelo;

import java.io.IOException;

/**
 * Controlador de la pantalla de juego.
 * Solo gestiona eventos de UI y delega toda la lógica a JuegoModelo.
 * Cuando la partida termina (victoria o derrota), navega a Resultado.fxml.
 */
public class JuegoControlador {

    // ── Fase 1: Exploración ──────────────────────────────────────────────────
    @FXML private TextField inputUsuario;
    @FXML private Label     outputSistema;
    @FXML private Label     mensajeExploracionLabel;
    @FXML private Button    btnYaConozcoLaRegla;
    @FXML private VBox      exploracionPane;

    // ── Fase 2: Validación ───────────────────────────────────────────────────
    @FXML private TextField reglaField;
    @FXML private Label     intentosLabel;
    @FXML private Label     mensajeValidacionLabel;
    @FXML private Label     n1Label, n2Label, n3Label, n4Label;
    @FXML private TextField r1Field,  r2Field,  r3Field,  r4Field;
    @FXML private Button    btnVolverExplorar;
    @FXML private VBox      validacionPane;

    // ── Global ───────────────────────────────────────────────────────────────
    @FXML private Button btnVolverMenu;

    private JuegoModelo modelo;

    // ── Inicialización ───────────────────────────────────────────────────────

    @FXML
    private void initialize() {
        modelo = App.getModelo();
        actualizarVista();
    }

    // ── Sincronización de vista ──────────────────────────────────────────────

    private void actualizarVista() {
        boolean enValidacion = modelo.isModoValidacion();

        exploracionPane.setVisible(!enValidacion);
        exploracionPane.setManaged(!enValidacion);
        validacionPane.setVisible(enValidacion);
        validacionPane.setManaged(enValidacion);

        outputSistema.setText(modelo.getUltimaSalida());
        mensajeExploracionLabel.setText(modelo.getMensajeExploracion());
        intentosLabel.setText("Intentos restantes: " + modelo.getIntentosRestantes());
        mensajeValidacionLabel.setText(modelo.getMensajeValidacion());

        int[] entradas = modelo.getEntradasReto();
        n1Label.setText(String.valueOf(entradas[0]));
        n2Label.setText(String.valueOf(entradas[1]));
        n3Label.setText(String.valueOf(entradas[2]));
        n4Label.setText(String.valueOf(entradas[3]));

        btnVolverExplorar.setVisible(enValidacion);
        btnVolverExplorar.setManaged(enValidacion);
        btnYaConozcoLaRegla.setVisible(!enValidacion);
        btnYaConozcoLaRegla.setManaged(!enValidacion);
    }

    // ── Eventos: Fase 1 ──────────────────────────────────────────────────────

    @FXML
    private void probarNumero() {
        try {
            int n   = Integer.parseInt(inputUsuario.getText().trim());
            int res = modelo.aplicarRegla(n);
            modelo.setUltimaSalida("Salida: " + res);
            actualizarVista();
        } catch (NumberFormatException e) {
            mensajeExploracionLabel.setText("Número inválido. Ingresa un entero.");
        }
    }

    @FXML
    private void yaConozcoLaRegla() {
        modelo.iniciarModoValidacion();
        actualizarVista();
    }

    // ── Eventos: Fase 2 ──────────────────────────────────────────────────────

    @FXML
    private void validar() throws IOException {
        if (modelo.isTerminado()) return;

        // Opción A: el jugador escribió la regla en texto
        if (modelo.verificarTextoRegla(reglaField.getText())) {
            modelo.ganar();
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

            if (modelo.verificarSalidas(respuestas)) {
                modelo.ganar();
                App.setRoot("Resultado");
            } else {
                modelo.registrarIntentoFallido();
                if (modelo.isTerminado()) {
                    App.setRoot("Resultado");   // perdió → ir a pantalla de resultado
                } else {
                    actualizarVista();          // aún quedan intentos
                }
            }

        } catch (NumberFormatException e) {
            mensajeValidacionLabel.setText("Completa todos los campos con números enteros.");
        }
    }

    @FXML
    private void volverExplorar() {
        modelo.volverExploracion();
        actualizarVista();
    }

    // ── Eventos: Global ──────────────────────────────────────────────────────

    @FXML
    private void volverMenu() throws IOException {
        App.setRoot("primary");
    }
}
