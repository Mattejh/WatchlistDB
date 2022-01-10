package org.matt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


/**
 * JavaFX App
 */
public class App extends Application {
    private static App instance;

    private static Scene scene;

    public App() {
        instance = this;
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML());
        scene.getStylesheets().add(getCss());
        stage.setTitle("MovieDB Manager");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }



    public static String getCss() {
        return Objects.requireNonNull(App.class.getResource("dark-theme.css")).toExternalForm();
    }

    public static App getInstance() {
        return instance;
    }

    private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("logIn" + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}