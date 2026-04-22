package poli.edu.co.controlador;

import java.io.IOException;
import javafx.fxml.FXML;
import poli.edu.co.App;

public class ReglasControlador {

    @FXML
    private void volverMenu() throws IOException {
        App.setRoot("primary");
    }
}
