package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import model.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UserDashboardController {

    public StackPane userContentPane; // Bind this to your FXML

    
    
    public void handlePayment() {
        loadContent("/view/Payment.fxml", "Payment");
    }
    
    public void Feedback() {
        loadContent("/view/Feedback.fxml", "Feedback");
    }

    public void DeliverParcel() {
        loadContent("/view/DeliverParcelC.fxml", "PickUp");
    }

    public void ParcelTracking() {
        loadContent("/view/ParcelTracking.fxml", "ParcelTracking");
    }
    
    public void sendParcels() {
        loadContent("/view/SendParcel.fxml", "Send Parcels");
    }

    public void logOut() {
        try {
        	 Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            

            Stage currentStage = (Stage) userContentPane.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadContent(String fxmlPath, String title) {
        try {
        	 Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
           
            

            // Replace content pane
            userContentPane.getChildren().clear();
            userContentPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Failed to load " + title + ". Please try again.");
            userContentPane.getChildren().clear();
            userContentPane.getChildren().add(errorLabel);
        }
    }
}
