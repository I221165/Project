package controller;

import javafx.fxml.FXML;

import databaseOP.*;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import model.*;

public class LoginRegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField emailField;
    @FXML private TextField cnicField; // Added CNIC field
    @FXML private TextField phoneNumberField; // Added Phone Number field
    @FXML private Label statusLabel;
    
    String UID;

  //  private CustomerService customerService = new CustomerService();

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String cnic = cnicField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || cnic.isEmpty() || phoneNumber.isEmpty()) {
            statusLabel.setText("All fields must be filled out.");
            return;
        }

        if (!isValidCNIC(cnic)) {
            statusLabel.setText("Invalid CNIC format. It should be 13 digits long.");
            return;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            statusLabel.setText("Invalid phone number format. It should be 11 digits long.");
            return;
        }

     
        
        if (CustomerOP.registerCustomer(cnic, username, phoneNumber, email, password)) {
        	
        	int userId = CustomerOP.searchCustomer(cnic); // Convert the input to an integer
        	Session.getInstance().clearSession(); // Clears all previous session data
        	Session.getInstance().setUserId(userId); // Set only the current role's ID
            showAlert("Registration Successful", "You can now log in.");
            statusLabel.setText("");
        } else {
            statusLabel.setText("Registration failed. Try a different username or email.");
        }
    }
    
    private boolean isValidCNIC(String cnic) {
        return cnic.matches("\\d{13}"); // Validates 13-digit CNIC
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{11}"); // Validates 11-digit phone number
    }



    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        
        
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password.");
            return;
        }
       
        int userId = Integer.parseInt(username); // Convert the input to an integer
       if (CustomerOP.verifyLogin(userId,password )) {
    	   Session.getInstance().setUserId(userId); 
            showAlert("Login Successful", "Welcome " + username);
            statusLabel.setText("");
            openUserDashboard();
        } else {
            statusLabel.setText("Login failed. Invalid credentials.");
        }
        
        
    }

    private void openUserDashboard() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/UserDashboard.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    


    @FXML
    private void openRegistration() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/Register.fxml"));
           
           
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openLogin() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
          
            
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
