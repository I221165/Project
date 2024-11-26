package controller;

import databaseOP.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Parcel;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliverParcelCController {

    @FXML
    private TableView<ObservableList<String>> parcelTable;

    private final ObservableList<ObservableList<String>> parcelList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Empty initialization since we will dynamically create columns
    }

    @FXML
    public void loadParcels() {
        try {
            // Fetch parcels from the database
            ResultSet resultSet = CustomerOP.getCustomerParcelsToReceive(Session.getInstance().getUserId());

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

    @FXML
    public void markAsDelivered() {
        // Iterate through the parcel rows and change status to "Delivered"
        for (ObservableList<String> row : parcelList) {
            if ("Shipped".equals(row.get(4))) { // Assuming status is in the 5th column
                row.set(4, "Delivered"); // Update status in the row
            }
        }
        parcelTable.refresh(); // Refresh the TableView to reflect changes
    }

    @FXML
    public void acceptParcel() {
        // Get the selected parcel (row)
        ObservableList<String> selectedParcel = parcelTable.getSelectionModel().getSelectedItem();

        if (selectedParcel != null) {
            // Change the status to "Accepted" (Assuming status is in 5th column)
            selectedParcel.set(4, "Accepted");

            if(CustomerOP.receiveParcelButton(Session.getInstance().getUserId(),parcelTable.getSelectionModel().getSelectedIndex()))
            		{
            	
            	
            		
            
            
            
            	parcelTable.refresh();
            System.out.println("Parcel " + selectedParcel.get(0) + " accepted.");
            		}
            
            // Refresh the table to reflect changes
            
        } else {
            System.out.println("No parcel selected for acceptance.");
        }
    }
}
