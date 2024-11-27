package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import databaseOP.ParcelOP;
import model.ParcelTracking;

public class ParcelTrackingController {

    @FXML
    private TableView<ParcelTracking> trackingTable;

    @FXML
    private Button backButton;

    private ObservableList<ParcelTracking> trackingData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
       
        try {
            
           

            ResultSet rs = ParcelOP.getTrackingInfoForCustomer(Session.getInstance().getUserId());

            if (rs != null) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    TableColumn<ParcelTracking, ?> column = new TableColumn<>(columnName);
                    
                   
                    if (metaData.getColumnType(i) == Types.INTEGER) {
                        column.setCellValueFactory(new PropertyValueFactory<>(columnName));
                    } else if (metaData.getColumnType(i) == Types.VARCHAR) {
                        column.setCellValueFactory(new PropertyValueFactory<>(columnName));
                    } else if (metaData.getColumnType(i) == Types.TIMESTAMP) {
                        column.setCellValueFactory(new PropertyValueFactory<>(columnName));
                    }
                    trackingTable.getColumns().add(column);
                }

                // Now fill data into table after creating columns
                trackingData.clear();
                while (rs.next()) {
                    int eventID = rs.getInt("event_id");
                    int parcelID = rs.getInt("parcel_id");
                    String status = rs.getString("status");
                    LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                    // Add data to trackingData list
                    trackingData.add(new ParcelTracking(eventID, parcelID, status, timestamp));
                }

                // Set the data in the TableView
                trackingTable.setItems(trackingData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

    @FXML
    private void handleBackButton() {
        System.out.println("Back button clicked. Return to the previous screen.");
        // Implement navigation logic here
    }
}
