package pl.edu.agh.to2.hotel.presenter.reservation;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.model.filters.ReservationFilter;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;
import pl.edu.agh.to2.hotel.presenter.customer.CustomerPickerSummary;
import pl.edu.agh.to2.hotel.presenter.filter.FilteringPresenter;
import pl.edu.agh.to2.hotel.presenter.filter.IllegalFilterInput;
import pl.edu.agh.to2.hotel.presenter.room.RoomPickerSummary;

@Component
@Scope("prototype")
public class ReservationFilteringPresenter extends FilteringPresenter<ReservationFilter> {
    @FXML
    private ChoiceBox<ReservationState> reservationState;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    public CustomerPickerSummary customerPickerSummaryController;
    @FXML
    public RoomPickerSummary roomPickerSummaryController;

    @Override
    @FXML
    protected void initialize() {
        reservationState.getItems().addAll(ReservationState.values());
    }

    @Override
    public ReservationFilter createFilter() throws IllegalFilterInput {
        Customer selectedCustomer = customerPickerSummaryController.getSelectedCustomer().get();
        Room selectedRoom = roomPickerSummaryController.getSelectedRoom().get();

        var filter = ReservationFilter.builder()
                .customerId(selectedCustomer == null ? null : selectedCustomer.getId())
                .roomId(selectedRoom == null ? null : selectedRoom.getId())
                .reservationState(reservationState.getValue())
                .startDate(startDate.getValue())
                .endDate(endDate.getValue())
                .build();

        if (startDate.getValue() != null && endDate.getValue() != null && startDate.getValue().isAfter(endDate.getValue())) {
            throw new IllegalFilterInput("Start date cannot be after end date.");
        }

        return filter;
    }

    @Override
    protected void resetModelFilter() {
        modelFilter.set(ReservationFilter.builder().build());
    }

    @Override
    protected void resetControls() {
        customerPickerSummaryController.getSelectedCustomer().set(null);
        roomPickerSummaryController.getSelectedRoom().set(null);
        reservationState.getSelectionModel().clearSelection();
        startDate.setValue(null);
        endDate.setValue(null);

    }
}
