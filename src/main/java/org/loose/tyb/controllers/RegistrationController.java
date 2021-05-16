package org.loose.tyb.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.tyb.exceptions.UsernameAlreadyExistsException;
import org.loose.tyb.services.UserService;

import java.io.IOException;

public class RegistrationController {

    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    @FXML
    public void handleRegisterAction() throws Exception {

        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username == null || username.isEmpty()) {
                registrationMessage.setText("Please type in a username!");
                return;
            }

            if (password == null || password.isEmpty()) {
                registrationMessage.setText("Password cannot be empty");
                return;
            }
            UserService.addUser(usernameField.getText(), passwordField.getText());
            registrationMessage.setText("Account created successfully!");
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage stage = (Stage) registrationMessage.getScene().getWindow();
            stage.setTitle("Trade your books");
            stage.setScene(new Scene(root, 400, 275));
            stage.show();
        } catch (UsernameAlreadyExistsException e) {
            registrationMessage.setText(e.getMessage());
        }
    }

    public void handleLogInAction() throws IOException {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage stage = (Stage) registrationMessage.getScene().getWindow();
            stage.setTitle("Trade your books");
            stage.setScene(new Scene(root, 400, 275));
            stage.show();
    }
}