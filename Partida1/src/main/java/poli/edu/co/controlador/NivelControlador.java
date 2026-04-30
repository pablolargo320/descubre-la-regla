package poli.edu.co.controlador;

import javafx.fxml.FXML;
import java.io.IOException;
import poli.edu.co.App;
import poli.edu.co.modelo.Nivel;
import poli.edu.co.modelo.Regla;

/**
 * Controlador de la pantalla de selección de nivel.
 *
 * Responsabilidades (MVC):
 *  - Capturar la elección del usuario.
 *  - Crear los objetos de dominio requeridos por el modelo (Nivel + Regla).
 *  - Inyectarlos en el modelo antes de navegar a la pantalla de juego.
 *
 * Nota de arquitectura:
 *  El 'new Regla(nivel)' está AQUÍ (en el controlador) y no en Partida.
 *  Esto cumple con el principio de que el modelo no instancia sus propias
 *  dependencias; el controlador actúa como ensamblador local de la partida.
 */
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

    /**
     * Crea la Regla en el controlador (new aquí, no en el modelo) y
     * delega la inicialización al modelo mediante inyección de parámetros.
     */
    private void seleccionar(Nivel nivel) throws IOException {
        Regla regla = new Regla(nivel);                  // ← new en el controlador
        App.getPartida().iniciarConNivel(nivel, regla);  // modelo recibe ambos
        App.setRoot("Juego");
    }
}
