package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.*;
public class SendParcelController {

    @FXML
    private TextField senderCityField;
    @FXML
    private TextField senderNeighbourhoodField;
    @FXML
    private TextField senderStreetNumberField;
    @FXML
    private TextField senderHouseNumberField;

    @FXML
    private TextField recipientCityField;
    @FXML
    private TextField recipientNeighbourhoodField;
    @FXML
    private TextField recipientStreetNumberField;
    @FXML
    private TextField recipientHouseNumberField;

    @FXML
    private TextField weightField;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleSendParcel() {
        // Validate sender address fields
        String senderCity = senderCityField.getText();
        String senderNeighbourhood = senderNeighbourhoodField.getText();
        String senderStreetNumber = senderStreetNumberField.getText();
        String senderHouseNumber = senderHouseNumberField.getText();

        if (senderCity.isEmpty() || senderNeighbourhood.isEmpty() || senderStreetNumber.isEmpty() || senderHouseNumber.isEmpty()) {
            statusLabel.setText("Please fill all sender address fields.");
            return;
        }

        // Validate recipient address fields
        String recipientCity = recipientCityField.getText();
        String recipientNeighbourhood = recipientNeighbourhoodField.getText();
        String recipientStreetNumber = recipientStreetNumberField.getText();
        String recipientHouseNumber = recipientHouseNumberField.getText();

        if (recipientCity.isEmpty() || recipientNeighbourhood.isEmpty() || recipientStreetNumber.isEmpty() || recipientHouseNumber.isEmpty()) {
            statusLabel.setText("Please fill all recipient address fields.");
            return;
        }

        // Validate weight
        int weight;
        try {
            weight = Integer.parseInt(weightField.getText());
            if (weight <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a valid weight.");
            return;
        }

        // Display success message
        statusLabel.setText("Parcel sent successfully from " + senderCity + " to " + recipientCity + "!");
    }
}
