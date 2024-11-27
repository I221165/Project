package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;
import databaseOP.*;
import model.*;
public class Customer_Update_Controller {

    // FXML fields for capturing customer input
    @FXML
    private TextField nameField;  // For new name
    @FXML
    private TextField ageField;   // For new age
    @FXML
    private TextField contactField;  // For new contact number
    @FXML
    private TextField emailField;  // For new email
    @FXML
    private TextField cnicField;   // For new CNIC
    @FXML
    private TextField passwordField;  // For new password
    @FXML
    private Button updateButton;  // Update button to trigger action

    // Handle the update button click event
    public void handleUpdate(ActionEvent event) {
        // Get the new values from the text fields
        String newName = nameField.getText();
        String newAge = ageField.getText();
        String newContact = contactField.getText();
        String newEmail = emailField.getText();
        String newCnic = cnicField.getText();
        String newPassword = passwordField.getText();

        // Validate the inputs (Ensure all fields have values)
        if (newName.isEmpty() || newAge.isEmpty() || newContact.isEmpty() || newEmail.isEmpty() || newCnic.isEmpty() || newPassword.isEmpty()) {
            showAlert("Input Error", "Please fill all fields.");
            return;
        }

        // Call the database update function

        int updateSuccess = AdminOP.updateCustomer(Integer.valueOf(newAge), newName, newCnic, newEmail, newContact, newPassword);
        // Show an alert based on the result of the update operation
        if (updateSuccess == 1) {
            showAlert("Success", "Customer details updated successfully.");
        } else {
            showAlert("Error", "Failed to update customer details.");
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
