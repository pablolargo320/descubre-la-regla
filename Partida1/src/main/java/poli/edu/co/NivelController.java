package poli.edu.co;

import javafx.fxml.FXML;
import java.io.IOException;

public class NivelController {

    @FXML
    private void seleccionarFacil() throws IOException {
        JuegoState.resetFull();
        JuegoState.setNivel("FACIL");
        App.setRoot("juego");
    }

    @FXML
    private void seleccionarIntermedio() throws IOException {
        JuegoState.resetFull();
        JuegoState.setNivel("INTERMEDIO");
        App.setRoot("juego");
    }

    @FXML
    private void seleccionarDificil() throws IOException {
        JuegoState.resetFull();
        JuegoState.setNivel("DIFICIL");
        App.setRoot("juego");
    }

    @FXML
    private void volverMenu() throws IOException {
        App.setRoot("primary");
    }
}