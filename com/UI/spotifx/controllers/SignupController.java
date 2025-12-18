package com.UI.spotifx.controllers;

import com.UI.spotifx.Main;
import com.db.SpotiDB;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.structure.Class.User.listener;

public class SignupController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;

    @FXML
    private void onSignup() {

        if (usernameField.getText().isEmpty()
                || emailField.getText().isEmpty()
                || passwordField.getText().isEmpty()
                || confirmPasswordField.getText().isEmpty()) {

            showError("All fields are required");
            return;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showError("Passwords do not match");
            return;
        }

        /* BACKEND INTEGRATION POINT */
        SpotiDB database = new SpotiDB();
        database.connect(error -> {
            if(error != null) {
                System.out.println("Error: Failed to establish connection to the MySQL database server\n" + error.getMessage());
                System.exit(2);
            } else {
                System.out.println("Success: Connected to the MySQL database server");
            }
        });
        listener thisListener = new listener.listenerBuilder(1,usernameField.getText()).email(emailField.getText()).build();
        try {
            thisListener = (listener) database.addUserToDB(thisListener, passwordField.getText());
            Main.setRoot("login.fxml");
        } catch (Exception e) {
            showError("Signup failed");
        }

        database.disconnect();
    }

    @FXML
    private void goToLogin() {
        Main.setRoot("login.fxml");
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
    }
}