package pl.edu.agh.to2.hotel.presenter.reservation;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerFilter;
import pl.edu.agh.to2.hotel.presenter.ActionDialogPresenter;
import pl.edu.agh.to2.hotel.presenter.customer.CustomerPickerSummary;
import pl.edu.agh.to2.hotel.presenter.room.RoomPickerSummary;
import pl.edu.agh.to2.hotel.service.CustomerService;
import pl.edu.agh.to2.hotel.service.RoomService;

@Component
public class ReservationActionDialog extends ActionDialogPresenter<Reservation, Reservation> {
    @FXML
    private DatePicker startDateField;
    @FXML
    private DatePicker endDateField;
    @FXML
    public RoomPickerSummary roomPickerSummaryController;
    @FXML
    public CustomerPickerSummary customerPickerSummaryController;

    private final CustomerService customerService;
    private final RoomService roomService;

    public ReservationActionDialog(CustomerService customerService, RoomService roomService) {
        this.customerService = customerService;
        this.roomService = roomService;
    }

    @FXML
    private void initialize() {
        this.updateSelectableRooms();
        var selectableCustomers = customerService.searchCustomers(CustomerFilter.builder().build());
        customerPickerSummaryController.getSelectableCustomers().setValue(FXCollections.observableArrayList(selectableCustomers));

        roomPickerSummaryController.getSelectRoomButton().disableProperty().bind(
                Bindings.isNull(startDateField.valueProperty())
                        .or(Bindings.isNull(endDateField.valueProperty()))
        );

        startDateField.valueProperty().addListener((observable, oldValue, newValue) -> this.updateSelectableRooms());
        endDateField.valueProperty().addListener((observable, oldValue, newValue) -> this.updateSelectableRooms());

        roomPickerSummaryController.setDialogStage(dialogStage);
        customerPickerSummaryController.setDialogStage(dialogStage);
    }

    @Override
    public void loadData() {
        startDateField.setValue(model.getStartDate());
        endDateField.setValue(model.getEndDate());
    }

    @Override
    public boolean isAddingDialog() {
        return model.getId() == -1;
    }

    @Override
    public boolean validateAndSubmitModel() {
        Customer customer = customerPickerSummaryController.getSelectedCustomer().get();
        Room room = roomPickerSummaryController.getSelectedRoom().get();

        if(customer == null || room == null) return false;

        model.setStartDate(startDateField.getValue());
        model.setEndDate(endDateField.getValue());
        model.setCustomer(customer);
        model.setRoom(room);

        return tryDoDefaultAction(model);
    }

    private void updateSelectableRooms() {
        var selectableRooms = roomService.findAvailableRoomsBetweenDates(startDateField.getValue(), endDateField.getValue());
        roomPickerSummaryController.getSelectableRooms().setValue(FXCollections.observableArrayList(selectableRooms));
    }
}
