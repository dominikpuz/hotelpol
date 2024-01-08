package pl.edu.agh.to2.hotel.presenter.room;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.model.filters.IModelFilter;
import pl.edu.agh.to2.hotel.model.filters.RoomFilter;
import pl.edu.agh.to2.hotel.persistance.StringBedTypeListConverter;
import pl.edu.agh.to2.hotel.presenter.PickerDialogPresenter;
import pl.edu.agh.to2.hotel.service.RoomService;

import java.util.List;

@Component
public class RoomPicker extends PickerDialogPresenter<Room> {
    @FXML
    public Button okButton;
    @FXML
    public TableView<Room> roomTable;
    @FXML
    public TableColumn<Room, String> roomNumberColumn;
    @FXML
    public TableColumn<Room, String> floorColumn;
    @FXML
    public TableColumn<Room, String> priceColumn;
    @FXML
    public TableColumn<Room, String> roomStandardColumn;
    @FXML
    public TableColumn<Room, String> bedListColumn;
    @FXML
    private RoomFilteringPresenter roomFilteringController;

    private final StringBedTypeListConverter converter;
    private final RoomService roomService;

    @Autowired
    public RoomPicker(RoomService roomService) {
        this.roomService = roomService;
        converter = new StringBedTypeListConverter();
    }

    @FXML
    private void initialize() {
        roomTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        roomNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRoomNumber()));
        floorColumn.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getFloor())));
        priceColumn.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getRentPrice())));
        roomStandardColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRoomStandard().toString()));
        bedListColumn.setCellValueFactory(param -> new SimpleStringProperty(
                converter.convertToDatabaseColumn(param.getValue().getBeds())
        ));

        okButton.disableProperty().bind(Bindings.isEmpty(roomTable.getSelectionModel().getSelectedItems()));

        roomFilteringController.modelFilter.addListener((observable, oldValue, newValue) -> loadData(newValue));
    }

    @Override
    public void loadData(IModelFilter roomFilter) {
        RoomFilter fullFilter = (RoomFilter) roomFilter.mergeFilter(partialFilter);

        List<Room> filteredData = roomService.searchRooms(fullFilter);
        ObservableList<Room> observableList = FXCollections.observableList(filteredData);
        SortedList<Room> sortedList = new SortedList<>(observableList);
        roomTable.setItems(FXCollections.observableArrayList(filteredData));
        sortedList.comparatorProperty().bind(roomTable.comparatorProperty());
    }

    @Override
    public void finalizeSelection() {
        model = roomTable.getSelectionModel().getSelectedItem();
    }
}
