package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class CardPaymentController {

    @FXML
    private TextField transactionIdField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private Label statusLabel;

    @FXML
    public void handleConfirmPayment() {
        String transactionId = transactionIdField.getText();
        String cardNumber = cardNumberField.getText();

        if (transactionId.isEmpty() || cardNumber.isEmpty()) {
            statusLabel.setText("All fields are required.");
            return;
        }

        statusLabel.setText("Card payment successful!");
    }
}
