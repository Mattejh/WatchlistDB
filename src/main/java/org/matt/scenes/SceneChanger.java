package org.matt.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.matt.App;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class SceneChanger {


    public static void changeScene(Views view, Node prev) {
        Parent root;
        FXMLLoader loader = getLoader(view);

        try {
            root = loader.load();
            root.getStylesheets().add(App.getCss());
            Stage newStage = new Stage();
            newStage.setTitle("MovieDB Manager");
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            newStage.show();

            Stage oldStage = (Stage) prev.getScene().getWindow();
            oldStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openNewScene(Views view) {
        Parent root;
        AtomicReference<Double> balancedXPos = new AtomicReference<>((double) 0);
        AtomicReference<Double> balancedYPos = new AtomicReference<>((double) 0);
        FXMLLoader loader = getLoader(view);
        Stage newStage = new Stage();

        try {
            root = loader.load();
            root.getStylesheets().add(App.getCss());
            root.setOnMousePressed(event -> {
                balancedXPos.set(event.getSceneX());
                balancedYPos.set(event.getSceneY());
            });
            root.setOnMouseDragged(event -> {
                newStage.setX(event.getScreenX() - balancedXPos.get());
                newStage.setY(event.getScreenY() - balancedYPos.get());
            });
            newStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setResizable(false);
        newStage.showAndWait();
    }


    public static FXMLLoader getLoader(Views view) {
        return new FXMLLoader(App.class.getResource(view.getFxml()));
    }

}
