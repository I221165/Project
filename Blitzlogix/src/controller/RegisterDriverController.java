package controller;

import databaseOP.CustomerOP;
import databaseOP.*;
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

        int ID = CenterOP.getCenterIDByCity(selectedCity);
        
if (DriverOP.registerDriver(cnic, driverName, phone, email, password, driverType,ID )) {
        	
        	int userId = DriverOP.searchDriver(cnic); // Convert the input to an integer
        	Session.getInstance().clearSession(); // Clears all previous session data
        	Session.getInstance().setDriverId(userId); // Set only the current role's ID
        	System.out.println("Driver Registered");
        	
        	driverNameField.setText("");
        } else {
        	System.out.println("Driver No registered");
        }
        
        
    }

    private boolean isValidCNIC(String cnic) {
        return cnic.matches("\\d{13}"); // Validates 13-digit CNIC
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d{11}"); // Validates 11-digit phone number
    }
}
