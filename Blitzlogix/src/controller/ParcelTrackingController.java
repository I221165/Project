package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ParcelTracking;
import databaseOP.ParcelOP;

import java.sql.*;
import java.time.LocalDateTime;

public class ParcelTrackingController {

    @FXML
    private TableView<ParcelTracking> trackingTable;

    @FXML
    private TableColumn<ParcelTracking, Integer> eventIDColumn;

    @FXML
    private TableColumn<ParcelTracking, Integer> parcelIDColumn;

    @FXML
    private TableColumn<ParcelTracking, String> statusColumn;

    @FXML
    private TableColumn<ParcelTracking, LocalDateTime> timestampColumn;

    @FXML
    private Button backButton;

    private ObservableList<ParcelTracking> trackingData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Explicitly define columns
        eventIDColumn = new TableColumn<>("Event ID");
        eventIDColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));

        parcelIDColumn = new TableColumn<>("Parcel ID");
        parcelIDColumn.setCellValueFactory(new PropertyValueFactory<>("parcelID"));

        statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        timestampColumn = new TableColumn<>("Timestamp");
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        // Add columns to the table
        trackingTable.getColumns().clear();
        trackingTable.getColumns().addAll(eventIDColumn, parcelIDColumn, statusColumn, timestampColumn);

        // Load data into the table
        loadTrackingData();
    }

    private void loadTrackingData() {
        try {
            ResultSet rs = ParcelOP.getTrackingInfoForCustomer(Session.getInstance().getUserId());

            if (rs != null) {
                trackingData.clear(); // Clear existing data
                while (rs.next()) {
                    int eventID = rs.getInt("eventid");
                    int parcelID = rs.getInt("parcelid");
                    String status = rs.getString("status");
                    Timestamp timestamp = rs.getTimestamp("timestamp");

                    // Convert SQL Timestamp to LocalDateTime
                    LocalDateTime localTimestamp = timestamp.toLocalDateTime();

                    // Add a new ParcelTracking object to the observable list
                    trackingData.add(new ParcelTracking(eventID, parcelID, status, localTimestamp));
                }
                // Set the observable list to the TableView
                trackingTable.setItems(trackingData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error Loading Data", "Failed to load tracking data from the database.");
        }
    }

    @FXML
    private void handleBackButton() {
        System.out.println("Back button clicked. Return to the previous screen.");
        // Implement navigation logic here
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
