package pl.edu.agh.to2.hotel.presenter.reservation;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.model.filters.RoomFilter;
import pl.edu.agh.to2.hotel.presenter.ActionDialogPresenter;
import pl.edu.agh.to2.hotel.presenter.customer.CustomerPickerSummary;
import pl.edu.agh.to2.hotel.presenter.room.RoomPickerSummary;

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

    @FXML
    private void initialize() {
        roomPickerSummaryController.getSelectRoomButton().disableProperty().bind(
                Bindings.isNull(startDateField.valueProperty())
                        .or(Bindings.isNull(endDateField.valueProperty()))
        );

        startDateField.valueProperty().addListener
                ((observable, oldValue, newValue) -> {
                    RoomFilter rf = RoomFilter.builder()
                            .startAvailabilityDate(newValue)
                            .endAvailabilityDate(endDateField.getValue())
                            .build();
                    roomPickerSummaryController.getPartialFilter().set(rf);
                });
        endDateField.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    RoomFilter rf = RoomFilter.builder()
                            .startAvailabilityDate(startDateField.getValue())
                            .endAvailabilityDate(newValue)
                            .build();
                    roomPickerSummaryController.getPartialFilter().set(rf);
                });

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

        model.setStartDate(startDateField.getValue());
        model.setEndDate(endDateField.getValue());
        model.setCustomer(customer);
        model.setRoom(room);

        return tryDoDefaultAction(model);
    }
}
