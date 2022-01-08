package org.matt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.matt.dataBeans.UserData;
import org.matt.scenes.SceneChanger;
import org.matt.scenes.Views;
import org.matt.sql.UserMethods;

public class LogInController {
    @FXML
    private Button logInButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField pwField;

    @FXML
    private void handleLoginButton(ActionEvent event) {
        UserData user = new UserData(nameField.getText(), pwField.getText());
        if (UserMethods.authenticateUser(user)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Log in was successful " + user.getName());
            alert.showAndWait();
            UserMethods.addId(user);
            GlobalProperties.setCurrentUserId(user.getUser_id());
            SceneChanger.changeScene(Views.MAIN, logInButton);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Credentials");
            alert.setHeaderText(null);
            alert.setContentText("Account not found in database, please try again or register a new user");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) {
        SceneChanger.openNewScene(Views.REGISTER);
    }
}
