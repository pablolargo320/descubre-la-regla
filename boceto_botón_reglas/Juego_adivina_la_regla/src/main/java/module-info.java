module poli.edu.co {
    requires javafx.controls;
    requires javafx.fxml;

    opens poli.edu.co to javafx.fxml;
    exports poli.edu.co;
}
