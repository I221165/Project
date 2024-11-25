package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import java.util.ArrayList;
import java.util.List;

public class DeliverParcelCController {

    @FXML
    private TableView<Parcel> parcelTable;

    @FXML
    private TableColumn<Parcel, Integer> parcelIdColumn;

    @FXML
    private TableColumn<Parcel, Integer> senderIdColumn;

    @FXML
    private TableColumn<Parcel, Integer> receiverIdColumn;

    @FXML
    private TableColumn<Parcel, Integer> weightColumn;

    @FXML
    private TableColumn<Parcel, String> statusColumn;

    private final ObservableList<Parcel> parcelList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up columns
        parcelIdColumn.setCellValueFactory(new PropertyValueFactory<>("parcelID"));
        senderIdColumn.setCellValueFactory(new PropertyValueFactory<>("senderID"));
        receiverIdColumn.setCellValueFactory(new PropertyValueFactory<>("receiverID"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load data into the TableView
        parcelTable.setItems(parcelList);
    }

    @FXML
    public void loadParcels() {
        // Simulate loading parcels
        List<Parcel> parcels = new ArrayList<>();
        parcels.add(new Parcel(1, 101, 201, 5, 10, "Pending", 1, ""));
        parcels.add(new Parcel(2, 102, 202, 8, 12, "Shipped", 2, ""));
        parcels.add(new Parcel(3, 103, 203, 12, 15, "Delivered", 3, ""));

        // Add parcels to the ObservableList
        parcelList.clear();
        parcelList.addAll(parcels);

        System.out.println("Parcels loaded into table.");
    }

    @FXML
    public void markAsDelivered() {
        for (Parcel parcel : parcelList) {
            if ("Shipped".equals(parcel.getStatus())) {
                parcel.setStatus("Delivered");
                System.out.println("Parcel " + parcel.getParcelID() + " marked as delivered.");
            }
        }
        parcelTable.refresh(); // Refresh the TableView to update the status column
    }

    @FXML
    public void acceptParcel() {
        // Get the selected parcel
        Parcel selectedParcel = parcelTable.getSelectionModel().getSelectedItem();

        if (selectedParcel != null) {
            // Change the status to "Accepted"
            selectedParcel.setStatus("Accepted");
            System.out.println("Parcel " + selectedParcel.getParcelID() + " accepted.");
            
            // Refresh the table to reflect changes
            parcelTable.refresh();
        } else {
            System.out.println("No parcel selected for acceptance.");
        }
    }
}
