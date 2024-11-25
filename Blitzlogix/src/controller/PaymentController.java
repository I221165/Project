package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;

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
        // Populate parcel IDs
        parcelIdComboBox.getItems().addAll(
                "Parcel-001 (Amount Due: $50.00)",
                "Parcel-002 (Amount Due: $75.00)",
                "Parcel-003 (Amount Due: $100.00)"
        );

        // Hide payment options initially
        paymentOptionsBox.setVisible(false);
    }

    @FXML
    public void handlePayment() {
        if (parcelIdComboBox.getValue() == null) {
            statusLabel.setText("Please select a Parcel ID.");
            return;
        }

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

