package pl.edu.agh.to2.hotel.presenter.room;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.fxml.IFxmlPresenter;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.StringBedTypeListConverter;
import pl.edu.agh.to2.hotel.persistance.room.RoomFilter;
import pl.edu.agh.to2.hotel.presenter.MainView;
import pl.edu.agh.to2.hotel.service.RoomService;


@Component
public class RoomOverview implements IFxmlPresenter {
    @FXML
    public TableView<Room> roomTable;
    @FXML
    public Button addRoomButton;
    @FXML
    public Button editRoomButton;
    @FXML
    public TableColumn<Room, String> roomNumberColumn;
    @FXML
    public TableColumn<Room, Number> floorColumn;
    @FXML
    public TableColumn<Room, Number> rentPriceColumn;
    @FXML
    public TableColumn<Room, String> roomStandardColumn;
    @FXML
    public TableColumn<Room, String> bedListColumn;

    private final MainView mainController;
    private final RoomService roomService;
    private final StringBedTypeListConverter bedTypeListConverter;

    @Autowired
    public RoomOverview(RoomService roomService, MainView mainController) {
        this.roomService = roomService;
        this.mainController = mainController;
        bedTypeListConverter = new StringBedTypeListConverter();
    }

    @FXML
    private void initialize() {
        roomTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        roomNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRoomNumber()));
        floorColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getFloor()));
        rentPriceColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getRentPrice()));
        roomStandardColumn.setCellValueFactory(param -> new SimpleStringProperty(
                param.getValue().getRoomStandard().toString()));
        bedListColumn.setCellValueFactory(param -> new SimpleStringProperty(
                bedTypeListConverter.convertToDatabaseColumn(param.getValue().getBeds())));

        editRoomButton.disableProperty().bind(Bindings.isEmpty(roomTable.getSelectionModel().getSelectedItems()));
        loadData();
    }

    private void loadData() {
        ObservableList<Room> rooms = FXCollections.observableArrayList(
                roomService.searchRooms(RoomFilter.builder().build()));
        roomTable.setItems(rooms);
    }

    @FXML
    public void handleAddRoom(ActionEvent ignoreEvent) {
        Room room = new Room();
        if (mainController.showAddRoomDialog(room)) {
            roomService.createNewRoom(room);
            loadData();
        }
    }

    @FXML
    public void handleEditRoom(ActionEvent ignoreEvent) {
        Room room = roomTable.getSelectionModel().getSelectedItem();
        if (room != null) {
            if (mainController.showEditRoomDialog(room)) {
                // TODO: edit room
                loadData();
            }
        }
    }
}
