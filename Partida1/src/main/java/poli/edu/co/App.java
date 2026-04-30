package poli.edu.co;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import poli.edu.co.modelo.Partida;
import poli.edu.co.modelo.dao.ConexionBD;
import poli.edu.co.modelo.dao.JugadorDAO;
import poli.edu.co.modelo.dao.JugadorDAOImpl;

import java.io.IOException;

public class App extends Application {

    private static Partida partida;
    private static Scene   scene;

    @Override
    public void start(Stage stage) throws IOException {
        ConexionBD.inicializarTablas();          // crea la tabla jugadores si no existe

        // ── Composition Root: ensamblado de dependencias ─────────────────────
        // Los 'new' de infraestructura y modelo van AQUÍ (entry point), no en el modelo.
        JugadorDAO dao = new JugadorDAOImpl();   // implementación concreta
        partida = new Partida(dao);              // modelo recibe la abstracción

        // ── Escena y CSS global ───────────────────────────────────────────────
        scene = new Scene(loadFXML("primary"), 660, 520);
        scene.getStylesheets().add(
            App.class.getResource("/poli/edu/co/game-style.css").toExternalForm());

        stage.setTitle("Adivina la Regla");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /** Devuelve la partida activa compartida entre controladores. */
    public static Partida getPartida() {
        return partida;
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static javafx.scene.Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            App.class.getResource("/poli/edu/co/" + fxml + ".fxml"));
        return loader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
