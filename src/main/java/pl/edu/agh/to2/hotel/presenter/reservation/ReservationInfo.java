package pl.edu.agh.to2.hotel.presenter.reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;
import pl.edu.agh.to2.hotel.service.IllegalOperationException;
import pl.edu.agh.to2.hotel.service.ReservationService;

import java.time.format.DateTimeFormatter;

@Component
public class ReservationInfo {
    @FXML
    public Text firstNameField;
    @FXML
    public Text lastNameField;
    @FXML
    public Text floorField;
    @FXML
    public Text roomNumberField;
    @FXML
    public Text startDateField;
    @FXML
    public Text endDateFiled;
    @FXML
    public ChoiceBox<ReservationState> statusBox;
    private final ReservationService reservationService;
    private Stage dialogStage;
    @Setter
    private Reservation reservation;
    private final LocalDateStringConverter converter;
    @Getter
    private boolean approved;

    public ReservationInfo(ReservationService reservationService) {
        this.reservationService = reservationService;
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        converter = new LocalDateStringConverter(formatter, formatter);
        approved = false;
    }

    @FXML
    private void initialize() {
        statusBox.getItems().addAll(ReservationState.CREATED, ReservationState.CHECKED_IN, ReservationState.CANCELLED,
                ReservationState.PAID, ReservationState.CHECKED_OUT);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        initializeControls();
    }

    private void initializeControls() {
        firstNameField.setText(reservation.getCustomer().getFirstName());
        lastNameField.setText(reservation.getCustomer().getLastName());
        floorField.setText(String.valueOf(reservation.getRoom().getFloor()));
        roomNumberField.setText(String.valueOf(reservation.getRoom().getRoomNumber()));
        startDateField.setText(converter.toString(reservation.getStartDate()));
        endDateFiled.setText(converter.toString(reservation.getEndDate()));
        statusBox.setValue(reservation.getState());
    }
    @FXML
    public void handleOkAction(ActionEvent actionEvent) {
        if (!reservation.getState().equals(statusBox.getValue())) {
            try {
                ReservationState newState = statusBox.getValue();
                reservation = reservationService.updateReservationState(reservation, newState);
                approved = true;
                dialogStage.close();
            } catch (IllegalOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            dialogStage.close();
        }
    }

    @FXML
    public void handleCancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }
}
