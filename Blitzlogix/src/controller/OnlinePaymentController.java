package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Payments;
import javafx.scene.control.Label;

public class OnlinePaymentController {

    @FXML
    private TextField bankNameField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private Label statusLabel;

    @FXML
    public void handleConfirmPayment() {
        String bankName = bankNameField.getText();
        String trans = cardNumberField.getText();

        if (bankName.isEmpty() || trans.isEmpty()) {
            statusLabel.setText("All fields are required.");
            return;
        }
        Payments p = new Payments(Session.getInstance().getParcelID());
        p.setName("CardPayment");
        p.setBank(bankName);
        p.setTransactionID(trans);
        p.setWayOfPayment("CardPayment");
        
        p.processPayment();
        statusLabel.setText("Online payment successful!");
    }
}
