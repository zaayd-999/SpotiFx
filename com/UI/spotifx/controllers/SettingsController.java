package com.UI.spotifx.controllers;

import com.UI.spotifx.Main;
import com.UI.spotifx.utils.AuthState;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;

public class SettingsController {

    @FXML
    private void onLogout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Logged out");
        alert.setContentText("You have been logged out successfully.");
        alert.showAndWait();

        AuthState.getInstance().logout();

        Main.setRoot("login.fxml");
    }
}
