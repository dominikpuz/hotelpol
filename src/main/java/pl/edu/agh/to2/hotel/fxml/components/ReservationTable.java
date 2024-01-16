package pl.edu.agh.to2.hotel.fxml.components;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import pl.edu.agh.to2.hotel.fxml.FxmlContextProvider;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Reservation;

import java.time.LocalDate;

public class ReservationTable extends PageableTable<Reservation> {
    private static final String COMPONENT_NAME = "components/ReservationTable.fxml";
    @FXML
    public TableColumn<Reservation, String> roomNumberColumn;
    @FXML
    public TableColumn<Reservation, String> customerColumn;
    @FXML
    public TableColumn<Reservation, LocalDate> startDateColumn;
    @FXML
    public TableColumn<Reservation, LocalDate> endDateColumn;
    @FXML
    public TableColumn<Reservation, String> reservationStateColumn;
    @FXML
    public TableColumn<Reservation, Number> rentPriceColumn;

    public ReservationTable() {
        FxmlContextProvider.loadComponent(COMPONENT_NAME, this);
    }

    @Override
    protected void initializeCellValueFactories() {
        roomNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRoom().getRoomNumber()));
        customerColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCustomer().getFirstName() + " " + param.getValue().getCustomer().getLastName()));
        startDateColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getStartDate()));
        endDateColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getEndDate()));
        reservationStateColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getState().toString()));
        rentPriceColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getRoom().getRentPrice()));
    }
}