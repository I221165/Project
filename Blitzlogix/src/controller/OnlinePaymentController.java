package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class OnlinePaymentController {

    @FXML
    private TextField bankNameField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private Label statusLabel;

    
    String adminId = Session.getInstance().getAdminId();
    String driverId = Session.getInstance().getDriverId();
    String UId = Session.getInstance().getUserId();
    
    @FXML
    public void initialize() {
    	
    }
    
    @FXML
    public void handleConfirmPayment() {
        String bankName = bankNameField.getText();
        String cardNumber = cardNumberField.getText();

        if (bankName.isEmpty() || cardNumber.isEmpty()) {
            statusLabel.setText("All fields are required.");

            System.out.println("Admin ID: " + adminId);
            System.out.println("D ID: " + driverId);
            System.out.println("u ID: " + UId);
            
            return;
        }

        statusLabel.setText("Online payment successful!");
    }
}
