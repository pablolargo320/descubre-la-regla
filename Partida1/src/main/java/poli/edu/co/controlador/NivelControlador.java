package poli.edu.co.controlador;

import javafx.fxml.FXML;
import java.io.IOException;
import poli.edu.co.App;
import poli.edu.co.modelo.Nivel;

public class NivelControlador {

    @FXML
    private void seleccionarFacil() throws IOException {
        seleccionar(Nivel.FACIL);
    }

    @FXML
    private void seleccionarIntermedio() throws IOException {
        seleccionar(Nivel.INTERMEDIO);
    }

    @FXML
    private void seleccionarDificil() throws IOException {
        seleccionar(Nivel.DIFICIL);
    }

    @FXML
    private void volverMenu() throws IOException {
        App.setRoot("primary");
    }

    private void seleccionar(Nivel nivel) throws IOException {
        App.getModelo().setNivel(nivel);
        App.setRoot("Juego");
    }
}
