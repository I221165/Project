package controller;
import model.*;
import databaseOP.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import databaseOP.*
;public class AdminLoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private AdminService adminService = new AdminService();

    @FXML
    private void openAdminDashboard() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Admin");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdminLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("All fields must be filled out.");
            return; 
        }

        // Create Admin object
        UserDetails userDetails = new UserDetails(); // Assuming UserDetails constructor with username and email
        Admin admin = new Admin();
       // admin.setDetails(userDetails);
        admin.setPassword(password);
        int adID = Integer.parseInt(username);
        if (AdminOP.verifyAdminLogin(adID, password)) {
        	Session.getInstance().clearSession();
Session.getInstance().setAdminId(adID);
            openAdminDashboard();
       } else {
            statusLabel.setText("Invalid login credentials.");
        }
        
        
       
        
    }
}
