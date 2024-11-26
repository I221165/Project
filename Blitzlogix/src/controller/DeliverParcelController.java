package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import databaseOP.DriverOP;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import model.*;

public class DeliverParcelController {

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
        // Set up table columns
        parcelIdColumn.setCellValueFactory(new PropertyValueFactory<>("parcelID"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        actionColumn.setCellFactory(column -> new TableCell<>() {
            private final Button deliverButton = new Button("Mark as Delivered");

            {
                deliverButton.setOnAction(event -> {
                    Parcel parcel = getTableView().getItems().get(getIndex());
                    
                    DriverOP.dropOffParcel(Session.getInstance().getDriverId(),parcel.getParcelID());
                    
                    parcel.setStatus("Delivered"); // Update parcel status
                    
                    
                    
                    parcelTable.refresh(); // Refresh table to reflect changes
                    System.out.println("Parcel " + parcel.getParcelID() + " marked as delivered.");
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView().getItems().get(getIndex()).getStatus().equalsIgnoreCase("Delivered")) {
                    setGraphic(null); // Hide button if already delivered
                } else {
                    setGraphic(deliverButton);
                }
            }
        });

        parcelTable.setItems(parcelList);
        loadParcels(); // Load initial data
    }

    private void loadParcels() {
    	ResultSet rs = DriverOP.getParcelsToDropOffForDriver(Session.getInstance().getDriverId()); // Example driverID

        try {
            while (rs.next()) {
                int parcelID = rs.getInt("parcel_id");
                String sourceCity = rs.getString("source_city");
                String destinationCity = rs.getString("destination_city");
                String status = rs.getString("status");

                Parcel parcel = new Parcel(parcelID, status); // Create Parcel with basic information
               

                parcelList.add(parcel); // Add the Parcel object to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
