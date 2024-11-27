package controller;

import java.io.*;
import databaseOP.*;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
public class DriverLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        
        if (username.isEmpty() || password.isEmpty() ) {
        	showAlert("All fields must be filled out.","Try again.");
            return; 
        }
        int dID = Integer.parseInt(username);
        // For now, check for basic credentials (you can connect to a database later)
        if (DriverOP.verifyDriverLogin(dID, password)) {
        	
        	Session.getInstance().setDriverId(dID); // Set only the current role's ID
            showAlert("Login Successful", "Welcome, Driver!");
            openDriverDashboard();
        } else {
            showAlert("Login Failed", "Invalid credentials. Please try again.");
        }
    
        
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void openDriverDashboard() {
        try {
            // Path to the new FXML file
        	 Parent root = FXMLLoader.load(getClass().getResource("/view/DriverDashboard.fxml"));


            // Create a new stage for the dashboard
            Stage stage = new Stage();
            stage.setTitle("Driver Dashboard");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current login stage
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
