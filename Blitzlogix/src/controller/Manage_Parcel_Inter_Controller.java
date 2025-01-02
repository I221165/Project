package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Manage_Parcel_Inter_Controller {

    @FXML
    private ListView<String> driverListView;
    @FXML
    private ListView<String> parcelListView;
    @FXML
    private Button assignButton;

    private String selectedDriver;
    private String selectedParcel;

    @FXML
    private void initialize() {
        // Create ObservableLists for ListViews
        ObservableList<String> driverData = FXCollections.observableArrayList();
        ObservableList<String> parcelData = FXCollections.observableArrayList();

        // Set data to ListViews
        driverListView.setItems(driverData);
        parcelListView.setItems(parcelData);

        // Populate ListViews with data from database
        populateLists();
    }

    public void populateLists() {
        // Get the ResultSet from the database
        ResultSet rs1 = databaseOP.ManagerOP.getIntercityDriversForManager(Session.getInstance().getManagerID());
        ResultSet rs2 = databaseOP.ManagerOP.getIntercityParcelsForManager(Session.getInstance().getManagerID());

        ObservableList<String> driverData = driverListView.getItems();
        ObservableList<String> parcelData = parcelListView.getItems();

        // Process driver data
        try {
            while (rs1.next()) {
                String driverRow = rs1.getInt(1) + " - " + rs1.getString(2) + " (" + rs1.getString(3) + ")";
                driverData.add(driverRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Process parcel data
        try {
            while (rs2.next()) {
                String parcelRow = rs2.getInt(1) + " - " + rs2.getString(2) + " (" + rs2.getString(3) + " to " + rs2.getString(5) + ")";
                parcelData.add(parcelRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDriverRowClick(MouseEvent event) {
        // Handle row selection for driver
        selectedDriver = driverListView.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onParcelRowClick(MouseEvent event) {
        // Handle row selection for parcel
        selectedParcel = parcelListView.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onAssignButtonClick() {
        if (selectedDriver == null || selectedParcel == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Error");
            alert.setHeaderText("No Row Selected");
            alert.setContentText("Please select a row from both lists.");
            alert.showAndWait();
            return;
        }

        // Extract Driver and Parcel IDs from the selected strings
        String driverID = selectedDriver.split(" - ")[0];
        String parcelID = selectedParcel.split(" - ")[0];

        try {
            int driverIntID = Integer.parseInt(driverID);
            int parcelIntID = Integer.parseInt(parcelID);

            // Handle the assignment (abstracted logic)
            databaseOP.ManagerOP.assignParcel(parcelIntID, driverIntID);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Assignment Successful");
            alert.setHeaderText("Driver Assigned");
            alert.setContentText("Driver with ID " + driverID + " has been assigned to Parcel with ID " + parcelID);
            alert.showAndWait();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Conversion Error");
            alert.setHeaderText("Invalid Data Format");
            alert.setContentText("Could not convert IDs to integers.");
            alert.showAndWait();
        }
    }
}
