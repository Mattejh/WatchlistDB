package org.matt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.matt.dataBeans.UserData;
import org.matt.sql.UserMethods;

public class RegisterController {
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField pwField;
    @FXML
    private Button backButton;
    @FXML
    private Button registerButton;

    @FXML
    private void handleRegisterButton(ActionEvent event) {
        UserData user = new UserData(nameField.getText(), pwField.getText());
        UserMethods.addUser(user);
        registerButton.getScene().getWindow().hide();
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
