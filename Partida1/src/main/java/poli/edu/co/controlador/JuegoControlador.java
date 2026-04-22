package poli.edu.co.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import poli.edu.co.App;
import poli.edu.co.modelo.JuegoModelo;

import java.io.IOException;

public class JuegoControlador {

    @FXML private TextField inputUsuario;
    @FXML private Label     outputSistema;
    @FXML private Label     mensajeExploracionLabel;

    @FXML private TextField reglaField;
    @FXML private Label     intentosLabel;
    @FXML private Label     mensajeValidacionLabel;

    @FXML private Label     n1Label, n2Label, n3Label, n4Label;
    @FXML private TextField r1Field, r2Field, r3Field, r4Field;

    @FXML private VBox   exploracionPane;
    @FXML private VBox   validacionPane;
    @FXML private Button btnYaConozcoLaRegla;
    @FXML private Button btnVolverExplorar;
    @FXML private Button btnNuevaPartida;
    @FXML private Button btnVolverMenu;

    private JuegoModelo modelo;

    @FXML
    private void initialize() {
        modelo = App.getModelo();
        actualizarVista();
    }

    private void actualizarVista() {
        boolean enValidacion = modelo.isModoValidacion();
        boolean terminado    = modelo.isTerminado();

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

        if (terminado) bloquear();

        btnVolverExplorar.setVisible(!terminado && enValidacion);
        btnVolverExplorar.setManaged(!terminado && enValidacion);
        btnYaConozcoLaRegla.setVisible(!enValidacion && !terminado);
        btnYaConozcoLaRegla.setManaged(!enValidacion && !terminado);
    }

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

    @FXML
    private void validar() {
        if (modelo.isTerminado()) return;

        if (modelo.verificarTextoRegla(reglaField.getText())) {
            modelo.ganar();
            actualizarVista();
            return;
        }

        try {
            int[] respuestas = {
                Integer.parseInt(r1Field.getText().trim()),
                Integer.parseInt(r2Field.getText().trim()),
                Integer.parseInt(r3Field.getText().trim()),
                Integer.parseInt(r4Field.getText().trim())
            };

            if (modelo.verificarSalidas(respuestas)) {
                modelo.ganar();
            } else {
                modelo.registrarIntentoFallido();
            }

        } catch (NumberFormatException e) {
            mensajeValidacionLabel.setText("Completa todos los campos con números enteros.");
        }

        actualizarVista();
    }

    @FXML
    private void volverExplorar() {
        if (!modelo.isTerminado()) {
            modelo.volverExploracion();
            actualizarVista();
        }
    }

    @FXML
    private void nuevaPartida() {
        modelo.reiniciar();
        desbloquear();
        limpiarCampos();
        actualizarVista();
    }

    @FXML
    private void volverMenu() throws IOException {
        App.setRoot("primary");
    }

    private void bloquear() {
        inputUsuario.setDisable(true);
        r1Field.setDisable(true);
        r2Field.setDisable(true);
        r3Field.setDisable(true);
        r4Field.setDisable(true);
        reglaField.setDisable(true);
    }

    private void desbloquear() {
        inputUsuario.setDisable(false);
        r1Field.setDisable(false);
        r2Field.setDisable(false);
        r3Field.setDisable(false);
        r4Field.setDisable(false);
        reglaField.setDisable(false);
    }

    private void limpiarCampos() {
        inputUsuario.clear();
        r1Field.clear();
        r2Field.clear();
        r3Field.clear();
        r4Field.clear();
        reglaField.clear();
    }
}
