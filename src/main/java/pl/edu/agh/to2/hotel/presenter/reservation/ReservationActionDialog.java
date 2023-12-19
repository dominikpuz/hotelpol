package pl.edu.agh.to2.hotel.presenter.reservation;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.presenter.ActionDialogPresenter;
import pl.edu.agh.to2.hotel.service.CustomerService;
import pl.edu.agh.to2.hotel.service.RoomService;

import java.util.Optional;

@Component
public class ReservationActionDialog extends ActionDialogPresenter<Reservation> {
    @FXML
    public DatePicker startDateField;
    @FXML
    public DatePicker endDateField;
    @FXML
    public TextField roomField;
    @FXML
    public TextField customerField;
    private final CustomerService customerService;
    private final RoomService roomService;

    public ReservationActionDialog(CustomerService customerService, RoomService roomService) {
        this.customerService = customerService;
        this.roomService = roomService;
    }

    @Override
    public void loadData() {
        startDateField.setValue(model.getStartDate());
        endDateField.setValue(model.getEndDate());
        roomField.textProperty().setValue(String.valueOf(model.getRoom().getId()));
        customerField.textProperty().setValue(String.valueOf(model.getCustomer().getId()));
    }

    @Override
    public boolean isAddingDialog() {
        return model.getId() == -1;
    }

    @Override
    public boolean validateAndSubmitModel() {
        Optional<Customer> customer = customerService.findCustomerById(Long.parseLong(customerField.getText()));
        Optional<Room> room = roomService.findRoomById(Long.parseLong(roomField.getText()));
        if(customer.isEmpty() || room.isEmpty()) return false;

        model.setStartDate(startDateField.getValue());
        model.setEndDate(endDateField.getValue());
        model.setCustomer(customer.get());
        model.setRoom(room.get());

        return true;
    }
}
