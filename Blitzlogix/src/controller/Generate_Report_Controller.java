package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Generate_Report_Controller {

    @FXML
    private Button confirmButton;

    @FXML
    private ChoiceBox<String> reportChoice;

    @FXML
    private TableView<ObservableList<String>> tableView;

    @FXML
    private TableColumn<ObservableList<String>, String> field1Column;

    @FXML
    private TableColumn<ObservableList<String>, String> field2Column;

    @FXML
    private TableColumn<ObservableList<String>, String> field3Column;

    @FXML
    private TableColumn<ObservableList<String>, String> field4Column;

    @FXML
    private TableColumn<ObservableList<String>, String> field5Column;

    public void initialize() {
        // Initialize the table columns to use SimpleStringProperty for each field in the row.
        field1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        field2Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        field3Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        field4Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        field5Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));

        // Set up the choices for the ChoiceBox
        reportChoice.setItems(FXCollections.observableArrayList("Driver", "Customer"));
    }

    // This method will be triggered when the Confirm button is pressed
    @FXML
    public void generateReport(MouseEvent event) {
        String selectedChoice = reportChoice.getValue();
        ResultSet resultSet = null;

        // Choose the database function based on the selected option
        if ("Driver".equals(selectedChoice)) {
            // Call the database function for "Driver"
            // resultSet = getDriverDataFromDatabase(); // Uncomment and implement this function
        } else if ("Customer".equals(selectedChoice)) {
            // Call the database function for "Customer"
            // resultSet = getCustomerDataFromDatabase(); // Uncomment and implement this function
        }

        // Once the ResultSet is retrieved, display the data in the table
        if (resultSet != null) {
            displayDataInTable(resultSet);
        }
    }

    // This method will extract data from the ResultSet and display it in the table view
    private void displayDataInTable(ResultSet resultSet) {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        try {
            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(resultSet.getString(1)); // Field 1
                row.add(resultSet.getString(2)); // Field 2
                row.add(resultSet.getString(3)); // Field 3
                row.add(resultSet.getString(4)); // Field 4
                row.add(resultSet.getString(5)); // Field 5
                data.add(row);
            }

            tableView.setItems(data); // Set the data into the TableView
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
