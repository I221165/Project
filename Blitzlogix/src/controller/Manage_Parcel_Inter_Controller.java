package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Manage_Parcel_Inter_Controller {

    @FXML
    private TableView<ObservableList<String>> table1;
    @FXML
    private TableView<ObservableList<String>> table2;
    @FXML
    private Button assignButton;

    private Object selectedDriver;
    private Object selectedParcel;

    @FXML
    private void initialize() {
        ObservableList<ObservableList<String>> data1 = FXCollections.observableArrayList();
        ObservableList<ObservableList<String>> data2 = FXCollections.observableArrayList();

        table1.setItems(data1);
        table2.setItems(data2);

        // Set up table columns for table1 (Driver Table)
        TableColumn<ObservableList<String>, String> driverDIDColumn = new TableColumn<>("DID");
        TableColumn<ObservableList<String>, String> driverNameColumn = new TableColumn<>("Name");
        TableColumn<ObservableList<String>, String> driverTypeColumn = new TableColumn<>("Driver Type");

        driverDIDColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        driverNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        driverTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));

        table1.getColumns().addAll(driverDIDColumn, driverNameColumn, driverTypeColumn);

        // Set up table columns for table2 (Parcel Table)
        TableColumn<ObservableList<String>, String> parcelIDColumn = new TableColumn<>("Parcel ID");
        TableColumn<ObservableList<String>, String> senderNameColumn = new TableColumn<>("Sender Name");
        TableColumn<ObservableList<String>, String> sourceCityColumn = new TableColumn<>("Source City");
        TableColumn<ObservableList<String>, String> receiverNameColumn = new TableColumn<>("Receiver Name");
        TableColumn<ObservableList<String>, String> destinationCityColumn = new TableColumn<>("Destination City");
        TableColumn<ObservableList<String>, String> statusColumn = new TableColumn<>("Status");

        parcelIDColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        senderNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        sourceCityColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));
        receiverNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(3)));
        destinationCityColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(4)));
        statusColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(5)));

        table2.getColumns().addAll(parcelIDColumn, senderNameColumn, sourceCityColumn, receiverNameColumn, destinationCityColumn, statusColumn);
    }

    public void populateTables() {
    	
         ////////////////////////////////////////////////////////////////////////////////////////////////////////
         ResultSet rs1 = databaseOP.ManagerOP.getIntercityDriversForManager(Session.getInstance().getManagerID());
         ResultSet rs2 = databaseOP.ManagerOP.getIntercityParcelsForManager(Session.getInstance().getManagerID());
         ////////////////////////////////////////////////////////////////////////////////////////////////////////
    	
    	
        ObservableList<ObservableList<String>> data1 = table1.getItems();
        ObservableList<ObservableList<String>> data2 = table2.getItems();

        // Process the first ResultSet (Driver data with 3 columns)
        try {
            while (rs1.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(Integer.toString(rs1.getInt(1))); // DID
                row.add(rs1.getString(2)); // Name
                row.add(rs1.getString(3)); // Driver Type
                data1.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Process the second ResultSet (Parcel data with 6 columns)
        try {
            while (rs2.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(Integer.toString(rs2.getInt(1))); // Parcel ID
                row.add(rs2.getString(2)); // Sender Name
                row.add(rs2.getString(3)); // Source City
                row.add(rs2.getString(4)); // Receiver Name
                row.add(rs2.getString(5)); // Destination City
                row.add(rs2.getString(6)); // Status
                data2.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDriverRowClick(MouseEvent event) {
        selectedDriver = table1.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onParcelRowClick(MouseEvent event) {
        selectedParcel = table2.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onAssignButtonClick() {
        if (selectedDriver == null || selectedParcel == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Error");
            alert.setHeaderText("No Row Selected");
            alert.setContentText("Please select a row from both tables.");
            alert.showAndWait();
            return;
        }

        // Extracting IDs from the selected rows
        ObservableList<String> driverData = (ObservableList<String>) selectedDriver;
        ObservableList<String> parcelData = (ObservableList<String>) selectedParcel;

        String driverID = driverData.get(0);
        String parcelID = parcelData.get(0);

        try {
            int driverIntID = Integer.parseInt(driverID);
            int parcelIntID = Integer.parseInt(parcelID);

            // Handle assignment here (you can replace this with real assignment logic)
            System.out.println("Driver ID: " + driverIntID + " assigned to Parcel ID: " + parcelIntID);
            
            ///////////////////////////////////////////////////////////
            databaseOP.ManagerOP.assignParcel(parcelIntID, driverIntID);
            ///////////////////////////////////////////////////////////
            
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
