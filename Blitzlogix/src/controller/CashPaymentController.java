package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class CashPaymentController {

    @FXML
    private ComboBox<String> parcelIdComboBox; // ComboBox for parcel selection

    @FXML
    private Label statusLabel; // Label to show status messages

    @FXML
    public void initialize() {
        // Populate ComboBox with parcel options
        parcelIdComboBox.getItems().addAll(
                "Parcel-001 (Amount Due: $50.00)",
                "Parcel-002 (Amount Due: $75.00)",
                "Parcel-003 (Amount Due: $100.00)"
        );
    }

    @FXML
    public void handleConfirmPayment() {
        // Check if a parcel is selected
        if (parcelIdComboBox.getValue() == null) {
            statusLabel.setText("Please select a Parcel ID.");
            return;
        }

        // Process payment confirmation for the selected parcel
        String selectedParcel = parcelIdComboBox.getValue();
        statusLabel.setText("Payment successful for " + selectedParcel + "!");
    }
}
