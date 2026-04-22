package poli.edu.co;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import poli.edu.co.modelo.JuegoModelo;

import java.io.IOException;

public class App extends Application {

    private static JuegoModelo modelo;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        modelo = new JuegoModelo();
        scene = new Scene(loadFXML("primary"), 660, 520);
        stage.setTitle("Adivina la Regla");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static JuegoModelo getModelo() {
        return modelo;
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static javafx.scene.Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/poli/edu/co/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
