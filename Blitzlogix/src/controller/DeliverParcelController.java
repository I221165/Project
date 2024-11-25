package controller;

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
        // Simulated parcel data
        parcelList.add(new Parcel(1, 101, 201, 5, 1001, "In Transit", -1, ""));
        parcelList.add(new Parcel(2, 102, 202, 10, 1002, "In Transit", -1, ""));
        parcelList.add(new Parcel(3, 103, 203, 8, 1003, "Delivered", -1, ""));
    }
}
