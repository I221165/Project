package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.time.LocalDateTime;
import model.*;


public class ParcelTrackingController {

    @FXML
    private TableView<ParcelTracking> trackingTable;

    @FXML
    private TableColumn<ParcelTracking, Integer> eventIdColumn;

    @FXML
    private TableColumn<ParcelTracking, Integer> parcelIdColumn;

    @FXML
    private TableColumn<ParcelTracking, String> statusColumn;

    @FXML
    private TableColumn<ParcelTracking, LocalDateTime> timestampColumn;

    @FXML
    private Button backButton;

    // Sample tracking data
    private ObservableList<ParcelTracking> trackingData = FXCollections.observableArrayList(
        new ParcelTracking(1, 101, "Dispatched", LocalDateTime.now().minusDays(3)),
        new ParcelTracking(2, 101, "In Transit", LocalDateTime.now().minusDays(2)),
        new ParcelTracking(3, 101, "Delivered", LocalDateTime.now().minusDays(1)),
        new ParcelTracking(4, 102, "Pending", LocalDateTime.now())
    );

    @FXML
    public void initialize() {
        // Initialize columns
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        parcelIdColumn.setCellValueFactory(new PropertyValueFactory<>("parcelID"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        // Load sample data into the table
        trackingTable.setItems(trackingData);
    }

    // Back button handler
    @FXML
    private void handleBackButton() {
        System.out.println("Back button clicked. Return to the previous screen.");
        // Implement navigation logic here
    }
}
