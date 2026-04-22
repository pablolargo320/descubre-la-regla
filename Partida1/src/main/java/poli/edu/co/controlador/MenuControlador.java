package poli.edu.co.controlador;

import java.io.IOException;
import javafx.fxml.FXML;
import poli.edu.co.App;

public class MenuControlador {

    @FXML
    private void iniciarPartida() throws IOException {
        App.setRoot("Nivel");
    }

    @FXML
    private void abrirReglas() throws IOException {
        App.setRoot("secondary");
    }
}
