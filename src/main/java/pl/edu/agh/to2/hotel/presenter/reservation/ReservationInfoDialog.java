package pl.edu.agh.to2.hotel.presenter.reservation;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.util.converter.LocalDateStringConverter;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;
import pl.edu.agh.to2.hotel.presenter.ActionDialogPresenter;
import pl.edu.agh.to2.hotel.service.ReservationService;

import java.time.format.DateTimeFormatter;

@Component
public class ReservationInfoDialog extends ActionDialogPresenter<Reservation> {

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
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private final LocalDateStringConverter converter;

    public ReservationInfoDialog(ReservationService reservationService) {
        this.reservationService = reservationService;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        converter = new LocalDateStringConverter(formatter, formatter);
    }

    @Override
    public void loadData() {
        statusBox.getItems().addAll(model.getState().getAvailableNextStates());
        firstNameField.setText(model.getCustomer().getFirstName());
        lastNameField.setText(model.getCustomer().getLastName());
        floorField.setText(String.valueOf(model.getRoom().getFloor()));
        roomNumberField.setText(String.valueOf(model.getRoom().getRoomNumber()));
        startDateField.setText(converter.toString(model.getStartDate()));
        endDateFiled.setText(converter.toString(model.getEndDate()));
        statusBox.setValue(model.getState());
    }

    @Override
    public boolean isAddingDialog() {
        return false;
    }

    @Override
    public boolean validateAndSubmitModel() {
        ReservationState newState = statusBox.getValue();
        if(newState == model.getState()) return true;
        reservationService.updateReservationState(model, newState);
        return true;
    }
}
