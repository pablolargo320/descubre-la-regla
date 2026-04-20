module poli.edu.co {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.desktop;
    requires java.logging;

    opens poli.edu.co to javafx.fxml;
    exports poli.edu.co;
}
