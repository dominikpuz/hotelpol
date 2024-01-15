package pl.edu.agh.to2.hotel.presenter.reservation;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.fxml.IFxmlPresenter;
import pl.edu.agh.to2.hotel.fxml.components.ReservationTable;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.presenter.MainView;
import pl.edu.agh.to2.hotel.service.ReservationService;

@Component
public class ReservationOverview implements IFxmlPresenter {
    @FXML
    public Button showCustomerButton;
    @FXML
    public Button showRoomButton;
    @FXML
    public Button editReservationButton;
    @FXML
    public Button addReservationButton;
    @FXML
    public Button showReservationButton;
    @FXML
    private ReservationTable reservationTable;
    @FXML
    public ReservationFilteringPresenter reservationFilteringController;  // the field name has to end with "Controller" so the JavaFX parses it correctly

    private final MainView mainController;
    private final ReservationService reservationService;

    public ReservationOverview(MainView mainController, ReservationService reservationService) {
        this.mainController = mainController;
        this.reservationService = reservationService;
    }

    @FXML
    private void initialize() {
        reservationTable.setPageSupplier(page -> reservationService.searchReservations(reservationFilteringController.modelFilter.get(), page));
        showCustomerButton.disableProperty().bind(Bindings.isEmpty(reservationTable.getSelectionModel().getSelectedItems()));
        showRoomButton.disableProperty().bind(Bindings.isEmpty(reservationTable.getSelectionModel().getSelectedItems()));
        showReservationButton.disableProperty().bind(Bindings.isEmpty(reservationTable.getSelectionModel().getSelectedItems()));
        editReservationButton.disableProperty().bind(Bindings.isEmpty(reservationTable.getSelectionModel().getSelectedItems()));

        reservationFilteringController.modelFilter.addListener(observable -> reservationTable.reloadDataAndShowFirstPage());
        reservationFilteringController.customerPickerSummaryController.setAddNewCustomerButtonEnabled(false);

        reservationTable.reloadDataAndShowFirstPage();
    }
    @FXML
    public void handleAddReservation(ActionEvent ignoreEvent) {
        mainController.showAddReservationDialog(new Reservation(), toSave -> {
            reservationService.addNewReservation(toSave);
            reservationTable.reloadData();
        });
    }

    @FXML
    public void handleEditReservation(ActionEvent ignoreEvent) {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if(reservation == null) return;
        mainController.showEditReservationDialog(reservation, toUpdate -> {
           reservationService.updateReservation(toUpdate);
           reservationTable.reloadData();
        });
    }
    @FXML
    public void handleShowReservation(ActionEvent ignoreEvent) {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if(reservation == null) return;
        mainController.showReservationInfo(reservation, updatedState -> {
            reservationService.updateReservationState(reservation, updatedState);
            reservationTable.reloadData();
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
