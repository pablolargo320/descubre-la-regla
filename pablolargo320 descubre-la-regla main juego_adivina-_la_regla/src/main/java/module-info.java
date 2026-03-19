module poli.educo {
    requires javafx.controls;
    requires javafx.fxml;

    opens poli.educo to javafx.fxml;
    exports poli.educo;
}