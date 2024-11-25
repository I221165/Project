package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;

import model.*;
public class AdminDashboardController {

    @FXML
    private StackPane contentPane; // Reference to the right side of the SplitPane

    public void showDriverRegistration() {
        try {
            // Load the FXML for the Driver Registration form
        	Parent driverRegistrationForm = FXMLLoader.load(getClass().getResource("/view/RegisterDriver.fxml"));
           
            
            
            
            contentPane.getChildren().clear();
            contentPane.getChildren().add(driverRegistrationForm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void logOut() {
        try {
      
            Parent root = FXMLLoader.load(getClass().getResource("/view/AdminLogin.fxml"));
            
            Stage currentStage = (Stage) contentPane.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showAdminTask1() {
    	try {
    	      
            Parent root = FXMLLoader.load(getClass().getResource("/view/Customer_Update.fxml"));
            
         // Clear the contentPane and set the new content
            contentPane.getChildren().clear();
            contentPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	}

    public void showAdminTask2() {
    	try {
  	      
            Parent root = FXMLLoader.load(getClass().getResource("/view/Driver_Remove.fxml"));
            
         // Clear the contentPane and set the new content
            contentPane.getChildren().clear();
            contentPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void showAdminTask3() {
    	try {
  	      
            Parent root = FXMLLoader.load(getClass().getResource("/view/Generate_Report.fxml"));
            
          //  Stage currentStage = (Stage) contentPane.getScene().getWindow();
         // Clear the contentPane and set the new content
            contentPane.getChildren().clear();
            contentPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void showAdminTask4() {
    	try {
  	      
            Parent root = FXMLLoader.load(getClass().getResource("/view/Manager_Remove.fxml"));
            
         // Clear the contentPane and set the new content
            contentPane.getChildren().clear();
            contentPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
