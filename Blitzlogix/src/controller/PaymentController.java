package controller;

import databaseOP.CustomerOP;
import databaseOP.PaymentOP;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentController {

    @FXML
    private ComboBox<String> parcelIdComboBox;

    @FXML
    private Button payButton;

    @FXML
    private Label statusLabel;

    @FXML
    private HBox paymentOptionsBox; // Container for dynamic buttons
    
    @FXML
    public void initialize() {
        // Populate parcel IDs from database
        populateParcelComboBox();

        // Hide payment options initially
        paymentOptionsBox.setVisible(false);
    }

    private void populateParcelComboBox() {
        try {
            
            ResultSet resultSet = PaymentOP.getUnpaidPaymentsForCustomer(Session.getInstance().getUserId()); 
            while (resultSet.next()) {
                
                String parcelId = resultSet.getString("parcel_id");
                
                
                
                String displayText = "Parcel-" + parcelId;
                
                // Add the formatted string to ComboBox
                parcelIdComboBox.getItems().add(displayText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error loading parcel data.");
        }
    }

    @FXML
    public void handlePayment() {
        if (parcelIdComboBox.getValue() == null) {
            statusLabel.setText("Please select a Parcel ID.");
            return;
        }
        
        
        String selectedParcel = parcelIdComboBox.getValue();
        String parcelId = selectedParcel.split(" ")[0].replace("Parcel-", ""); 
        Session.getInstance().setParcelID(Integer.parseInt(parcelId));

        // Show payment options
        paymentOptionsBox.setVisible(true);
    }

    @FXML
    public void handleCashPayment() {
        loadScene("/view/CashPayment.fxml", "Cash Payment");
    }

    @FXML
    public void handleCardPayment() {
        loadScene("/view/CardPayment.fxml", "Card Payment");
    }

    @FXML
    public void handleOnlinePayment() {
        loadScene("/view/OnlinePayment.fxml", "Online Payment");
    }

    private void loadScene(String fxmlFile, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
