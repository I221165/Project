package controller;

import databaseOP.DriverOP;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PickUpParcelController {

    @FXML
    private TableView<Parcel> parcelTable;

    @FXML
    private TableColumn<Parcel, Integer> parcelIdColumn;

    @FXML
    private TableColumn<Parcel, String> statusColumn;

    @FXML
    private TableColumn<Parcel, Button> actionColumn;

    private final ObservableList<Parcel> parcelList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns
        parcelIdColumn.setCellValueFactory(new PropertyValueFactory<>("parcelId"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Configure action column for "Pick Up" button
        actionColumn.setCellFactory(column -> new TableCell<>() {
            private final Button pickUpButton = new Button("Pick Up");

            {
                pickUpButton.setOnAction(event -> {
                    Parcel parcel = getTableView().getItems().get(getIndex());
                    int parcelId = parcel.getParcelId();

                    // Mark parcel as picked up in the database
                    DriverOP.pickupParcel(Session.getInstance().getDriverId(), parcelId);

                    // Update status in the UI
                    parcel.setStatus("Picked Up");
                    parcelTable.refresh();
                    System.out.println("Parcel " + parcelId + " marked as picked up.");
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    Parcel parcel = getTableView().getItems().get(getIndex());
                    setGraphic("Picked Up".equals(parcel.getStatus()) ? null : pickUpButton);
                }
            }
        });

        // Load parcels into the table
        loadParcels();
    }

    @FXML
    public void loadParcels() {
        try {
            // Fetch parcels from the database
            ResultSet resultSet = DriverOP.getAssignedParcelsForDriver(Session.getInstance().getDriverId());

            if (resultSet != null) {
                populateTable(resultSet);
            } else {
                System.out.println("No data found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load parcels.");
        }
    }

    private void populateTable(ResultSet resultSet) {
        try {
            parcelList.clear(); // Clear existing rows

            while (resultSet.next()) {
                int parcelId = resultSet.getInt("parcel_id");
                String status = resultSet.getString("status");

                // Add new Parcel object to the list
                parcelList.add(new Parcel(parcelId, status));
            }

            // Bind data to the TableView
            parcelTable.setItems(parcelList);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to populate table.");
        }
    }
}
