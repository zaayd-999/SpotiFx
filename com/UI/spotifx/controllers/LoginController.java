package com.UI.spotifx.controllers;

import com.UI.spotifx.Main;
import com.UI.spotifx.utils.AuthState;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.db.SpotiDB;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void onLogin() {

        String username = emailField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please fill all fields");
            return;
        }



        //SpotiDB database = MainController.getInstance().getAppConnection();
        SpotiDB database = new SpotiDB();
        database.connect(error -> {
            if(error != null) {
                System.out.println("Error: Failed to establish connection to the MySQL database server\n" + error.getMessage());
                System.exit(2);
            } else {
                System.out.println("Success: Connected to the MySQL database server");
            }
        });
        if (database.login(username,password) != null) {
            AuthState.getInstance().login();
            Main.setRoot("main.fxml");

        } else {
            showError("Invalid credentials");
        }
        database.disconnect();
    }

    @FXML
    private void goToSignup() {
        // SWITCH VIEW CORRECTLY
        Main.setRoot("signup.fxml");
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
    }
}