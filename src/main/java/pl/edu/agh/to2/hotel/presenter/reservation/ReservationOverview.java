package pl.edu.agh.to2.hotel.presenter.reservation;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.presenter.MainView;
import pl.edu.agh.to2.hotel.service.ReservationService;

import java.time.LocalDate;

@Component
public class ReservationOverview {
    @FXML
    public Button showCustomerButton;
    @FXML
    public Button showRoomButton;
    @FXML
    public Button editReservationButton;
    @FXML
    public Button addReservationButton;
    @FXML
    public TableColumn<Reservation, String> roomNumberColumn;
    @FXML
    public TableColumn<Reservation, String> customerColumn;
    @FXML
    public TableColumn<Reservation, LocalDate> startDateColumn;
    @FXML
    public TableColumn<Reservation, LocalDate> endDateColumn;
    @FXML
    public TableColumn<Reservation, String> reservationStateColumn;
    @FXML
    public TableColumn<Reservation, Number> rentPriceColumn;
    @FXML
    public Button showReservationButton;
    @FXML
    private TableView<Reservation> reservationTable;
    private final MainView mainController;
    private final ReservationService reservationService;

    public ReservationOverview(MainView mainController, ReservationService reservationService) {
        this.mainController = mainController;
        this.reservationService = reservationService;
    }

    @FXML
    private void initialize() {
        reservationTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        roomNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRoom().getRoomNumber()));
        customerColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCustomer().getFirstName() + " " + param.getValue().getCustomer().getLastName()));
        startDateColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getStartDate()));
        endDateColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getStartDate()));
        reservationStateColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getState().toString()));
        rentPriceColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getRoom().getRentPrice()));

        showCustomerButton.disableProperty().bind(Bindings.isEmpty(reservationTable.getSelectionModel().getSelectedItems()));
        showRoomButton.disableProperty().bind(Bindings.isEmpty(reservationTable.getSelectionModel().getSelectedItems()));
        showReservationButton.disableProperty().bind(Bindings.isEmpty(reservationTable.getSelectionModel().getSelectedItems()));
        editReservationButton.disableProperty().bind(Bindings.isEmpty(reservationTable.getSelectionModel().getSelectedItems()));

        loadData();
    }

    public void loadData() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(
                reservationService.findReservationsByStartDate(LocalDate.now()));
        reservationTable.setItems(reservations);
    }
    @FXML
    public void handleAddReservation(ActionEvent ignoreEvent) {
        mainController.showAddReservationDialog(new Reservation(), toSave -> {
            reservationService.addNewReservation(toSave);
            loadData();
        });
    }

    @FXML
    public void handleEditReservation(ActionEvent ignoreEvent) {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if(reservation == null) return;
        mainController.showEditReservationDialog(reservation, toUpdate -> {
           reservationService.updateReservation(toUpdate);
           loadData();
        });
    }
    @FXML
    public void handleShowReservation(ActionEvent ignoreEvent) {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if(reservation == null) return;
        mainController.showReservationInfo(reservation, updatedState -> {
            reservationService.updateReservationState(reservation, updatedState);
            loadData();
        });
    }

    @FXML
    public void handleShowCustomer(ActionEvent ignoreEvent) {
        Customer customer = reservationTable.getSelectionModel().getSelectedItem().getCustomer();
        if (customer != null) {
            mainController.showCustomerInfo(customer);
        }
    }

    @FXML
    public void handleShowRoom(ActionEvent ignoreEvent) {
        Room room = reservationTable.getSelectionModel().getSelectedItem().getRoom();
        if (room != null) {
            mainController.showRoomInfo(room);
        }
    }
}
