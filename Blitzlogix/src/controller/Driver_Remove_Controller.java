package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.sql.*;

import databaseOP.*;

public class Driver_Remove_Controller {

    // FXML fields for the input and button
    @FXML
    private TextField idField;  // To input the driver ID to be removed
    @FXML
    private Button removeButton;  // Remove button to trigger action

    // Handle the Remove button click event
    public void handleRemove(ActionEvent event) {
        // Get the driver ID from the text field
        String driverId = idField.getText();

        // Validate the input (Ensure ID is not empty)
        if (driverId.isEmpty()) {
            showAlert("Input Error", "Please enter a driver ID.");
            return;
        }
        int userId = Integer.parseInt(driverId); // Convert the input to an integer
        // Call the database function to remove the driver
        boolean removalSuccess = DriverOP.removeDriver(userId);

        // Show an alert based on the result of the remove operation
        if (removalSuccess) {
            showAlert("Success", "Driver removed successfully.");
        } else {
            showAlert("Error", "Failed to remove the driver.");
        }
    }

    

    // Method to show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
