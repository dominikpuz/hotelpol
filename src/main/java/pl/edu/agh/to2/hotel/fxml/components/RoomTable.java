package pl.edu.agh.to2.hotel.fxml.components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import pl.edu.agh.to2.hotel.fxml.FxmlContextProvider;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.StringBedTypeListConverter;

public class RoomTable extends PageableTable<Room> {
    private static final String COMPONENT_NAME = "components/RoomTable.fxml";
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

    private final StringBedTypeListConverter bedTypeListConverter = new StringBedTypeListConverter();

    public RoomTable() {
        FxmlContextProvider.loadComponent(COMPONENT_NAME, this);
    }

    @Override
    protected void initializeCellValueFactories() {
        roomNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRoomNumber()));
        floorColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getFloor()));
        rentPriceColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getRentPrice()));
        roomStandardColumn.setCellValueFactory(param -> new SimpleStringProperty(
                param.getValue().getRoomStandard().toString()));
        bedListColumn.setCellValueFactory(param -> new SimpleStringProperty(
                bedTypeListConverter.convertToDatabaseColumn(param.getValue().getBeds())));
    }
}