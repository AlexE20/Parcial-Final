module com.example.parcial_final {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.parcial_final to javafx.fxml;
    exports com.example.parcial_final;
}