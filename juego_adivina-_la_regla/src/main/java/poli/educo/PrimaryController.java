package poli.educo;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void abrirReglas() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void iniciarPartida() throws IOException {
        App.setRoot("juego");
    }

}