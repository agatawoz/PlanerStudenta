module com.example.login_page {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    //requires eu.hansolo.tilesfx;

    opens com.example.login_page to javafx.fxml;
    exports com.example.login_page;
}