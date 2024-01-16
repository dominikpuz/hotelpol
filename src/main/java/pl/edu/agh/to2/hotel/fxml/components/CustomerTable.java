package pl.edu.agh.to2.hotel.fxml.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import pl.edu.agh.to2.hotel.fxml.FxmlContextProvider;
import pl.edu.agh.to2.hotel.model.Customer;

public class CustomerTable extends PageableTable<Customer> {
    private static final String COMPONENT_NAME = "components/CustomerTable.fxml";
    @FXML
    private TableColumn<Customer, String> firstNameColumn;

    @FXML
    private TableColumn<Customer, String> lastNameColumn;

    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;

    @FXML
    private TableColumn<Customer, String> emailColumn;

    public CustomerTable() {
        FxmlContextProvider.loadComponent(COMPONENT_NAME, this);
    }

    @Override
    protected void initializeCellValueFactories() {
        firstNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastName()));
        phoneNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPhoneNumber()));
        emailColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEmail()));
    }
}