package controller;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import databaseOP.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

;public class CashPaymentController {

    @FXML
    private ComboBox<String> parcelIdComboBox; // ComboBox for parcel selection

    @FXML
    private Label statusLabel; // Label to show status messages

    @FXML
    public void initialize() {
        // Populate ComboBox with parcel options
    	populateParcelComboBox();
    }

    private void populateParcelComboBox() {
        try {
            
            ResultSet resultSet = PaymentOP.getUnpaidPaymentsForCustomer(Session.getInstance().getUserId()); 
            while (resultSet.next()) {
                
                String parcelId = resultSet.getString("parcel_id");
                
                
                
               // String displayText = "Parcel-" + parcelId;
                
                // Add the formatted string to ComboBox
                parcelIdComboBox.getItems().add(parcelId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error loading parcel data.");
        }
    }
    
    @FXML
    public void handleConfirmPayment() {
        // Check if a parcel is selected
        if (parcelIdComboBox.getValue() == null) {
            statusLabel.setText("Please select a Parcel ID.");
            return;
        }

        Payments p = new Payments(Session.getInstance().getParcelID());
        p.setName("CashPayment");
        p.setWayOfPayment("CashPayment");
        p.processPayment();
        
        
    }
}
