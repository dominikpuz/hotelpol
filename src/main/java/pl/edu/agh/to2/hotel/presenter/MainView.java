package pl.edu.agh.to2.hotel.presenter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.FXMLLoaderProvider;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.presenter.customer.CustomerInfo;
import pl.edu.agh.to2.hotel.presenter.reservation.ReservationEditDialog;
import pl.edu.agh.to2.hotel.presenter.reservation.ReservationInfo;
import pl.edu.agh.to2.hotel.presenter.room.RoomEditDialog;
import pl.edu.agh.to2.hotel.presenter.room.RoomInfo;

import java.io.IOException;

@Component
public class MainView {
    @FXML
    public Tab reservationsTab;
    @FXML
    public BorderPane reservationsTabPage;
    @FXML
    public Tab roomsTab;
    @FXML
    public BorderPane roomsTabPage;

    private final FXMLLoaderProvider fxmlLoaderProvider;

    @Setter
    private Stage primaryStage;

    public MainView(FXMLLoaderProvider fxmlLoaderProvider) {
        this.fxmlLoaderProvider = fxmlLoaderProvider;
    }

    private Stage createDialog(BorderPane dialog, String title) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setScene(new Scene(dialog));
        return dialogStage;
    }

    public boolean showEditReservationDialog(Reservation reservation) {
        try {
            FXMLLoader loader = fxmlLoaderProvider.load("ReservationEditDialog");
            BorderPane dialog = loader.load();

            Stage dialogStage = createDialog(dialog, "Edit reservation");

            ReservationEditDialog presenter = loader.getController();
            presenter.setReservation(reservation);
            presenter.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return presenter.isApproved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showAddReservationDialog(Reservation reservation) {
        try {
            FXMLLoader loader = fxmlLoaderProvider.load("ReservationEditDialog");
            BorderPane dialog = loader.load();

            Stage dialogStage = createDialog(dialog, "Add reservation");

            ReservationEditDialog presenter = loader.getController();
            presenter.setReservation(reservation);
            presenter.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return presenter.isApproved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showCustomerInfo(Customer customer) {
        try {
            FXMLLoader loader = fxmlLoaderProvider.load("CustomerInfoDialog");
            BorderPane dialog = loader.load();

            Stage dialogStage = createDialog(dialog, "Customer info");

            CustomerInfo presenter = loader.getController();
            presenter.setCustomer(customer);
            presenter.setDialogStage(dialogStage);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRoomInfo(Room room) {
        try {
            FXMLLoader loader = fxmlLoaderProvider.load("RoomInfoDialog");
            BorderPane dialog = loader.load();

            Stage dialogStage = createDialog(dialog, "Room info");

            RoomInfo presenter = loader.getController();
            presenter.setRoom(room);
            presenter.setDialogStage(dialogStage);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showReservationInfo(Reservation reservation) {
        try {
            FXMLLoader loader = fxmlLoaderProvider.load("ReservationInfoDialog");
            BorderPane dialog = loader.load();

            Stage dialogStage = createDialog(dialog, "Reservation info");

            ReservationInfo presenter = loader.getController();
            presenter.setReservation(reservation);
            presenter.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return presenter.isApproved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showAddRoomDialog(Room room) {
        try {
            FXMLLoader loader = fxmlLoaderProvider.load("RoomEditDialog");
            BorderPane dialog = loader.load();

            Stage dialogStage = createDialog(dialog, "Add room");

            RoomEditDialog presenter = loader.getController();
            presenter.setRoom(room);
            presenter.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return presenter.isApproved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showEditRoomDialog(Room room) {
        try {
            FXMLLoader loader = fxmlLoaderProvider.load("RoomEditDialog");
            BorderPane dialog = loader.load();

            Stage dialogStage = createDialog(dialog, "Edit room");

            RoomEditDialog presenter = loader.getController();
            presenter.setRoom(room);
            presenter.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return presenter.isApproved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
