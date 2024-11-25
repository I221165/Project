package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;


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
    
    // Assuming a logged-in driver with a city
    private String driverCity = "New York"; // Set dynamically based on logged-in driver

    @FXML
    public void initialize() {
        // Set up table columns
        parcelIdColumn.setCellValueFactory(new PropertyValueFactory<>("parcelID"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        actionColumn.setCellFactory(column -> new TableCell<>() {
            private final Button pickUpButton = new Button("Pick Up");

            {
                pickUpButton.setOnAction(event -> {
                    Parcel parcel = getTableView().getItems().get(getIndex());
                    parcel.setStatus("Picked Up"); // Update parcel status
                    parcelTable.refresh(); // Refresh table to reflect changes
                    System.out.println("Parcel " + parcel.getParcelID() + " marked as picked up.");
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView().getItems().get(getIndex()).getStatus().equalsIgnoreCase("Picked Up")) {
                    setGraphic(null); // Hide button if already picked up
                } else {
                    setGraphic(pickUpButton);
                }
            }
        });

        parcelTable.setItems(parcelList);
        loadParcels(); // Load initial data
    }

    private void loadParcels() {
        // Simulated parcel data, now with city information
        parcelList.add(new Parcel(1, 101, 201, 5, 1001, "In Transit", -1, ""));
        parcelList.add(new Parcel(2, 102, 202, 10, 1002, "In Transit", -1,"" ));
        parcelList.add(new Parcel(3, 103, 203, 8, 1003, "In Transit", -1,""));
        
        // Filter parcels that match the driver's city (example: "New York")
        ObservableList<Parcel> filteredParcels = FXCollections.observableArrayList();
        for (Parcel parcel : parcelList) {
            
                filteredParcels.add(parcel);
            
        }
        
        parcelTable.setItems(filteredParcels); // Set filtered list to table
    }
}
