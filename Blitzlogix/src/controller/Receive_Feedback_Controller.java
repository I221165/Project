package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import databaseOP.*;


public class Receive_Feedback_Controller {
    
    @FXML
    private TextField feedbackIdField;  // To capture the feedback ID entered by the user
    
    @FXML
    private Button confirmButton;  // Button to confirm the action
    
    
    public void handleConfirm(ActionEvent event) {
        String feedbackId = feedbackIdField.getText();  
        
        if (feedbackId == null || feedbackId.trim().isEmpty()) {
            
            showAlert("Input Error", "Please enter a valid feedback ID.");
            return;
        }
String feedback = null;
        
        ResultSet resultSet = FeedbackOP.getFeedbackForManagerCenter(Session.getInstance().getManagerID());  // This should be replaced with actual DB interaction

        try {
			if (resultSet.next()) {
			    // Extract feedback data from the result set
			    feedback = "Comment: " + resultSet.getString("comment") + "\nScore: " + resultSet.getString("score");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        if (feedback != null) {
            // Display feedback to the user (You can update a label or show the feedback in an alert)
            showAlert("Feedback Received", "Feedback for ID " + feedbackId + ": " + feedback);
        } else {
            // If no feedback is found, notify the user
            showAlert("No Feedback Found", "No feedback found for the given ID.");
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
