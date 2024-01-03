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
import pl.edu.agh.to2.hotel.presenter.customer.CustomerInfoDialog;
import pl.edu.agh.to2.hotel.presenter.customer.CustomerPicker;
import pl.edu.agh.to2.hotel.presenter.reservation.ReservationActionDialog;
import pl.edu.agh.to2.hotel.presenter.reservation.ReservationInfoDialog;
import pl.edu.agh.to2.hotel.presenter.room.RoomActionDialog;
import pl.edu.agh.to2.hotel.presenter.room.RoomInfoDialog;
import pl.edu.agh.to2.hotel.presenter.room.RoomPicker;

import java.util.List;

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

    private final FxmlContextProvider fxmlContextProvider;

    @Setter
    private Stage primaryStage;

    @Autowired
    public MainView(FxmlContextProvider fxmlContextProvider) {
        this.fxmlContextProvider = fxmlContextProvider;
    }

    private Stage createDialog(Pane dialog, String title) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setScene(new Scene(dialog));
        return dialogStage;
    }

    private Stage createDialog(Pane dialog, String title, Stage owner) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setScene(new Scene(dialog));
        return dialogStage;
    }

    public Customer showCustomerPicker(Stage owner, List<Customer> customers) {
        FxmlContext<CustomerPicker> context = fxmlContextProvider.load(CUSTOMER_PICKER);
        Stage dialogStage = createDialog(context.view(), "Select customer", owner);

        CustomerPicker presenter = context.controller();
        presenter.initializeDialog(dialogStage, customers);

        dialogStage.showAndWait();
        return presenter.getModel();
    }

    public Room showRoomPicker(Stage owner, List<Room> rooms) {
        FxmlContext<RoomPicker> context = fxmlContextProvider.load(ROOM_PICKER);
        Stage dialogStage = createDialog(context.view(), "Select room", owner);

        RoomPicker presenter = context.controller();
        presenter.initializeDialog(dialogStage, rooms);

        dialogStage.showAndWait();
        return presenter.getModel();
    }

    public boolean showAddReservationDialog(Reservation reservation) {
        return showReservationDialog(reservation, "Add reservation");
    }

    public boolean showEditReservationDialog(Reservation reservation) {
        return showReservationDialog(reservation,  "Edit reservation");
    }

    public boolean showReservationDialog(Reservation reservation, String title) {
        FxmlContext<ReservationActionDialog> context = fxmlContextProvider.load(RESERVATION_EDIT_DIALOG);
        Stage dialogStage = createDialog(context.view(), title);

        ReservationActionDialog presenter = context.controller();
        presenter.initializeDialog(dialogStage, reservation);

        dialogStage.showAndWait();
        return presenter.isApproved();
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

    public boolean showReservationInfo(Reservation reservation) {
        FxmlContext<ReservationInfoDialog> context = fxmlContextProvider.load(RESERVATION_INFO_DIALOG);

        ReservationInfoDialog presenter = context.controller();
        Stage dialogStage = createDialog(context.view(), "Reservation info");
        presenter.initializeDialog(dialogStage, reservation);

        dialogStage.showAndWait();
        return presenter.isApproved();
    }

    public boolean showRoomDialog(Room room, String title) {
        FxmlContext<RoomActionDialog> context = fxmlContextProvider.load(ROOM_EDIT_DIALOG);

        Stage dialogStage = createDialog(context.view(), title);

        RoomActionDialog presenter = context.controller();
        presenter.initializeDialog(dialogStage, room);

        dialogStage.showAndWait();
        return presenter.isApproved();
    }

    public boolean showEditRoomDialog(Room room) {
        return showRoomDialog(room, "Edit room");
    }

    public boolean showAddRoomDialog(Room room) {
        return showRoomDialog(room, "Add room");
    }
}
