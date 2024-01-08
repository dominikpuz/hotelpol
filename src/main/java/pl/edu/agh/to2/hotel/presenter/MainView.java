package pl.edu.agh.to2.hotel.presenter;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.fxml.FxmlContext;
import pl.edu.agh.to2.hotel.fxml.FxmlContextProvider;
import pl.edu.agh.to2.hotel.fxml.IFxmlPresenter;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.model.filters.CustomerFilter;
import pl.edu.agh.to2.hotel.model.filters.RoomFilter;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;
import pl.edu.agh.to2.hotel.presenter.customer.CustomerActionDialog;
import pl.edu.agh.to2.hotel.presenter.customer.CustomerInfoDialog;
import pl.edu.agh.to2.hotel.presenter.customer.CustomerPicker;
import pl.edu.agh.to2.hotel.presenter.reservation.ReservationActionDialog;
import pl.edu.agh.to2.hotel.presenter.reservation.ReservationInfoDialog;
import pl.edu.agh.to2.hotel.presenter.room.RoomActionDialog;
import pl.edu.agh.to2.hotel.presenter.room.RoomInfoDialog;
import pl.edu.agh.to2.hotel.presenter.room.RoomPicker;

import java.util.function.Consumer;

import static pl.edu.agh.to2.hotel.fxml.FxmlContextType.*;

@Component
public class MainView implements IFxmlPresenter {
    @FXML
    public Tab reservationsTab;
    @FXML
    public BorderPane reservationsTabPage;
    @FXML
    public Tab roomsTab;
    @FXML
    public BorderPane roomsTabPage;
    @FXML
    public Tab customersTab;
    @FXML
    public BorderPane customersTabPage;

    private final FxmlContextProvider fxmlContextProvider;

    @Setter
    private Stage primaryStage;

    @Autowired
    public MainView(FxmlContextProvider fxmlContextProvider) {
        this.fxmlContextProvider = fxmlContextProvider;
    }

    private Stage createDialog(Pane dialog, String title) {
        return createDialog(dialog, title, primaryStage);
    }

    private Stage createDialog(Pane dialog, String title, Stage owner) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setScene(new Scene(dialog));
        return dialogStage;
    }

    public Customer showCustomerPicker(Stage owner, CustomerFilter partialFilter) {
        FxmlContext<CustomerPicker> context = fxmlContextProvider.load(CUSTOMER_PICKER);
        Stage dialogStage = createDialog(context.view(), "Select customer", owner);

        CustomerPicker presenter = context.controller();
        presenter.initializeDialog(dialogStage, partialFilter);

        dialogStage.showAndWait();
        return presenter.getModel();
    }

    public Room showRoomPicker(Stage owner, RoomFilter partialFilter) {
        FxmlContext<RoomPicker> context = fxmlContextProvider.load(ROOM_PICKER);
        Stage dialogStage = createDialog(context.view(), "Select room", owner);

        RoomPicker presenter = context.controller();
        presenter.initializeDialog(dialogStage, partialFilter);

        dialogStage.showAndWait();
        return presenter.getModel();
    }

    public void showAddReservationDialog(Reservation reservation, Consumer<Reservation> onSave) {
        showReservationDialog(reservation, "Add reservation", onSave);
    }

    public void showEditReservationDialog(Reservation reservation, Consumer<Reservation> onSave) {
        showReservationDialog(reservation, "Edit reservation", onSave);
    }

    public void showReservationDialog(Reservation reservation, String title, Consumer<Reservation> onSave) {
        FxmlContext<ReservationActionDialog> context = fxmlContextProvider.load(RESERVATION_EDIT_DIALOG);
        Stage dialogStage = createDialog(context.view(), title);

        ReservationActionDialog presenter = context.controller();
        presenter.initializeDialog(dialogStage, reservation, onSave);

        dialogStage.showAndWait();
    }

    public void showAddCustomerDialog(Customer customer, Consumer<Customer> onSave) {
        showCustomerDialog(customer, "Add customer", onSave);
    }
    public void showAddCustomerDialog(Customer customer, Stage owner, Consumer<Customer> onSave) {
        showCustomerDialog(customer, "Add customer", owner, onSave);
    }

    public void showEditCustomerDialog(Customer customer, Consumer<Customer> onSave) {
        showCustomerDialog(customer, "Edit customer", onSave);
    }

    public void showCustomerDialog(Customer customer, String title, Consumer<Customer> onSave) {
        showCustomerDialog(customer, title, primaryStage, onSave);
    }

    public void showCustomerDialog(Customer customer, String title, Stage owner, Consumer<Customer> onSave) {
        FxmlContext<CustomerActionDialog> context = fxmlContextProvider.load(CUSTOMER_EDIT_DIALOG);
        Stage dialogStage = createDialog(context.view(), title, owner);

        CustomerActionDialog presenter = context.controller();
        presenter.initializeDialog(dialogStage, customer, onSave);

        dialogStage.showAndWait();
    }

    public void showCustomerInfo(Customer customer) {
        FxmlContext<CustomerInfoDialog> context = fxmlContextProvider.load(CUSTOMER_INFO_DIALOG);

        Stage dialogStage = createDialog(context.view(), "Customer info");

        CustomerInfoDialog presenter = context.controller();
        presenter.initializeDialog(dialogStage, customer);

        dialogStage.showAndWait();
    }

    public void showRoomInfo(Room room) {
        FxmlContext<RoomInfoDialog> view = fxmlContextProvider.load(ROOM_INFO_DIALOG);

        Stage dialogStage = createDialog(view.view(), "Room info");

        RoomInfoDialog presenter = view.controller();
        presenter.initializeDialog(dialogStage, room);

        dialogStage.showAndWait();
    }

    public void showReservationInfo(Reservation reservation, Consumer<ReservationState> onStateUpdate) {
        FxmlContext<ReservationInfoDialog> context = fxmlContextProvider.load(RESERVATION_INFO_DIALOG);

        ReservationInfoDialog presenter = context.controller();
        Stage dialogStage = createDialog(context.view(), "Reservation info");
        presenter.initializeDialog(dialogStage, reservation, onStateUpdate);

        dialogStage.showAndWait();
    }

    public void showRoomDialog(Room room, String title, Consumer<Room> onSave) {
        FxmlContext<RoomActionDialog> context = fxmlContextProvider.load(ROOM_EDIT_DIALOG);

        Stage dialogStage = createDialog(context.view(), title);

        RoomActionDialog presenter = context.controller();
        presenter.initializeDialog(dialogStage, room, onSave);

        dialogStage.showAndWait();
    }

    public void showEditRoomDialog(Room room, Consumer<Room> onSave) {
        showRoomDialog(room, "Edit room", onSave);
    }

    public void showAddRoomDialog(Room room, Consumer<Room> onSave) {
        showRoomDialog(room, "Add room", onSave);
    }
}
