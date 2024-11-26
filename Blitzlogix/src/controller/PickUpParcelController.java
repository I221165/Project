package controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import databaseOP.DriverOP;
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
    private TableView<ObservableList<String>> parcelTable;

    private final ObservableList<ObservableList<String>> parcelList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<ObservableList<String>, String> parcelIdColumn;

    @FXML
    private TableColumn<ObservableList<String>, String> statusColumn;

    @FXML
    private TableColumn<ObservableList<String>, Button> actionColumn;  // Adding action column

    @FXML
    public void initialize() {
        // Set up table columns dynamically if necessary (for parcel ID and status)

        // Assuming that the first two columns are parcel ID and status
        parcelIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(0)));
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(4)));

        // Action column (where the "Pick Up" button will be placed)
        actionColumn.setCellFactory(column -> new TableCell<ObservableList<String>, Button>() {
            private final Button pickUpButton = new Button("Pick Up");

            {
                pickUpButton.setOnAction(event -> {
                    ObservableList<String> parcel = getTableView().getItems().get(getIndex());
                   
                   int parcelId = Integer.parseInt(parcel.get(0));
                    
                    
                    DriverOP.pickupParcel(Session.getInstance().getDriverId() , parcelId);
                    
                    parcel.set(4, "Picked Up");  // Set the status to "Picked Up"
                    parcelTable.refresh(); // Refresh table to reflect changes
                    System.out.println("Parcel " + parcel.get(0) + " marked as picked up.");
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                ObservableList<String> row = getTableView().getItems().get(getIndex());
                if (empty || "Picked Up".equals(row.get(4))) {
                    setGraphic(null);  // Hide button if already picked up
                } else {
                    setGraphic(pickUpButton);
                }
            }
        });
    }

    @FXML
    public void loadParcels() {
        try {
            // Fetch parcels from the database
            ResultSet resultSet = DriverOP.getAssignedParcelsForDriver(Session.getInstance().getDriverId());

            // Dynamically populate TableView from ResultSet
            populateTable(resultSet);

            System.out.println("Parcels loaded into table.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load parcels.");
        }
    }

    private void populateTable(ResultSet resultSet) {
        try {
            // Get ResultSet metadata
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Clear existing columns
            parcelTable.getColumns().clear();

            // Dynamically create columns based on ResultSet metadata
            for (int i = 1; i <= columnCount; i++) {
                final int colIndex = i - 1; // 0-based index for column values

                // Create a column for each column in the ResultSet
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(metaData.getColumnName(i));
                column.setCellValueFactory(cellData -> 
                    new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(colIndex))
                );
                parcelTable.getColumns().add(column);
            }

            // Add action column to the table
            parcelTable.getColumns().add(actionColumn);

            // Populate rows dynamically
            ObservableList<String> row;
            while (resultSet.next()) {
                row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i)); // Add value for each column
                }
                parcelList.add(row);
            }

            // Set the ObservableList to the TableView
            parcelTable.setItems(parcelList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
