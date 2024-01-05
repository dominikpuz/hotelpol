package pl.edu.agh.to2.hotel.presenter.room;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.StringBedTypeListConverter;
import pl.edu.agh.to2.hotel.presenter.PickerDialogPresenter;

@Component
public class RoomPicker extends PickerDialogPresenter<Room> {
    @FXML
    public Button okButton;
    @FXML
    public TableView<Room> roomTable;
    @FXML
    public TextField searchField;
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
    private final StringBedTypeListConverter converter;

    public RoomPicker() {
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
    }

    @Override
    public void loadData() {
        FilteredList<Room> filteredData = new FilteredList<>(FXCollections.observableArrayList(modelList));
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createPredicate(newValue))
        );
        SortedList<Room> sortableData = new SortedList<>(filteredData);
        roomTable.setItems(sortableData);
        sortableData.comparatorProperty().bind(roomTable.comparatorProperty());
    }

    @Override
    public void finalizeSelection() {
        model = roomTable.getSelectionModel().getSelectedItem();
    }
    @Override
    protected boolean searchFindsModel(Room model, String searchText) {
        return model.getRoomNumber().toLowerCase().contains(searchText);
    }

}
