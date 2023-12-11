package pl.edu.agh.to2.hotel.presenter.reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.service.CustomerService;
import pl.edu.agh.to2.hotel.service.RoomService;

@Component
public class ReservationEditDialog {
    @FXML
    public DatePicker startDateField;
    @FXML
    public DatePicker endDateField;
    @FXML
    public TextField roomField;
    @FXML
    public TextField customerField;
    @Setter
    private Reservation reservation;
    private Stage dialogStage;
    @Getter
    private boolean approved;
    private final CustomerService customerService;
    private final RoomService roomService;

    public ReservationEditDialog(CustomerService customerService, RoomService roomService) {
        this.customerService = customerService;
        this.roomService = roomService;
        approved = false;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        updateControls();
    }

    public void handleCancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    public void handleOkAction(ActionEvent actionEvent) {
        try {
            updateModel();
            approved = true;
            dialogStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateModel() throws Exception {
        reservation.setStartDate(startDateField.getValue());
        reservation.setEndDate(endDateField.getValue());
        reservation.setCustomer(customerService.findCustomerById(Long.parseLong(customerField.getText())).
                orElseThrow(() -> new Exception("Customer not found - " + customerField.getText())));
        reservation.setRoom(roomService.findRoomById(Long.parseLong(roomField.getText())).
                orElseThrow(() -> new Exception("Room not found - " + roomField.getText())));
    }

    private void updateControls() {
        if (reservation.getId() != -1) {
            startDateField.setValue(reservation.getStartDate());
            endDateField.setValue(reservation.getEndDate());
            roomField.textProperty().setValue(String.valueOf(reservation.getRoom().getId()));
            customerField.textProperty().setValue(String.valueOf(reservation.getCustomer().getId()));
        }
    }
}
