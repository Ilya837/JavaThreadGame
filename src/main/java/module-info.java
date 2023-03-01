module com.example.threadpanelfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.threadpanelfx to javafx.fxml;
    exports com.example.threadpanelfx;
}