package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Generate_Report_Controller {

    @FXML
    private TextField idField;  // To input the ID for generating the report

    @FXML
    private ChoiceBox<String> reportChoice;  // ChoiceBox for selecting report type (Driver or Customer)

    @FXML
    private Button confirmButton;  // Confirm button to trigger report generation

    // Handle the "Confirm" button click event to generate the report
    public void generateReport(ActionEvent event) {
        String selectedType = reportChoice.getValue();  // Get selected report type (Driver or Customer)
        String id = idField.getText();  // Get the ID input

        // Validate inputs
        if (selectedType == null || selectedType.isEmpty()) {
            showAlert("Input Error", "Please select a report type.");
            return;
        }

        if (id.isEmpty()) {
            showAlert("Input Error", "Please enter an ID.");
            return;
        }

        // Call the function to generate the report
     //   boolean reportGenerated = AdminOP.generateReportForID(selectedType, id);
        boolean reportGenerated = true;
        // Show success or failure alert
        if (reportGenerated) {
            showAlert("Success", selectedType + " report generated successfully.");
        } else {
            showAlert("Error", "Failed to generate the report for the given ID.");
        }
    }

    

    // Method to show alert messages
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
