package pl.edu.agh.to2.hotel.presenter.room;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.fxml.components.RoomTable;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.model.filters.RoomFilter;
import pl.edu.agh.to2.hotel.presenter.PickerDialogPresenter;
import pl.edu.agh.to2.hotel.service.RoomService;

@Component
public class RoomPicker extends PickerDialogPresenter<Room, RoomFilter> {
    @FXML
    public Button okButton;

    @FXML
    private RoomTable roomTable;
    @FXML
    private RoomFilteringPresenter roomFilteringController;
    private final RoomService roomService;

    @Autowired
    public RoomPicker(RoomService roomService) {
        this.roomService = roomService;
    }

    @FXML
    private void initialize() {
        roomTable.setPageSupplier(page -> roomService.searchRooms(roomFilteringController.modelFilter.get().mergeFilter(partialFilter), page));
        okButton.disableProperty().bind(Bindings.isEmpty(roomTable.getSelectionModel().getSelectedItems()));

        roomFilteringController.modelFilter.addListener((observable) -> roomTable.reloadDataAndShowFirstPage());
        roomTable.reloadDataAndShowFirstPage();
    }

    @Override
    protected Room getSelectedModel() {
        return roomTable.getSelectionModel().getSelectedItem();
    }
}
