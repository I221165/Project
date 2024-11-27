package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import databaseOP.*;; // Assuming you have a DatabaseHandler class

public class Manager_Login_Controller {

    

    // FXML fields for ID and password TextFields
    @FXML
    private TextField managerIdField;
    
    @FXML
    private TextField passwordField;

    public Manager_Login_Controller() {
        
    }

    public void login(ActionEvent e) {
        
        String managerId = managerIdField.getText();
        String password = passwordField.getText();

        
        
        int userId = Integer.parseInt(managerId); // Convert the input to an integer
        if (ManagerOP.verifyManagerLogin(userId, password)) {
            Session.getInstance().setManagerID(userId);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Manager_Menu.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Manager Menu");
                stage.setScene(new Scene(root, 700, 700));
                stage.show();
                // Close the current login window
                ((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            // Show an error message if login is invalid
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Please check your manager ID and password.");
            alert.showAndWait();
        }
    }
}
