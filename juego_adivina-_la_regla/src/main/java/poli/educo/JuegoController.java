package poli.educo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

public class JuegoController {

    @FXML
    private Label contadorLabel;

    @FXML
    private Label mensajeLabel;

    private int intentos = 0;
    private final int MAX_INTENTOS = 10;

    @FXML
    private void realizarIntento() {

        if(intentos < MAX_INTENTOS){

            intentos++;

            contadorLabel.setText("Intentos: " + intentos);

            if(intentos == MAX_INTENTOS){
                mensajeLabel.setText("Has llegado al límite de intentos.");
            }

        }

    }

    @FXML
    private void reiniciarPartida(){

        intentos = 0;

        contadorLabel.setText("Intentos: 0");

        mensajeLabel.setText("");
    }

    @FXML
    private void volverMenu() throws IOException {
        App.setRoot("primary");
    }

}