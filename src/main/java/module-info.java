module com.example.kabe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kabe to javafx.fxml;
    exports com.example.kabe;
}