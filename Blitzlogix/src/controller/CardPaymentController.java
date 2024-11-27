package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Payments;
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
        String Bank = transactionIdField.getText();
        String cardNumber = cardNumberField.getText();

        if (Bank.isEmpty() || cardNumber.isEmpty()) {
            statusLabel.setText("All fields are required.");
            return;
        }

        Payments p = new Payments(Session.getInstance().getParcelID());
        p.setName("CardPayment");
        p.setBank(Bank);
        p.setCardNumber(cardNumber);
        
        
        p.processPayment();
        
        
        statusLabel.setText("Card payment successful!");
    }
}
