package pl.edu.agh.to2.hotel.presenter.room;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.fxml.IFxmlPresenter;
import pl.edu.agh.to2.hotel.fxml.components.RoomTable;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.presenter.MainView;
import pl.edu.agh.to2.hotel.service.RoomService;


@Component
public class RoomOverview implements IFxmlPresenter {

    @FXML
    private RoomTable roomTable;
    @FXML
    public Button addRoomButton;
    @FXML
    public Button editRoomButton;
    @FXML
    public RoomFilteringPresenter roomFilteringController;

    private final MainView mainController;
    private final RoomService roomService;

    @Autowired
    public RoomOverview(RoomService roomService, MainView mainController) {
        this.roomService = roomService;
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        roomTable.setPageSupplier((page) -> roomService.searchRooms(roomFilteringController.modelFilter.get(), page));
        editRoomButton.disableProperty().bind(Bindings.isEmpty(roomTable.getSelectionModel().getSelectedItems()));

        roomFilteringController.modelFilter.addListener(observable -> roomTable.reloadDataAndShowFirstPage());
        roomTable.reloadDataAndShowFirstPage();
    }

    @FXML
    public void handleAddRoom(ActionEvent ignoreEvent) {
        mainController.showAddRoomDialog(new Room(), toSave -> {
            roomService.createNewRoom(toSave);
            roomTable.reloadData();
        });
    }

    @FXML
    public void handleEditRoom(ActionEvent ignoreEvent) {
        Room room = roomTable.getSelectionModel().getSelectedItem();
        if(room == null) return;

        mainController.showEditRoomDialog(room, toUpdate -> {
            roomService.updateRoom(toUpdate);
            roomTable.reloadData();
        });
    }
}
