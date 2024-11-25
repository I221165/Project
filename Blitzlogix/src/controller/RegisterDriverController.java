package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.*;
public class RegisterDriverController {
    @FXML
    private ComboBox<String> cityComboBox;
    @FXML
    private TextField driverNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField cnicField;
    @FXML
    private TextField phoneField;
    @FXML
    private ComboBox<String> driverTypeComboBox;

    public void initialize() {
        // Sample list of cities; replace with actual dynamic data if necessary
        ObservableList<String> cities = FXCollections.observableArrayList(
            "New York", "Los Angeles", "Chicago", "San Francisco", "Miami"
        );
        cityComboBox.setItems(cities);

        ObservableList<String> driverTypes = FXCollections.observableArrayList("IntraCity", "InterCity");
        driverTypeComboBox.setItems(driverTypes);
    }

    @FXML
    private void registerDriver() {
        String driverName = driverNameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String cnic = cnicField.getText();
        String phone = phoneField.getText();
        String driverType = driverTypeComboBox.getValue();
        String selectedCity = cityComboBox.getValue();

        // Validation
        if (driverName.isEmpty() || password.isEmpty() || email.isEmpty() || cnic.isEmpty() || phone.isEmpty() || driverType == null || selectedCity == null) {
            System.out.println("Please fill in all fields.");
            return;
        }

        if (!isValidCNIC(cnic)) {
            System.out.println("Invalid CNIC format. It should be 13 digits long.");
            return;
        }

        if (!isValidPhoneNumber(phone)) {
            System.out.println("Invalid phone number format. It should be 11 digits long.");
            return;
        }

        // Your logic for registering the driver (e.g., saving data to the database or list)
        System.out.println("Driver Registered: " + driverName + ", " + email + ", " + cnic + ", " + phone + ", " + driverType + ", " + selectedCity);
    }

    private boolean isValidCNIC(String cnic) {
        return cnic.matches("\\d{13}"); // Validates 13-digit CNIC
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d{11}"); // Validates 11-digit phone number
    }
}
