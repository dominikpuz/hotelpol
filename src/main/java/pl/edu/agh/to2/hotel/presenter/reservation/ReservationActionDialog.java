package pl.edu.agh.to2.hotel.presenter.reservation;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerFilter;
import pl.edu.agh.to2.hotel.presenter.ActionDialogPresenter;
import pl.edu.agh.to2.hotel.presenter.MainView;
import pl.edu.agh.to2.hotel.service.CustomerService;
import pl.edu.agh.to2.hotel.service.RoomService;

import java.util.List;

@Component
public class ReservationActionDialog extends ActionDialogPresenter<Reservation> {
    @FXML
    public DatePicker startDateField;
    @FXML
    public DatePicker endDateField;
    @FXML
    public Button selectRoomButton;
    @FXML
    public Text floorText;
    @FXML
    public Text roomNumberText;
    @FXML
    public Text customerText;

    private Customer customer;
    private Room room;
    private final CustomerService customerService;
    private final RoomService roomService;
    private final MainView mainController;

    public ReservationActionDialog(CustomerService customerService, RoomService roomService, MainView mainController) {
        this.mainController = mainController;
        this.customerService = customerService;
        this.roomService = roomService;
    }

    @FXML
    private void initialize() {
        selectRoomButton.disableProperty().bind(Bindings.isNull(startDateField.valueProperty())
                .or(Bindings.isNull(endDateField.valueProperty())));
        floorText.setVisible(false);
        roomNumberText.setVisible(false);
        customerText.setVisible(false);
    }

    private void updateInfo() {
        if (room != null) {
            roomNumberText.setText("Room nr: " + room.getRoomNumber());
            floorText.setText("Floor: " + room.getFloor());
            floorText.setVisible(true);
            roomNumberText.setVisible(true);
        }
        if (customer != null) {
            customerText.setText("Customer: " + customer.getFirstName() + " " + customer.getLastName());
            customerText.setVisible(true);
        }
    }
    @Override
    public void loadData() {
        startDateField.setValue(model.getStartDate());
        endDateField.setValue(model.getEndDate());
        room = model.getRoom();
        customer = model.getCustomer();
        updateInfo();
    }

    @Override
    public boolean isAddingDialog() {
        return model.getId() == -1;
    }

    @Override
    public boolean validateAndSubmitModel() {
        if(customer == null || room == null) return false;

        model.setStartDate(startDateField.getValue());
        model.setEndDate(endDateField.getValue());
        model.setCustomer(customer);
        model.setRoom(room);

        return true;
    }

    @FXML
    public void handleSelectCustomer(ActionEvent ignoreEvent) {
        List<Customer> customers = customerService.searchCustomers(CustomerFilter.builder().build());
        customer = mainController.showCustomerPicker(dialogStage, customers);
        updateInfo();
    }

    @FXML
    public void handleNewCustomer(ActionEvent ignoreEvent) {
        Customer newCustomer = new Customer();
        if (mainController.showAddCustomerDialog(newCustomer, dialogStage)) {
            customer = customerService.addNewCustomer(newCustomer);
            updateInfo();
        }
    }

    @FXML
    public void handleSelectRoom(ActionEvent ignoreEvent) {
        List<Room> rooms = roomService.findAvailableRoomsBetweenDates(startDateField.getValue(), endDateField.getValue());
        room = mainController.showRoomPicker(dialogStage, rooms);
        updateInfo();
    }
}
