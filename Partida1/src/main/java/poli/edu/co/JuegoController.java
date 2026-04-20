package poli.edu.co;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class JuegoController {

    @FXML private TextField inputUsuario;
    @FXML private Label outputSistema;
    @FXML private Label mensajeExploracionLabel;

    @FXML private TextField reglaField;
    @FXML private Label intentosLabel;
    @FXML private Label mensajeValidacionLabel;

    @FXML private Label n1Label, n2Label, n3Label, n4Label;
    @FXML private TextField r1Field, r2Field, r3Field, r4Field;

    @FXML private VBox exploracionPane;
    @FXML private VBox validacionPane;

    @FXML private Button btnYaConozcoLaRegla;
    @FXML private Button btnVolverExplorar;
    @FXML private Button btnNuevaPartida;
    @FXML private Button btnVolverMenu;

    @FXML
    private void initialize() {
        actualizarVista();
    }

    private void actualizarVista() {
        exploracionPane.setVisible(!JuegoState.validationMode);
        exploracionPane.setManaged(!JuegoState.validationMode);

        validacionPane.setVisible(JuegoState.validationMode);
        validacionPane.setManaged(JuegoState.validationMode);

        outputSistema.setText(JuegoState.lastOutput);
        mensajeExploracionLabel.setText(JuegoState.exploreMessage);

        intentosLabel.setText("Intentos: " + JuegoState.attemptsLeft);
        mensajeValidacionLabel.setText(JuegoState.validationMessage);

        n1Label.setText("" + JuegoState.challengeInputs[0]);
        n2Label.setText("" + JuegoState.challengeInputs[1]);
        n3Label.setText("" + JuegoState.challengeInputs[2]);
        n4Label.setText("" + JuegoState.challengeInputs[3]);

        if (JuegoState.finished) {
            bloquear();
        }

        // Si ya ganó o perdió, no puede volver a explorar
        btnVolverExplorar.setVisible(!JuegoState.finished && JuegoState.validationMode);
        btnVolverExplorar.setManaged(!JuegoState.finished && JuegoState.validationMode);

        // El botón de entrar a validación solo aparece en la fase de exploración
        btnYaConozcoLaRegla.setVisible(!JuegoState.validationMode && !JuegoState.finished);
        btnYaConozcoLaRegla.setManaged(!JuegoState.validationMode && !JuegoState.finished);
    }

    @FXML
    private void probarNumero() {
        try {
            int n = Integer.parseInt(inputUsuario.getText().trim());
            int res = JuegoState.applyRule(n);

            JuegoState.lastOutput = "Salida: " + res;
            actualizarVista();

        } catch (Exception e) {
            mensajeExploracionLabel.setText("Número inválido");
        }
    }

    @FXML
    private void yaConozcoLaRegla() {
        JuegoState.startValidationMode();
        actualizarVista();
    }

    @FXML
    private void validar() {
        if (JuegoState.finished) return;

        if (!reglaField.getText().isEmpty() &&
                JuegoState.checkRuleText(reglaField.getText())) {

            JuegoState.finishWon();
            actualizarVista();
            return;
        }

        try {
            int[] r = {
                    Integer.parseInt(r1Field.getText().trim()),
                    Integer.parseInt(r2Field.getText().trim()),
                    Integer.parseInt(r3Field.getText().trim()),
                    Integer.parseInt(r4Field.getText().trim())
            };

            if (JuegoState.checkOutputs(r)) {
                JuegoState.finishWon();
            } else {
                JuegoState.registerFailedAttempt();
            }

        } catch (Exception e) {
            mensajeValidacionLabel.setText("Completa todo");
        }

        actualizarVista();
    }

    @FXML
    private void volverExplorar() {
        if (!JuegoState.finished) {
            JuegoState.backToExploration();
            actualizarVista();
        }
    }

    @FXML
    private void nuevaPartida() {
        JuegoState.resetFull();
        desbloquear();
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
}