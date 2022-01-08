module org.matt {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mariadb.jdbc;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.google.gson;
    requires opencsv;
    requires com.jfoenix;

    opens org.matt to javafx.fxml;
    exports org.matt;
    exports org.matt.controllers;
    opens org.matt.controllers to javafx.fxml;
    exports org.matt.scenes;
    opens org.matt.scenes to javafx.fxml;
}
