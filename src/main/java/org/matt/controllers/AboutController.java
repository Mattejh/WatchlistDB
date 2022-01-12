package org.matt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.FontAwesome;
import org.matt.App;

import java.net.URL;
import java.util.ResourceBundle;

import static org.matt.controllers.GlobalProperties.gfxGlyph;

public class AboutController implements Initializable {
    @FXML
    private Button closeButton;
    @FXML
    private Hyperlink gitLink;


    private final String githubUrl = "https://github.com/Mattejh/WatchlistDB";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeButton.setGraphic(gfxGlyph(FontAwesome.Glyph.CLOSE, Color.WHITE, 2));
    }

    @FXML
    private void closeAboutWindow(ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void openGithub(ActionEvent actionEvent) {
        App.getInstance().getHostServices().showDocument(githubUrl);
    }
}
