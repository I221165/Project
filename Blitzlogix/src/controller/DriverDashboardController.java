package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DriverDashboardController {


	@FXML
    private StackPane driverContentPane;

    // Load assigned parcels content
    @FXML
    private void viewDeliveryParcels() {

    	loadContent("/view/DeliverParcel.fxml","DeliveryTime");
    	
    }
    @FXML
    private void pickUpParcel() {
        loadContent("/view/PickUpParcel.fxml", "Pick Up Parcel");
    }
    // Load update delivery status content
    
    
    
    private void loadContent(String fxmlPath, String title) {
        try {
        	 Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
           
            

            // Replace content pane
        	 driverContentPane.getChildren().clear();
        	 driverContentPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Failed to load " + title + ". Please try again.");
            driverContentPane.getChildren().clear();
            driverContentPane.getChildren().add(errorLabel);
        }
    }
    
    public void logOut() {
        try {
        	 Parent root = FXMLLoader.load(getClass().getResource("/view/DriverLogin.fxml"));
          

            Stage currentStage = (Stage) driverContentPane.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


